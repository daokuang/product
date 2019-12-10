package com.amir.service.emp;

import com.amir.controller.emp.vo.EmpSubsibyMonthlyVo;
import com.amir.mapper.emp.EmpSubsibyMonthlyMapper;
import com.amir.model.Page;
import com.amir.model.emp.EmpSubsibyMonthly;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Created by liuyq on 2019/9/11.
 */
@Service
public class EmpSubsibyMonthlyService {

    @Resource
    private EmpSubsibyMonthlyMapper empSubsibyMonthlyMapper;

    public Integer save(EmpSubsibyMonthly empSubsibyMonthly) {
        return Optional.ofNullable(empSubsibyMonthly).map(t -> {
            empSubsibyMonthlyMapper.insertSelective(t);
            return t.getId();
        }).orElse(0);
    }

    public EmpSubsibyMonthly getByNoAndType(String billNo, Integer type) {
        return Optional.ofNullable(billNo).map(t -> {
            EmpSubsibyMonthly empSubsibyMonthly = empSubsibyMonthlyMapper.getByNo(t, type);
            return empSubsibyMonthly;
        }).orElse(null);
    }

    public Integer update(EmpSubsibyMonthly empSubsibyMonthly) {
        return Optional.ofNullable(empSubsibyMonthly).map(t -> {
            empSubsibyMonthlyMapper.updateByPrimaryKey(t);
            return t.getId();
        }).orElse(0);
    }

    public EmpSubsibyMonthly getByID(Integer id) {
        return Optional.ofNullable(id).map(t -> {
            EmpSubsibyMonthly empSubsibyMonthly = empSubsibyMonthlyMapper.selectByPrimaryKey(t);
            return empSubsibyMonthly;
        }).orElse(null);
    }


    public Page<EmpSubsibyMonthlyVo> listPage(String yearMonth, String empName, String billNo, String deptName, Integer isConfirm,
                                              Integer type, Page page) {
        PageHelper.startPage(page.getPage(), page.getRp());
        List<EmpSubsibyMonthlyVo> empSubsibyMonthlyVos = empSubsibyMonthlyMapper.list(yearMonth, empName, billNo, deptName, isConfirm, type);
        PageInfo pageInfo = new PageInfo(empSubsibyMonthlyVos);
        pageInfo.setList(empSubsibyMonthlyVos);
        return new Page<>(pageInfo);
    }

    public List<EmpSubsibyMonthlyVo> list(String yearMonth, String empName, String billNo, String deptName, Integer isConfirm,
                                          Integer type) {

        return empSubsibyMonthlyMapper.list(yearMonth, empName, billNo, deptName, isConfirm,
                type);
    }

    public Integer confirm(List<String> ids) {
        return empSubsibyMonthlyMapper.confirm(ids);
    }


    /**
     * 根据月份 名称 类型 获取补贴金额
     *
     * @return
     */
    public BigDecimal getSumSubsiby(String yearMonth, Integer empID, Integer type) {
        Double sumTimeSalary = empSubsibyMonthlyMapper.getSumSubsiby(yearMonth, empID, type);
        return BigDecimal.valueOf(sumTimeSalary != null ? sumTimeSalary : 0);
    }
}
