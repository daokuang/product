package com.amir.model.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wordnik.swagger.annotations.ApiModelProperty;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class XaResult<T> implements Serializable {
    private static final long serialVersionUID = -6162800414401807226L;
    @ApiModelProperty("code : 返回代码，1表示OK，其它的都有对应问题   9表示 未登陆")
    private int code = 1;
    @ApiModelProperty("message : 如果code!=1,错误信息")
    private String message = "成功!";
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty("code为1时返回结果集")
    private T object = null;
    @ApiModelProperty("附加信息")
    private Map<String, Object> map;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty("分页信息")
    private PageRequest page = null;

    public XaResult(String errorMsg) {
        this.message = errorMsg;
        this.code = 0;
        this.object = null;
        this.map = new HashMap<>();
    }

    public XaResult(int code, String message, T object, Map<String, Object> map) {
        this.code = code;
        this.message = message;
        this.object = object;
        this.map = map;
    }


    public XaResult() {
        this.object = null;
        this.map = new HashMap<>();
    }

    public static <R> XaResult<R> success(R object) {
        XaResult<R> xaResult = new XaResult<>();
        xaResult.setSuccess(object);
        return xaResult;
    }

    public static <R> XaResult<R> success(R object, Map<String, Object> map) {
        XaResult<R> xaResult = new XaResult<>();
        xaResult.setSuccess(object);
        xaResult.setMap(map);
        return xaResult;
    }

    public static <R> XaResult<R> success() {
        XaResult<R> xaResult = new XaResult<>();
        xaResult.setSuccess();
        return xaResult;
    }

    public static <R> XaResult<R> error(String message) {
        XaResult<R> xaResult = new XaResult<>();
        xaResult.setError(message == null ? "网络不给力" : message);
        setHeaderCode();
        return xaResult;
    }

    public static <R> XaResult<R> error(String message, R object) {
        XaResult<R> xaResult = new XaResult<>();
        xaResult.setError(message == null ? "网络不给力" : message);
        xaResult.setObject(object);
        setHeaderCode();
        return xaResult;
    }

    public static <R> XaResult<R> unlogin() {
        XaResult<R> xaResult = new XaResult<>();
        xaResult.setCode(401);
        xaResult.setMessage("请登录后再尝试！");
        return xaResult;
    }

    public static <R> XaResult<R> unloginForNoAccessToken() {
        XaResult<R> xaResult = new XaResult<>();
        xaResult.setCode(402);
        xaResult.setMessage("请登录后再尝试！");
        return xaResult;
    }

    public static <R> XaResult<R> noAuthority() {
        XaResult<R> xaResult = new XaResult<>();
        xaResult.setCode(0);
        xaResult.setMessage("您无权操作该功能！");
        return xaResult;
    }

    public static <R> XaResult<R> repeat() {
        XaResult<R> xaResult = new XaResult<>();
        xaResult.setCode(3);
        xaResult.setMessage("请勿重复提交");
        return xaResult;
    }

    public static <R> XaResult<R> unSend() {
        XaResult<R> xaResult = new XaResult<>();
        xaResult.setCode(0);
        xaResult.setMessage("您使用验证码次数已达上限，请稍后再试");
        return xaResult;
    }

    public static <R> XaResult<R> unCheck() {
        XaResult<R> xaResult = new XaResult<>();
        xaResult.setCode(0);
        xaResult.setMessage("您使用验证码次数已达上限，请稍后再试");
        return xaResult;
    }

    public static void setHeaderCode() {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        response.setHeader("reCode", "0");
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getObject() {
        return this.object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    public Map<String, Object> getMap() {
        return this.map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public void setSuccess() {
        this.code = 1;
        this.message = "成功!";
    }

    public void setSuccess(T object) {
        this.code = 1;
        this.message = "成功!";
        this.object = object;
        this.map = new HashMap<>();
    }

    public void setError() {
        this.code = 0;
        this.message = "系统错误!";
        this.object = null;
        this.map = new HashMap<>();
    }

    public void setError(String message) {
        this.code = 0;
        this.message = message;
        this.object = null;
        this.map = new HashMap<>();
    }

    public void setError(Integer code, String message) {
        this.code = code;
        this.message = message;
        this.object = null;
        this.map = new HashMap<>();
    }

    public PageRequest getPage() {
        return this.page;
    }

    public void setPage(PageRequest page) {
        this.page = page;
    }
}