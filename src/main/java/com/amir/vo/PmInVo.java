package com.amir.vo;

import com.amir.util.DateUtil;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PmInVo {

    private Integer id;

    private String pmNo;

    private String name;

    private String type;

    private BigDecimal num;

    private String unit;

    private String remark;

    private String inDate;

    private Boolean allowIn = Boolean.TRUE;

    private String supplier;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getNum() {
        return num;
    }

    public void setNum(BigDecimal num) {
        this.num = num;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getInDate() {
        return inDate;
    }

    public void setInDate(Date inDate) {
        this.inDate = DateUtil.dateToString(inDate, new SimpleDateFormat("yyyy/MM/dd"));
    }

    public Boolean getAllowIn() {
        return allowIn;
    }

    public void setAllowIn(Boolean allowIn) {
        this.allowIn = allowIn;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }
}
