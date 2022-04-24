package com.eicas.crawler;

import com.eicas.crawler.driver.WebDriverDownloader;
import com.eicas.crawler.entity.CollectNodeEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;

import javax.annotation.Resource;

@Slf4j
@Component
public class ArticleSpider {

    @Resource
    private ArticlePageProcessor articlePageProcessor;

    @Resource
    private ArticlePipeline articlePipeline;

    //phantomjs在系统存放的路径
    @Value("${ecms.phantomJS}")
    private String phantomJS;

    /**
     * 开启爬虫方法
     *
     * @param node 采集规则
     */
    public void run(CollectNodeEntity node) {
        try {
            //设置采集规则
            articlePageProcessor.setNode(node);
            new Spider(articlePageProcessor)
                    .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(10000000)))
                    .addUrl(node.getCollectUrl())
                    .setDownloader(new WebDriverDownloader(phantomJS))
                    .addPipeline(articlePipeline)
                    .thread(1)
                    .start();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
