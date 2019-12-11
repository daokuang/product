package com.amir.controller.base;

import com.amir.controller.weixin.vo.WxEmpVo;
import com.amir.exception.BusinessException;
import com.amir.interceptor.LoginInfoCache;
import com.amir.interceptor.LoginInterceptor;
import com.amir.model.common.XaResult;
import com.amir.model.sys.SysUser;
import com.amir.util.RdStringUtil;
import org.apache.log4j.Logger;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;

/**
 * Created by liuyq on 2019/7/28.
 */
public abstract class ProductBaseController {
    private static final Logger LOGGER = Logger
            .getLogger(ProductBaseController.class);


    @Resource
    private LoginInfoCache loginInfoCache;

    /**
     * 获取当前登录的角色ID
     *
     * @return
     */
    public SysUser getLoginUser() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        String secretKey = request.getHeader(LoginInterceptor.SECRETKEY);
        SysUser sysUser = (SysUser) loginInfoCache.get(secretKey);
        if (null == sysUser) {
            throw new BusinessException("请登录之后重试");
        } else {
            return sysUser;
        }
    }

    /**
     * 获取小程序登入用户信息
     *
     * @return
     */
    public WxEmpVo getWxLoginUser() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        String secretKey = request.getHeader(LoginInterceptor.SECRETKEY);
        WxEmpVo wxEmpVo = (WxEmpVo) loginInfoCache.getForever(secretKey);
        if (null == wxEmpVo) {
            throw new BusinessException("请登录之后重试");
        } else {
            return wxEmpVo;
        }
    }


    public HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public String getRequestParamsAndUrl() {
        String params = "";
        try {
            // 同样的参数顺序请求，在request取值时可能出现顺序不一致的情况，在slb转发 订单重复时，检验无法判断，因此此处需要进行排序
            Enumeration<String> e = getRequest().getParameterNames();
            List<String> list = Collections.list(e);
            Collections.sort(list);
            params += getRequest().getServletPath();
            for (String name : list) {
                String value = paramString(name);
                params += name + "=" + value + "&";
            }

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return params;
    }

    protected String paramString(String str) {
        return RdStringUtil.isNull(getRequest().getParameter(str));
    }

    @ExceptionHandler(Exception.class)
    public
    @ResponseBody
    XaResult<?> handleUncaughtException(Exception exception) { // 系统异常
        LOGGER.error(exception.getMessage(), exception);
        exception.printStackTrace();
        return new XaResult<Object>("系统异常");
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public
    @ResponseBody
    XaResult<?> handleValidationException(
            ConstraintViolationException constraintViolationException) { // 数据校验异常
        Set<ConstraintViolation<?>> set = constraintViolationException.getConstraintViolations();
        Iterator<ConstraintViolation<?>> iterable = set.iterator();
        String message = "";
        while (iterable.hasNext()) {
            ConstraintViolation<?> constraintViolation = iterable.next();
            message = constraintViolation.getMessage();
            break;
        }
        LOGGER.error(message, constraintViolationException);
        return new XaResult<Object>(constraintViolationException.getMessage() == null ? "请检查网络"
                : constraintViolationException.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    public
    @ResponseBody
    XaResult<?> handleBusinessException(BusinessException businessException) { // 业务逻辑异常
        LOGGER.error(businessException.getMessage(), businessException);
        businessException.printStackTrace();
        return new XaResult<Object>(businessException.getMessage() == null ? "请检查网络" : businessException.getMessage());
    }

    @ExceptionHandler(BindException.class)
    public
    @ResponseBody
    XaResult<?> handleBindException(BindException bindException) { // 业务逻辑异常
        LOGGER.error(bindException.getMessage(), bindException);
        System.out.println(bindException.getLocalizedMessage());
        return new XaResult<Object>(bindException.getMessage() == null ? "请检查网络" : bindException.getMessage());
    }

    @ExceptionHandler(HttpMessageNotWritableException.class)
    public
    @ResponseBody
    XaResult<?> handleJSONConvertException(
            HttpMessageNotWritableException httpMessageNotWritableException) { // JSON格式转换异常
        LOGGER.error(httpMessageNotWritableException.getMessage(), httpMessageNotWritableException);
        return new XaResult<Object>("JSON格式转换异常");
    }
}
