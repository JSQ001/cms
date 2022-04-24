package com.eicas.common.exception;

import com.eicas.common.ResultCode;
import lombok.Getter;

/**
 * @author jsq
 * @date 2022/03/04 16:43
 * @description: 自定义异常
 */
@Getter
public class APIException extends RuntimeException {
    private final long code;
    private final String message;

    public APIException() {
        this(ResultCode.FAILED);
    }

    public APIException(ResultCode failed) {
        this.code = failed.getCode();
        this.message = failed.getMessage();
    }
}
