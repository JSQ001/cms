package com.eicas.crawler.scheduler;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eicas.crawler.entity.CollectNodeEntity;
import com.eicas.crawler.service.ICollectNodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

import static com.eicas.utils.CommonUtils.LocalDateTimeConvertToCron;

@Slf4j
@Component
public class InitTask implements CommandLineRunner {
    @Resource
    CronTaskRegistrar cronTaskRegistrar;

    @Resource
    ICollectNodeService collectNodeService;

    @Override
    public void run(String... args) throws Exception {
        List<CollectNodeEntity> list = collectNodeService
                .list(Wrappers.<CollectNodeEntity>lambdaQuery()
                        .eq(CollectNodeEntity::getAutomatic, true));
        try {
            for (CollectNodeEntity node : list) {
                cronTaskRegistrar.addCronTask(new SchedulingRunnable("articleSpider",
                                "run",
                                node.getId().toString()),
                        LocalDateTimeConvertToCron(node.getAutoTime()));
                log.debug("调试信息：" + node.getAutoTime());
                log.debug("调试信息：" + LocalDateTimeConvertToCron(node.getAutoTime()));
            }
        } catch (Exception e) {
            log.error("定时任务启动失败！");
        }
    }
}
