package com.amir.model;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public class XaResultHelper {
    public XaResultHelper() {
    }

    public static <R> XaResult<R> success(R object) {
        return XaResult.success(object);
    }

    public static <R> XaResult<R> success(R object, Map<String, Object> map) {
        return XaResult.success(object, map);
    }

    public static <K> XaResult<List<K>> success(org.springframework.data.domain.Page page) {
        return XaResult.success(page);
    }

    public static <K> XaResult<List<K>> success(Page page, List<K> contents) {
        return XaResult.success(page, contents);
    }

    public static <R> XaResult<R> success() {
        return XaResult.success();
    }

    public static <R> XaResult<R> error(String message) {
        return XaResult.error(message);
    }

    public static <R> XaResult<R> error(String message, R object) {
        return XaResult.error(message, object);
    }

    public static <R> XaResult<R> unlogin() {
        return XaResult.unlogin();
    }

    public static <R> XaResult<R> noAuthority() {
        return XaResult.noAuthority();
    }

    public static <R> XaResult<R> repeat() {
        return XaResult.repeat();
    }

    public static <R> XaResult<R> unSend() {
        return XaResult.unSend();
    }

    public static <R> XaResult<R> unCheck() {
        return XaResult.unCheck();
    }
}