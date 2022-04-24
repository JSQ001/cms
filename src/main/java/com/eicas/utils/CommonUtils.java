package com.eicas.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author osnudt
 * @since 2022/4/22
 */


public class CommonUtils {
    /**
     * 将LocalDateTime转换为Cron表达式
     * @param ldt
     * @return
     */
    public static String LocalDateTimeConvertToCron(LocalDateTime ldt) {

        final String CRON_DATE_FORMAT = "ss mm HH * * *";
        DateTimeFormatter df = DateTimeFormatter.ofPattern(CRON_DATE_FORMAT);
        return df.format(ldt);
    }
}
