package com.btjf.controller.weixin;

import com.alibaba.druid.util.StringUtils;
import com.btjf.application.util.XaResult;
import com.btjf.common.utils.JSONUtils;
import com.btjf.common.utils.MD5Utils;
import com.btjf.controller.base.ProductBaseController;
import com.btjf.controller.weixin.vo.WxEmpVo;
import com.btjf.interceptor.LoginInfoCache;
import com.btjf.model.emp.Emp;
import com.btjf.service.emp.EmpService;
import com.btjf.util.RegUtil;
import com.heige.aikajinrong.business.utils.MD5Util;
import com.wordnik.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by liuyq on 2019/8/18.
 */
@RestController
@Api(value = "WxLoginController", description = "小程序 登入", position = 1)
@RequestMapping(value = "/wx/")
public class WxLoginController extends ProductBaseController{

    @Resource
    private LoginInfoCache loginInfoCache;

    @Resource
    private EmpService empService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public XaResult<WxEmpVo> login(String phone, String password, Integer type) {
        if (null == type || (type != 1 && type != 2)) return XaResult.error("请输入正确的类型");

        if (phone == null) return XaResult.error("请输入手机号码");

        WxEmpVo wxEmpVo = null;
        //普通登入
        if (type == 1) {
            if (password == null) return XaResult.error("请输入密码");

            wxEmpVo = empService.login(phone, MD5Util.getMD5String(password));
            if (wxEmpVo == null) {
               return XaResult.error("用户名或密码错误");
            }
        } else {
            wxEmpVo = empService.login(phone, null);
            if (wxEmpVo == null) return XaResult.error("用户不存在");
        }
        String json = JSONUtils.toJSON(wxEmpVo);
        String key = MD5Utils.ecodeByMD5(json);
        loginInfoCache.add(key, wxEmpVo);
        wxEmpVo.setSecretKey(key);
        return XaResult.success(wxEmpVo);
    }


    @RequestMapping(value = "checkPhoneAndIdCar", method = RequestMethod.GET)
    public XaResult<Emp> checkPhoneAndIdCar(String phone, String idCar) {
        if (phone == null) return XaResult.error("请输入手机号码");

        if (!RegUtil.isMobile(phone)) return XaResult.error("请输入正确的手机号码");
        Emp emp = null;
        if (StringUtils.isEmpty(idCar)) {
            emp = empService.getByPhoneOrIdCard(phone, null);
            if (emp == null) return XaResult.error("您填写的手机号错误，请修改");
        } else {
            emp = empService.getByPhoneOrIdCard(phone, idCar);
            if (emp == null) return XaResult.error("输入的身份证号错误，请修改");
        }
        return XaResult.success(emp);
    }

    @RequestMapping(value = "resetPassWord", method = RequestMethod.POST)
    public XaResult<Integer> resetPassWord(Integer id, String newPassword) {

        if (id == null || StringUtils.isEmpty(newPassword)) return XaResult.error("请输入完整参数");

        Emp emp = empService.getByID(id);
        if (emp == null) return XaResult.error("该用户不存在");

        if(newPassword.length() < 6) return XaResult.error("密码长度最少6位");

        newPassword = MD5Utils.ecodeByMD5(newPassword);

        if (newPassword.equals(emp.getPassword())) return XaResult.error("新密码不能与原密码相同");


        emp.setPassword(newPassword);
        emp.setLastModifyTime(new Date());
        return XaResult.success(empService.update(emp));
    }
}
