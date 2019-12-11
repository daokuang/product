package com.amir.service.emp;

import com.amir.mapper.emp.SummarySalaryMonthlyMapper;
import com.amir.model.common.Page;
import com.amir.model.emp.SummarySalaryMonthly;
import com.amir.vo.SubsidyVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by liuyq on 2019/9/18.
 */
@Service
public class SummarySalaryMonthlyService {

    @Resource
    private SummarySalaryMonthlyMapper summarySalaryMonthlyMapper;

    public Integer saveOrUpdate(SummarySalaryMonthly summarySalaryMonthly) {
        return Optional.ofNullable(summarySalaryMonthly).map(v -> {
            SummarySalaryMonthly summarySalaryMonthly1 = summarySalaryMonthlyMapper.getByEmpIDAndYearMonth(summarySalaryMonthly.getEmpId(), summarySalaryMonthly.getYearMonth());
            if (null == summarySalaryMonthly1) {
                summarySalaryMonthly.setIsDelete(0);
                summarySalaryMonthly.setCreateTime(new Date());
                summarySalaryMonthlyMapper.insertSelective(summarySalaryMonthly);
                return summarySalaryMonthly.getId();
            } else {
                summarySalaryMonthly.setId(summarySalaryMonthly1.getId());
                summarySalaryMonthlyMapper.updateByPrimaryKeySelective(summarySalaryMonthly);
                return summarySalaryMonthly1.getId();
            }
        }).orElse(0);
    }

    public List<SummarySalaryMonthly> getList(String yearMonth, String deptName, String empName,
                                              Integer type) {
        List<SummarySalaryMonthly> summarySalaryMonthlies = summarySalaryMonthlyMapper.getList(yearMonth, deptName, empName, type);
        return summarySalaryMonthlies;
    }


    public Page<SummarySalaryMonthly> getPage(String yearMonth, String deptName, String empName,
                                              Integer type, Page page) {
        PageHelper.startPage(page.getPage(), page.getRp());
        List<SummarySalaryMonthly> summarySalaryMonthlies = this.getList(yearMonth, deptName, empName, type);
        PageInfo pageInfo = new PageInfo(summarySalaryMonthlies);
        pageInfo.setList(summarySalaryMonthlies);
        return new Page<>(pageInfo);
    }

    public SummarySalaryMonthly getByYearMonthAndName(String yearMonth, String name) {
        List<SummarySalaryMonthly> summarySalaryMonthlies = this.getList(yearMonth, null, name, null);
        if (CollectionUtils.isNotEmpty(summarySalaryMonthlies)) {
            return summarySalaryMonthlies.get(0);
        }
        return null;
    }

    public void delete(String yearMonth) {
        summarySalaryMonthlyMapper.delete(yearMonth);
    }

    public Page<SubsidyVo> findLatheWorkerSubsidyList(String yearMonth, String empName, String deptName, Page page) {
        PageHelper.startPage(page.getPage(), page.getRp());
        List<SubsidyVo> list = summarySalaryMonthlyMapper.findLatheWorkerSubsidyList(yearMonth, deptName, empName);
        PageInfo pageInfo = new PageInfo(list);
        pageInfo.setList(list);
        return new Page<>(pageInfo);
    }

    public Page<SubsidyVo> findNewLatheWorkerSubsidyList(String yearMonth, String empName, String deptName, Page page) {
        PageHelper.startPage(page.getPage(), page.getRp());
        List<SubsidyVo> list = summarySalaryMonthlyMapper.findNewLatheWorkerSubsidyList(yearMonth, deptName, empName);
        PageInfo pageInfo = new PageInfo(list);
        pageInfo.setList(list);
        return new Page<>(pageInfo);
    }

    public Page<SubsidyVo> findPercentSubsidyList(String yearMonth, String empName, String deptName, Page page) {
        PageHelper.startPage(page.getPage(), page.getRp());
        List<SubsidyVo> list = summarySalaryMonthlyMapper.findPercentSubsidyList(yearMonth, deptName, empName);
        PageInfo pageInfo = new PageInfo(list);
        pageInfo.setList(list);
        return new Page<>(pageInfo);
    }
}
