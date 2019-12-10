package com.amir.vo;

import com.amir.model.emp.EmpSalaryMonthly;

public class EmpSubsidyVo {

    private Integer id;
    private String name;
    private String dept;
    private String work;
    private Double subsidy;

    public EmpSubsidyVo() {
    }

    public EmpSubsidyVo(EmpSalaryMonthly t) {
        this.id = t.getId();
        this.name = t.getEmpName();
        this.dept = t.getDeptName();
        this.work = t.getWorkName();
        this.subsidy = t.getScore() == null ? 0 : t.getScore().doubleValue();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public Double getSubsidy() {
        return subsidy;
    }

    public void setSubsidy(Double subsidy) {
        this.subsidy = subsidy;
    }
}
