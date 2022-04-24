package com.eicas.cms.pojo.param;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DayArticleStaticsResult{
    private LocalDate day;
    private Integer visitNum;
}
