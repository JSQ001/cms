package com.eicas.common.annotation;

import java.lang.annotation.*;

/**
 * 处理请求头content-type
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
public @interface FormatContentType {
    /**
     * 值
     */
    String[] value() default {};

}
