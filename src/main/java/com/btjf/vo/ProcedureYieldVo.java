package com.btjf.vo;

import com.btjf.common.utils.DateUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ProcedureYieldVo {

    private String yearMonth;
    private String name;
    private String dept;
    private String work;
    private String orderNo;
    private String productNo;
    private String productionNo;
    private String procedureName;
    private Double num;
    private Double price;
    private Double money;
    private String date;
    private String checker;

    private Date sortTime;
    private Integer confirmed;//是否质检
    private Double workOutput;//工作产出

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
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

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getProductionNo() {
        return productionNo;
    }

    public void setProductionNo(String productionNo) {
        this.productionNo = productionNo;
    }

    public String getProcedureName() {
        return procedureName;
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }

    public Double getNum() {
        return num;
    }

    public void setNum(Double num) {
        this.num = num;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = DateUtil.dateToString(date, DateUtil.ymdhmFormat);
    }

    public String getChecker() {
        return checker;
    }

    public void setChecker(String checker) {
        this.checker = checker;
    }

    public Date getSortTime() {
        return sortTime;
    }

    public Integer getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Integer confirmed) {
        this.confirmed = confirmed;
    }

    public Double getWorkOutput() {
        return workOutput;
    }

    public void setWorkOutput(Double workOutput) {
        this.workOutput = workOutput;
    }

    public void setSortTime(Date sortTime) {
        //纯排序字段  不需要返回值
        this.sortTime = sortTime;
    }
}
