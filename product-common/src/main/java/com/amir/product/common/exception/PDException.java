package com.amir.product.common.exception;

/**
 * Created by liuyq on 2019/7/27.
 */
public class PDException extends RuntimeException {
    public PDException() {
    }

    public PDException(String message, Throwable cause) {
        super(message, cause);
    }

    public PDException(String message) {
        super(message);
    }

    public PDException(Throwable cause) {
        super(cause);
    }
}
