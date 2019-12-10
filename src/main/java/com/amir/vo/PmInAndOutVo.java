package com.amir.vo;

import com.btjf.common.utils.DateUtil;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PmInAndOutVo {

    private Integer id;

    private String pmNo;

    private String name;

    private BigDecimal num;//存数

    private BigDecimal inNum;//入数

    private BigDecimal outNum;//出数

    private String remark;

    private String date;

    private String dateTime;

    private String supplier;

    private String operator;

    private String orderNo;

    private String productNo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPmNo() {
        return pmNo;
    }

    public void setPmNo(String pmNo) {
        this.pmNo = pmNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getNum() {
        return num;
    }

    public void setNum(BigDecimal num) {
        this.num = num;
    }

    public BigDecimal getInNum() {
        return inNum;
    }

    public void setInNum(BigDecimal inNum) {
        this.inNum = inNum;
    }

    public BigDecimal getOutNum() {
        return outNum;
    }

    public void setOutNum(BigDecimal outNum) {
        this.outNum = outNum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
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

    public String getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = DateUtil.dateToString(date, new SimpleDateFormat("yyyy/MM/dd"));
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = DateUtil.dateToString(dateTime, new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"));
    }
}
