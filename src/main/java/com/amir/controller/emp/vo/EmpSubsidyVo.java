package com.amir.controller.emp.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by liuyq on 2019/9/22.
 */
public class EmpSubsidyVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String empName;
    private Integer deptId;
    private String deptName;
    private String workName;
    private BigDecimal ylbx;
    private BigDecimal sybx;
    private BigDecimal yiliaobx;
    private BigDecimal gjj;
    private BigDecimal sumDeduction;

    private String entryDate;//入职时间
    private String remark;// 备注
    private String workAge;
    private BigDecimal ageSubsidy;//每月补贴

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    public BigDecimal getYlbx() {
        return ylbx;
    }

    public void setYlbx(BigDecimal ylbx) {
        this.ylbx = ylbx;
    }

    public BigDecimal getSybx() {
        return sybx;
    }

    public void setSybx(BigDecimal sybx) {
        this.sybx = sybx;
    }

    public BigDecimal getYiliaobx() {
        return yiliaobx;
    }

    public void setYiliaobx(BigDecimal yiliaobx) {
        this.yiliaobx = yiliaobx;
    }

    public BigDecimal getGjj() {
        return gjj;
    }

    public void setGjj(BigDecimal gjj) {
        this.gjj = gjj;
    }

    public BigDecimal getSumDeduction() {
        return sumDeduction;
    }

    public void setSumDeduction(BigDecimal sumDeduction) {
        this.sumDeduction = sumDeduction;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getWorkAge() {
        return workAge;
    }

    public void setWorkAge(String workAge) {
        this.workAge = workAge;
    }

    public BigDecimal getAgeSubsidy() {
        return ageSubsidy;
    }

    public void setAgeSubsidy(BigDecimal ageSubsidy) {
        this.ageSubsidy = ageSubsidy;
    }
}
