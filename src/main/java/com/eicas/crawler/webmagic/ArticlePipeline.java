package com.eicas.crawler.webmagic;

import cn.hutool.core.date.DateUtil;
import com.eicas.crawler.entity.CollectArticleEntity;
import com.eicas.crawler.service.ICollectArticleService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import javax.annotation.Resource;
import java.time.LocalDateTime;


/**
 * 把PageProcessor抽取的结果，
 * 继续进行处理,保存入库
 */

@Slf4j
@Service
@Setter
@Transactional
public class ArticlePipeline implements Pipeline {

    @Resource
    private ICollectArticleService collectArticleService;

    @Override
    public void process(ResultItems items, Task task) {
        if (!StringUtils.hasText(items.get("title")) || !StringUtils.hasText(items.get("content"))) {
            items.setSkip(true);
            return;
        }

        Long catalogId = items.get("catalogId");
        String title = items.get("title");
        String subTitle = items.get("subTitle");
        String essential = items.get("essential");
        String content = items.get("content");
        String coverImageUrl = items.get("coverImageUrl");
        String author = items.get("author");
        String originUrl = items.get("originUrl");
        String publishTimeStr = items.get("publish_time");
        String source = items.get("source");

        /*
         * 作者处理，空则设置为系统采集
         */
        if (!StringUtils.hasText(author)) {
            author = "系统采集";
            log.debug(author);
        }
        /*
         * 摘要处理，为空则摘取内容前200个字符
         */
        if (!StringUtils.hasText(essential)) {
            String str = content.replaceAll("<([^>]*)>", "")
                    .replace("\t", "")
                    .replace("　", "")
                    .replace("&nbsp;", "").trim();
            essential = str.length() >= 200 ? str.substring(200) : str;
//            log.debug(essential);
        }
        /*
         * 原文发布时间处理
         */
        LocalDateTime publishTime;
        try {
            publishTime = DateUtil.parse(publishTimeStr).toTimestamp().toLocalDateTime();
        } catch (Exception e) {
            log.error("无法转换采集到的时间");
            publishTime = LocalDateTime.now();
        }

        CollectArticleEntity collectArticle = new CollectArticleEntity();
        collectArticle.setCatalogId(catalogId)
                .setTitle(title)
                .setSubTitle(subTitle)
                .setEssential(essential)
                .setContent(content)
                .setCoverImgUrl(coverImageUrl)
                .setAuthor(author)
                .setOriginUrl(originUrl)
                .setPublishTime(publishTime)
                .setSource(source)
                .setReceived(false)
                .setCollectTime(LocalDateTime.now());

        collectArticleService.save(collectArticle);
        log.debug(collectArticle.getOriginUrl() + "页面采集成功");
    }
}
