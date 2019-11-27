package com.btjf.controller.order.vo;

import org.omg.PortableInterceptor.INACTIVE;

import java.io.Serializable;

/**
 * Created by liuyq on 2019/8/6.
 */
public class OrderVo implements Serializable {


    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer orderId;

    private String customerName;

    private String orderNo;

    private String productNo;

    private Integer num;

    private String unit;

    private String maxNum;

    private String type;

    private String createTime;

    private String completeDate;

    private String remark;

    private Double blanking;//下料车间 工序数

    private Double frontFm; //前道车间 --负面

    private Double frontCheck;//前道车间 质检

    private Double backBigAssist;//后到大复工

    private Double backCenterAssist;//后道中中复工

    private Double assist;//外协质检

    private Double inspection;//成品质检

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(String maxNum) {
        this.maxNum = maxNum;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCompleteDate() {
        return completeDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Double getBlanking() {
        return blanking;
    }

    public void setBlanking(Double blanking) {
        this.blanking = blanking;
    }

    public Double getFrontFm() {
        return frontFm;
    }

    public void setFrontFm(Double frontFm) {
        this.frontFm = frontFm;
    }

    public Double getFrontCheck() {
        return frontCheck;
    }

    public void setFrontCheck(Double frontCheck) {
        this.frontCheck = frontCheck;
    }

    public Double getBackBigAssist() {
        return backBigAssist;
    }

    public void setBackBigAssist(Double backBigAssist) {
        this.backBigAssist = backBigAssist;
    }

    public Double getBackCenterAssist() {
        return backCenterAssist;
    }

    public void setBackCenterAssist(Double backCenterAssist) {
        this.backCenterAssist = backCenterAssist;
    }

    public Double getAssist() {
        return assist;
    }

    public void setAssist(Double assist) {
        this.assist = assist;
    }

    public Double getInspection() {
        return inspection;
    }

    public void setInspection(Double inspection) {
        this.inspection = inspection;
    }

    public void setCompleteDate(String completeDate) {
        this.completeDate = completeDate;
    }


    public static class ProcessDetail{
        private String processName;

        private Integer num;

        private Integer pencent;

        public String getProcessName() {
            return processName;
        }

        public void setProcessName(String processName) {
            this.processName = processName;
        }

        public Integer getNum() {
            return num;
        }

        public void setNum(Integer num) {
            this.num = num;
        }

        public Integer getPencent() {
            return pencent;
        }

        public void setPencent(Integer pencent) {
            this.pencent = pencent;
        }
    }
}
