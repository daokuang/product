package com.amir.controller.order.vo;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.amir.model.order.OrderProduct;
import com.amir.model.product.ProductProcedureWorkshop;
import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liuyq on 2019/8/8.
 */
public class OrderProductVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer orderId;

    private String productNo;

    private Integer maxNum;

    private List<WorkShopVo.Procedure> procedures;

    public OrderProductVo(OrderProduct orderProduct, List<ProductProcedureWorkshop> productProcedureWorkshops) {
        if (null != orderProduct) {
            this.id = orderProduct.getId();
            this.orderId = orderProduct.getOrderId();
            this.productNo = orderProduct.getProductNo();
            this.maxNum = orderProduct.getMaxNum();
        }
        if (!CollectionUtils.isEmpty(productProcedureWorkshops)) {
            procedures = Lists.newArrayList();
            productProcedureWorkshops.forEach(t -> {
                procedures.add(new WorkShopVo.Procedure(t));
            });
        }

    }

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

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public Integer getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(Integer maxNum) {
        this.maxNum = maxNum;
    }

    public List<WorkShopVo.Procedure> getProcedures() {
        return procedures;
    }

    public void setProcedures(List<WorkShopVo.Procedure> procedures) {
        this.procedures = procedures;
    }
}
