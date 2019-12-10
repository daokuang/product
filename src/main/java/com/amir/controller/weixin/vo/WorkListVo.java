package com.amir.controller.weixin.vo;

import com.amir.controller.order.vo.WorkShopVo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liuyq on 2019/8/19.
 */
public class WorkListVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer orderId;

    private String productNo; //型号

    private String orderNo;//订单编号

    private String productionNo;

    private String billNo;

    private Integer louId;//罗id

    //工序
    private List<WorkShopVo.Procedure> procedures;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getLouId() {
        return louId;
    }

    public void setLouId(Integer louId) {
        this.louId = louId;
    }

    public List<WorkShopVo.Procedure> getProcedures() {
        return procedures;
    }

    public void setProcedures(List<WorkShopVo.Procedure> procedures) {
        this.procedures = procedures;
    }

    public String getProductionNo() {
        return productionNo;
    }

    public void setProductionNo(String productionNo) {
        this.productionNo = productionNo;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }
}
