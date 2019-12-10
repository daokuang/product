package com.amir.vo;

import java.math.BigDecimal;

public class SubsidyVo {

    private Integer id;
    private String yearMonth;
    private String empName;
    private String deptName;
    private String workName;

    private BigDecimal normalDays;
    private BigDecimal normalPrice;
    private BigDecimal normalMoney;
    private BigDecimal overtimeDays;
    private BigDecimal overtimePrice;
    private BigDecimal overtimeMoney;
    private BigDecimal sum;

    private BigDecimal newLatheWorkerSubsidy;

    private BigDecimal basicSalary;
    private BigDecimal percentSubsidy;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
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

    public BigDecimal getNormalDays() {
        return normalDays;
    }

    public void setNormalDays(BigDecimal normalDays) {
        this.normalDays = normalDays;
    }

    public BigDecimal getNormalPrice() {
        return normalPrice;
    }

    public void setNormalPrice(BigDecimal normalPrice) {
        this.normalPrice = normalPrice;
    }

    public BigDecimal getNormalMoney() {
        return normalMoney;
    }

    public void setNormalMoney(BigDecimal normalMoney) {
        this.normalMoney = normalMoney;
    }

    public BigDecimal getOvertimeDays() {
        return overtimeDays;
    }

    public void setOvertimeDays(BigDecimal overtimeDays) {
        this.overtimeDays = overtimeDays;
    }

    public BigDecimal getOvertimePrice() {
        return overtimePrice;
    }

    public void setOvertimePrice(BigDecimal overtimePrice) {
        this.overtimePrice = overtimePrice;
    }

    public BigDecimal getOvertimeMoney() {
        return overtimeMoney;
    }

    public void setOvertimeMoney(BigDecimal overtimeMoney) {
        this.overtimeMoney = overtimeMoney;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public BigDecimal getNewLatheWorkerSubsidy() {
        return newLatheWorkerSubsidy;
    }

    public void setNewLatheWorkerSubsidy(BigDecimal newLatheWorkerSubsidy) {
        this.newLatheWorkerSubsidy = newLatheWorkerSubsidy;
    }

    public BigDecimal getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(BigDecimal basicSalary) {
        this.basicSalary = basicSalary;
    }

    public BigDecimal getPercentSubsidy() {
        return percentSubsidy;
    }

    public void setPercentSubsidy(BigDecimal percentSubsidy) {
        this.percentSubsidy = percentSubsidy;
    }
}
