package com.amir.controller.productpm.vo;

import com.amir.model.product.Product;

import java.io.Serializable;

/**
 * Created by liuyq on 2019/8/5.
 */
public class ProductWorkShopVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String productNo;//产品型号

    private String type;//产品类别

    private Integer blanking;//下料车间 工序数

    private Integer assist;//外协工序数

    private Integer groundFloor;// 一楼车间工序数

    private Integer backAssist;//后道辅工

    private Integer backCenterAssist;//后道中辅工

    private Integer backBigAssist;//后道大辅工

    private Integer inspection;// 验货

    private Integer packing;//包装

    private Integer backSmallAssist;// 后道小辅工

    public ProductWorkShopVo(Product t) {
        this.productNo = t.getProductNo();
        this.type = t.getType();
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getBlanking() {
        return blanking;
    }

    public void setBlanking(Integer blanking) {
        this.blanking = blanking;
    }

    public Integer getAssist() {
        return assist;
    }

    public void setAssist(Integer assist) {
        this.assist = assist;
    }

    public Integer getGroundFloor() {
        return groundFloor;
    }

    public void setGroundFloor(Integer groundFloor) {
        this.groundFloor = groundFloor;
    }

    public Integer getBackAssist() {
        return backAssist;
    }

    public void setBackAssist(Integer backAssist) {
        this.backAssist = backAssist;
    }

    public Integer getBackCenterAssist() {
        return backCenterAssist;
    }

    public void setBackCenterAssist(Integer backCenterAssist) {
        this.backCenterAssist = backCenterAssist;
    }

    public Integer getBackBigAssist() {
        return backBigAssist;
    }

    public void setBackBigAssist(Integer backBigAssist) {
        this.backBigAssist = backBigAssist;
    }

    public Integer getInspection() {
        return inspection;
    }

    public void setInspection(Integer inspection) {
        this.inspection = inspection;
    }

    public Integer getPacking() {
        return packing;
    }

    public void setPacking(Integer packing) {
        this.packing = packing;
    }

    public Integer getBackSmallAssist() {
        return backSmallAssist;
    }

    public void setBackSmallAssist(Integer backSmallAssist) {
        this.backSmallAssist = backSmallAssist;
    }
}
