package com.eicas.crawler.webmagic;

import com.eicas.cms.service.IArticleService;
import com.eicas.crawler.entity.CollectNodeEntity;
import com.eicas.crawler.service.ICollectArticleService;
import com.eicas.crawler.service.ICollectNodeService;
import com.eicas.utils.FileUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import javax.annotation.Resource;
import java.io.Closeable;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * PageProcessor的定制分为三个部分，分别是爬虫的配置、页面元素的抽取和链接的发现。
 */
@Component
@Getter
@Setter
@Slf4j
public class ArticlePageProcessor implements PageProcessor, Closeable {

    @Resource
    ICollectNodeService collectNodeService;

    @Resource
    IArticleService articleService;
    @Resource
    ICollectArticleService collectArticleService;

    @Value("${ecms.store-root-path}")
    private String storeRootPath;
    @Value("${ecms.mapping-root-path}")
    private String mappingRootPath;

    private CollectNodeEntity node;

    private Site site = Site.me().setRetryTimes(3).setSleepTime(5000);

    /**
     * 定制爬虫抽取逻辑，校验采集规则
     *
     * @param page 采集的页面
     */
    @Override
    public void process(Page page) {
        Long columnId = node.getCatalogId();
        String source = StringUtils.hasText(node.getSource()) ? node.getSource().trim() : null;
        String collectUrl = StringUtils.hasText(node.getCollectUrl()) ? node.getCollectUrl().trim() : null;
        String pattern_list = node.getLinksRule();
        String pattern_title = node.getTitleRule();
        String pattern_content = node.getContentRule();
        String pattern_subtitle = node.getSubTitleRule();
        String pattern_essential = node.getEssentialRule();
        String pattern_author = node.getAuthorRule();
        String pattern_publish_time = node.getPublishTimeRule();
        /*
         * 校验规则，列表、标题、内容规则不能为空
         */
        if (node == null || !StringUtils.hasText(pattern_list) || !StringUtils.hasText(pattern_title) || !StringUtils.hasText(pattern_content)) {
            page.setSkip(true);
            return;
        }

        if (page.getUrl().toString().equals(collectUrl)) {
            log.info("开始分析列表页");
            List<String> urls = page.getHtml().xpath(pattern_list).links().all();
            urls = urls.stream()
                    .distinct()
                    .filter(i -> !collectArticleService.hasRepetition(i)).collect(Collectors.toList());
            page.addTargetRequests(urls);
            log.info("下列页面将被采集：" + urls);
        } else {
            log.info("开始采集内容页");
            page.putField("columnId", columnId);
            page.putField("source", source);
            page.putField("title", page.getHtml().xpath(pattern_title).toString());
            page.putField("originUrl", page.getUrl().toString());

            if (StringUtils.hasText(pattern_subtitle)) {
                page.putField("subTitle", page.getHtml().xpath(pattern_subtitle).toString());
            } else {
                page.putField("subTitle", "");
            }

            if (StringUtils.hasText(pattern_essential)) {
                page.putField("essential", page.getHtml().xpath(pattern_essential).toString());
            } else {
                page.putField("essential", "");
            }

            if (StringUtils.hasText(pattern_author)) {
                page.putField("author", page.getHtml().xpath(pattern_author).toString());
            } else {
                page.putField("author", "");
            }

            if (StringUtils.hasText(pattern_publish_time)) {
                page.putField("publish_time", page.getHtml().xpath(pattern_publish_time).toString());
            } else {
                page.putField("publish_time", LocalDateTime.now());
            }

            /*
             * 1.采集文章内容
             * 2.下载正文图片，更新图片链接
             * 3.将第一张图片设置为文章封面图片
             */

            StringBuffer content = new StringBuffer(page.getHtml().xpath(pattern_content).toString());
            String coverImageUrl = crabImages(page.getUrl().toString(), content);
            if (StringUtils.hasText(coverImageUrl)) {
                page.putField("coverImageUrl", coverImageUrl);
            }
            page.putField("content", content.toString());
        }
    }

    /**
     * 1.下载文章所有图片，保存至本地
     * 2.更新文章图片地址
     * 3.返回第1张图片URL，将被作为文章标题图片
     *
     * @param baseURI 文章URL
     * @param content 文章内容
     * @return 更新图片链接后的文章内容
     */

    private String crabImages(String baseURI, StringBuffer content) {
        String coverImageUrl = null;
        // 清除内嵌样式及脚本
        content.replace(0, content.length(), stripHTML(content.toString()));
        Document document;
        if (StringUtils.hasText(content)) {
            try {
                // 基本网页URI
                URI base = new URI(baseURI);
                int count = 0;
                document = Jsoup.parse(content.toString()).normalise();
                // 清除style样式
                document.select("*").removeAttr("style");
                for (Element element : document.getElementsByTag("img")) {
                    String propertyValue = element.attr("src");
                    if (!propertyValue.matches("^(https?|ftp):(\\\\|//).*$")) {
                        // 解析相对URL，得到绝对URI
                        String imageUrl = base.resolve(propertyValue).toString();

                        String[] names = imageUrl.split("/");
                        String filename = names[names.length - 1];
                        String urlFile = FileUtils.crabImage(imageUrl, filename, storeRootPath, mappingRootPath);

                        if (urlFile != null) {
                            element.attr("src", urlFile);
                            // 将第一张图片设为焦点图
                            if (count++ == 0) {
                                coverImageUrl = urlFile;
                            }
                        }
                    }
                }
                content.replace(0, content.length(), document.body().toString());
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        return coverImageUrl;
    }

    /**
     * 清除自带样式及JS脚本
     */
    private String stripHTML(String html) {
        /*
         * 仅保留br,p,img,div标签
         */
        return html.replaceAll("</?[^/?(br)|(p)|(img)|(div)][^><]*>", "").trim().replace("\r\n", "").replace("&nbsp", "");
    }

    @Override
    public void close() {
        // TODO 状态处理
    }
}
