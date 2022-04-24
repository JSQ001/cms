package com.eicas.crawler.webmagic;

import com.eicas.crawler.webmagic.driver.WebDriverDownloader;
import com.eicas.crawler.entity.CollectNodeEntity;
import com.eicas.crawler.service.ICollectNodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;

import javax.annotation.Resource;

@Slf4j
@Component("articleSpider")
public class ArticleSpider {
    @Resource
    private ICollectNodeService collectNodeService;

    @Resource
    private ArticlePageProcessor articlePageProcessor;

    @Resource
    private Pipeline articlePipeline;

    //phantomjs在系统存放的路径
    @Value("${ecms.phantomJS}")
    private String phantomJS;

    /**
     * 开启爬虫方法
     *
     * @param nodeId 采集规则ID
     */
    public void run(Long nodeId) {
        CollectNodeEntity nodeEntity = collectNodeService.getById(nodeId);
        log.debug(nodeEntity.toString());
        try {
            //设置采集规则
            articlePageProcessor.setNode(nodeEntity);
            new Spider(articlePageProcessor)
                    .setScheduler(new QueueScheduler()
                            .setDuplicateRemover(new BloomFilterDuplicateRemover(10000000)))
                    .addUrl(nodeEntity.getCollectUrl())
                    .setDownloader(new WebDriverDownloader(phantomJS))
                    .addPipeline(articlePipeline)
                    .thread(1)
                    .start();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 定时任务接收String类型参数
     * @param nodeIdStr
     */
    public void run(String nodeIdStr) {
        Long nodeId =  Long.valueOf(nodeIdStr);
        this.run(nodeId);
    }

}
