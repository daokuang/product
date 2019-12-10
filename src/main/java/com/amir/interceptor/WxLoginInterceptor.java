package com.amir.interceptor;

import com.alibaba.fastjson.JSON;
import com.amir.controller.weixin.vo.WxEmpVo;
import com.amir.model.XaResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

/**
 * 权限过滤器
 *
 * @author dapeng
 */
public class WxLoginInterceptor implements HandlerInterceptor {

    public static final String SECRETKEY = "secretKey";
    @Resource
    private LoginInfoCache loginInfoCache;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String secretKey = request.getHeader(SECRETKEY);
        if (StringUtils.isEmpty(secretKey)) {
            response.setStatus(HttpStatus.PAYMENT_REQUIRED.value());
            response.setContentType("text/xml;charset=UTF-8");
            response.getOutputStream().write(JSON.toJSONStringWithDateFormat(XaResult.unloginForNoAccessToken(), "yyyy-MM-dd HH:mm:ss").getBytes(StandardCharsets.UTF_8));
            return false;
        }
        WxEmpVo wxEmpVo = (WxEmpVo) loginInfoCache.getForever(secretKey);
        if (wxEmpVo == null) {
            response.setStatus(HttpStatus.PAYMENT_REQUIRED.value());
            response.setContentType("text/xml;charset=UTF-8");
            response.getOutputStream().write(JSON.toJSONStringWithDateFormat(XaResult.unloginForNoAccessToken(), "yyyy-MM-dd HH:mm:ss").getBytes(StandardCharsets.UTF_8));
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }


}
