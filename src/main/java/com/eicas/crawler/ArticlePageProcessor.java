package com.eicas.crawler;

import com.eicas.crawler.entity.CollectNodeEntity;
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
import java.util.List;

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
        Long catalogId = node.getCatalogId();
        String source = node.getSource();
        String collectUrl = node.getCollectUrl().trim();
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
        if (node == null ||
                !StringUtils.hasText(pattern_list) ||
                !StringUtils.hasText(pattern_title) ||
                !StringUtils.hasText(pattern_content)) {
            page.setSkip(true);
            return;
        }

        if (page.getUrl().toString().equals(collectUrl)) {
            log.info("开始分析列表页");
            List<String> urls = page.getHtml().xpath(pattern_list).links().all();
            page.addTargetRequests(urls);
            log.info("如下地址将被采集：" + urls.toString());
        } else {
            log.info("开始采集内容页");
            /*
             * 设置入库栏目ID
             */
            page.putField("catalogId", catalogId);

            /*
             * 设置采集来源
             */
            page.putField("source", source);

            /*
             * 采集文章标题
             */
            page.putField("title", page.getHtml().xpath(pattern_title).toString().trim());
            /*
             * 设置文章原文URL
             */
            page.putField("originUrl", page.getUrl().toString().trim());

            /*
             * 采集文章副标题
             */
            if (StringUtils.hasText(pattern_subtitle)) {
                page.putField("subTitle", page.getHtml().xpath(pattern_subtitle).toString().trim());
            } else {
                page.putField("subTitle", "");
            }
            /*
             * 采集文章摘要
             */
            if (StringUtils.hasText(pattern_essential)) {
                page.putField("essential", page.getHtml().xpath(pattern_essential).toString().trim());
            } else {
                page.putField("essential", "");
            }
            /*
             * 采集文章作者
             */
            if (StringUtils.hasText(pattern_author)) {
                page.putField("author", page.getHtml().xpath(pattern_author).toString().trim());
            } else {
                page.putField("autor", "");
            }

            /*
             * 采集文章发布时间
             */
            if (StringUtils.hasText(pattern_publish_time)) {
                page.putField("publish_time", page.getHtml().xpath(pattern_publish_time).toString().trim());
            }

            /*
             * 1.采集文章内容
             * 2.下载正文图片，更新图片链接
             * 3.将第一张图片设置为文章封面图片
             */
            StringBuffer content = new StringBuffer(page.getHtml().xpath(pattern_content).toString().trim());
            String coverImageUrl = crabImages(page.getUrl().toString(), content);
            if (StringUtils.hasText(coverImageUrl)) {
                page.putField("coverImageUrl", coverImageUrl.trim());
            }
            page.putField("content", content.toString().trim());
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
        String cleanStr = html.replaceAll("</?[^/?(br)|(p)|(img)|(div)][^><]*>", "").trim()
                .replace("\r\n", "")
                .replace("&nbsp", "");
        return cleanStr;
    }

    @Override
    public void close() {
        // TODO 状态处理
    }
}
