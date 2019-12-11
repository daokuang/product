package com.amir.model.common;

import com.amir.enums.PlatformSourceEnum;

import java.util.List;

public class AppXaResultHelper extends XaResultHelper {
    public AppXaResultHelper() {
    }

    public static <K> XaResult<List<K>> success(Page page) {
        XaResult<List<K>> xaResult = new XaResult();
        xaResult.setSuccess(page.getRows());
        xaResult.setPage(new PageRequest(page, PlatformSourceEnum.APP));
        return xaResult;
    }

    public static <K> XaResult<List<K>> success(Page page, List<K> contents) {
        XaResult<List<K>> xaResult = new XaResult();
        xaResult.setSuccess(contents);
        xaResult.setPage(new PageRequest(page, PlatformSourceEnum.APP));
        return xaResult;
    }
}
