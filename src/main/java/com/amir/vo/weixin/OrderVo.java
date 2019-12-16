package com.amir.vo.weixin;

import java.util.List;

public class OrderVo {

    private String orderNo;
    private String date;
    private Boolean changed;//是否全部工序已调整
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

    public Boolean getChanged() {
        return changed;
    }

    public void setChanged(Boolean changed) {
        this.changed = changed;
    }

    public List<OrderProductVo> getList() {
        return list;
    }

    public void setList(List<OrderProductVo> list) {
        this.list = list;
    }
}
