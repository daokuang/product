package com.amir.vo;

import java.util.List;

public class PmOutStockDetailListVo {

    private String orderNo;

    private String productNo;

    private Integer num;

    private List<PmOutStockDetailVo> list;

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

    public List<PmOutStockDetailVo> getList() {
        return list;
    }

    public void setList(List<PmOutStockDetailVo> list) {
        this.list = list;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
