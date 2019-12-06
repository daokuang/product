package com.btjf.controller.productionorder.vo;

import java.io.Serializable;

/**
 * Created by liuyq on 2019/8/8.
 */
public class ProductionOrderVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String orderNo;

    private String productionNo;

    private String customerName;

    private String productNo;

    private Integer assignNum;

    private String unit;

    private Integer maxNum;

    private String productType;

    private String workshop;

    private String workshopDirector;

    private String createTime;

    private String createStartTime;

    private String createEndTime;

    private Integer type;


    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getProductionNo() {
        return productionNo;
    }

    public void setProductionNo(String productionNo) {
        this.productionNo = productionNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public Integer getAssignNum() {
        return assignNum;
    }

    public void setAssignNum(Integer assignNum) {
        this.assignNum = assignNum;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(Integer maxNum) {
        this.maxNum = maxNum;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getWorkshop() {
        return workshop;
    }

    public void setWorkshop(String workshop) {
        this.workshop = workshop;
    }

    public String getWorkshopDirector() {
        return workshopDirector;
    }

    public void setWorkshopDirector(String workshopDirector) {
        this.workshopDirector = workshopDirector;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateStartTime() {
        return createStartTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setCreateStartTime(String createStartTime) {
        this.createStartTime = createStartTime;
    }

    public String getCreateEndTime() {
        return createEndTime;
    }

    public void setCreateEndTime(String createEndTime) {
        this.createEndTime = createEndTime;
    }
}
