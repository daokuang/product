package com.amir.vo.weixin;

import java.util.Date;
import java.util.List;

public class EmpDayWorkDetailVo {

    private String orderNo;
    private String productNo;
    private String billNo;
    private Integer type;//0 领料单 1生产单 2罗
    private Integer luoId;
    private String statusDesc;
    private Date time;
    private List<ProcedureInfoVo> procedureInfoVoList;


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

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public List<ProcedureInfoVo> getProcedureInfoVoList() {
        return procedureInfoVoList;
    }

    public void setProcedureInfoVoList(List<ProcedureInfoVo> procedureInfoVoList) {
        this.procedureInfoVoList = procedureInfoVoList;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getLuoId() {
        return luoId;
    }

    public void setLuoId(Integer luoId) {
        this.luoId = luoId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
