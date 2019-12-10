package com.amir.vo;

import com.amir.util.DateUtil;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PmPlanOutVo {

    private Integer id;

    private String pmNo;

    private String name;

    private BigDecimal outNum;//出数

    private String outInfo;//出库说明

    private String date;

    private String operator;

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

    public BigDecimal getOutNum() {
        return outNum;
    }

    public void setOutNum(BigDecimal outNum) {
        this.outNum = outNum;
    }

    public String getOutInfo() {
        return outInfo;
    }

    public void setOutInfo(String outInfo) {
        this.outInfo = outInfo;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = DateUtil.dateToString(date, new SimpleDateFormat("yyyy/MM/dd"));
    }


}
