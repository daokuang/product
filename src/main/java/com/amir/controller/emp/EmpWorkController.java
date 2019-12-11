package com.amir.controller.emp;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.amir.controller.base.ProductBaseController;
import com.amir.model.common.XaResult;
import com.amir.model.emp.EmpWork;
import com.amir.service.emp.EmpWorkService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by liuyq on 2019/8/26.
 */
@RestController
@RequestMapping(value = "/empWork")
public class EmpWorkController extends ProductBaseController {


    @Resource
    private EmpWorkService empWorkService;


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public XaResult<List<EmpWork>> list() {
        List<EmpWork> works = empWorkService.getList();
        return XaResult.success(works);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public XaResult<Integer> add(String name) {
        if (StringUtils.isEmpty(name)) return XaResult.error("请输入新增工种名称");

        if (null != empWorkService.getByName(name)) {
            return XaResult.error("工种已存在");
        }
        EmpWork empWork = new EmpWork();
        empWork.setLastModifyTime(new Date());
        empWork.setIsDelete(0);
        empWork.setName(name);
        empWork.setCreateTime(new Date());
        return XaResult.success(empWorkService.add(empWork));
    }

    @RequestMapping(value = "/updateList", method = RequestMethod.POST)
    public XaResult<Integer> updateList(String empWorks) {
        if (StringUtils.isEmpty(empWorks)) return XaResult.error("请输入要更新的工种");

        List<EmpWork> empWorks1 = JSONObject.parseArray(empWorks, EmpWork.class);


        empWorks1.stream().filter(t -> t != null && null == empWorkService.getByName(t.getName())).forEach(t -> {
            t.setLastModifyTime(new Date());
            empWorkService.update(t);
        });
        return XaResult.success(empWorks1.size());
    }
}
