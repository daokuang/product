package com.amir.service.salary;

import com.amir.mapper.salary.SalaryMonthlyMapper;
import com.amir.model.common.Page;
import com.amir.model.salary.SalaryMonthly;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SalaryMonthlyService {

    @Resource
    private SalaryMonthlyMapper salaryMonthlyMapper;

    public SalaryMonthly get(Integer id) {
        return salaryMonthlyMapper.selectByPrimaryKey(id);
    }


    public void create(SalaryMonthly salaryMonthly) {
        salaryMonthlyMapper.insertSelective(salaryMonthly);
    }

    public void update(SalaryMonthly salaryMonthly) {
        salaryMonthlyMapper.updateByPrimaryKeySelective(salaryMonthly);
    }

    public Page<SalaryMonthly> list(Page page) {
        PageHelper.startPage(page.getPage(), page.getRp());
        List<SalaryMonthly> pmList = salaryMonthlyMapper.findList();
        PageInfo pageInfo = new PageInfo(pmList);
        pageInfo.setList(pmList);
        return new Page<>(pageInfo);
    }

    public SalaryMonthly getByYearMonth(String yearMonth) {
        return salaryMonthlyMapper.getByYearMonth(yearMonth);
    }
}
