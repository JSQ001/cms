package com.eicas.cms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eicas.cms.entity.ArticleVisitLog;
import com.eicas.cms.pojo.param.DayArticleStaticsResult;
import com.eicas.cms.service.ArticleVisitLogService;
import com.eicas.cms.mapper.ArticleVisitLogMapper;
import lombok.var;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
* @author jsq
* @description 针对表【cms_article_visit_log】的数据库操作Service实现
* @createDate 2022-04-21 21:51:04
*/
@Service
public class ArticleVisitLogServiceImpl extends ServiceImpl<ArticleVisitLogMapper, ArticleVisitLog> implements ArticleVisitLogService{

    @Resource
    private ArticleVisitLogMapper articleVisitLogMapper;

    @Override
    public Integer statisticLog(LocalDateTime startTime, LocalDateTime endTime) {
        return articleVisitLogMapper.statisticLog(startTime, endTime);
    }

    @Override
    public List<DayArticleStaticsResult> staticsArticleByDays(LocalDate startTime, LocalDate endTime) {
        List<LocalDate> list = new ArrayList<>();
        for(LocalDate i = startTime; !i.isAfter(endTime); i = i.plusDays(1)){
            list.add(i);
        }
        return articleVisitLogMapper.staticsArticleByDays(list);
    }
}




