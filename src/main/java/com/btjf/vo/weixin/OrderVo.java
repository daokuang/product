package com.btjf.vo.weixin;

import java.util.List;

public class OrderVo {

    private String orderNo;
    private String date;
    private List<OrderProductVo> list;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<OrderProductVo> getList() {
        return list;
    }

    public void setList(List<OrderProductVo> list) {
        this.list = list;
    }
}
