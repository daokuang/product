package com.amir.product.common.exception;

/**
 * @author L.C
 * @version 0.0.1
 * @date 2016/10/17
 * @time 下午5:00
 * @function 功能:
 * @describe 版本描述:
 * @modifyLog 修改日志:
 */
public class BusinessException extends PDException {
    /**
     * 异常
     */
    public BusinessException() {
        super();
    }

    /**
     * @param message
     *            异常消息
     * @param cause
     *            异常原因
     */
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     *            异常消息
     */
    public BusinessException(String message) {
        super(message);
    }

    /**
     * @param cause
     *            异常原因
     */
    public BusinessException(Throwable cause) {
        super(cause);
    }
}
