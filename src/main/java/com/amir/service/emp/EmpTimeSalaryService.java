package com.amir.service.emp;

import com.amir.mapper.emp.EmpTimesalaryMonthlyMapper;
import com.amir.model.emp.EmpTimesalaryMonthly;
import com.amir.vo.EmpTimesalaryMonthlyVo;
import com.btjf.common.page.Page;
import com.btjf.common.utils.BeanUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class EmpTimeSalaryService {

    @Resource
    private EmpTimesalaryMonthlyMapper empTimesalaryMonthlyMapper;


    public EmpTimesalaryMonthly findByBillNo(String billNo) {
        List<EmpTimesalaryMonthly> list = empTimesalaryMonthlyMapper.findByBillNo(billNo);
        if (list == null || list.size() < 1) {
            return null;
        } else {
            return list.get(0);
        }
    }

    public void create(EmpTimesalaryMonthly empTimesalaryMonthly) {
        empTimesalaryMonthlyMapper.insert(empTimesalaryMonthly);
    }

    public void confirm(Integer[] ids) {
        List<Integer> list = new ArrayList<>(Arrays.asList(ids));
        empTimesalaryMonthlyMapper.confirm(list);
    }

    public Page<EmpTimesalaryMonthlyVo> findList(String yearMonth, String empName, String deptName, String billNo,
                                                 Integer isConfirm, Page page) {
        PageHelper.startPage(page.getPage(), page.getRp());
        List<EmpTimesalaryMonthly> pmList = empTimesalaryMonthlyMapper.findList(yearMonth, empName, deptName, billNo, isConfirm);
        List<EmpTimesalaryMonthlyVo> voList = BeanUtil.convertList(pmList, EmpTimesalaryMonthlyVo.class);
        PageInfo pageInfo = new PageInfo(voList);
        pageInfo.setList(voList);
        return new Page<>(pageInfo);
    }

    public void update(EmpTimesalaryMonthly empTimesalaryMonthly) {
        empTimesalaryMonthlyMapper.updateByPrimaryKey(empTimesalaryMonthly);
    }

    public EmpTimesalaryMonthly get(Integer id) {
        return empTimesalaryMonthlyMapper.selectByPrimaryKey(id);
    }

    public List<EmpTimesalaryMonthlyVo> findExceptList(String yearMonth, String empName, String deptName,
                                                       String billNo, Integer isConfirm) {

        List<EmpTimesalaryMonthly> pmList = empTimesalaryMonthlyMapper.findList(yearMonth, empName, deptName, billNo, isConfirm);
        List<EmpTimesalaryMonthlyVo> voList = BeanUtil.convertList(pmList, EmpTimesalaryMonthlyVo.class);
        return voList;
    }

    public BigDecimal getTimeSalary(String yearMonth, Integer empId) {
        Double sumTimeSalary = empTimesalaryMonthlyMapper.getTimeSalary(yearMonth, empId);
        return BigDecimal.valueOf(sumTimeSalary != null ? sumTimeSalary : 0);
    }
}
