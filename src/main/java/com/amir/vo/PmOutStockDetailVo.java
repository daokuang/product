package com.amir.vo;

import com.amir.model.order.OrderProduct;
import com.amir.model.product.ProductPm;

public class PmOutStockDetailVo {

    private String pmNo;

    private String pmName;

    private Double sum;//总数

    private String unit;
    //以下 返回参数  件/双耗料/需用料数量  接口专用
    private String type;
    private String remark;
    private Double num;//单位数
    private Double unitNum;//倒数

    public PmOutStockDetailVo() {
    }

    public PmOutStockDetailVo(OrderProduct orderProduct, ProductPm productPm, Boolean isMore) {
        if (isMore) {
            this.type = productPm.getType();
            this.remark = productPm.getRemark();
            this.num = productPm.getNum().doubleValue();
            this.unitNum = productPm.getUnitNum().doubleValue();
        }
        this.sum = productPm.getNum().doubleValue() * orderProduct.getNum();
        this.pmNo = productPm.getPmNo();
        this.pmName = productPm.getPmName();
        this.unit = productPm.getUnit();
    }

    public String getPmNo() {
        return pmNo;
    }

    public void setPmNo(String pmNo) {
        this.pmNo = pmNo;
    }

    public String getPmName() {
        return pmName;
    }

    public void setPmName(String pmName) {
        this.pmName = pmName;
    }

    public Double getNum() {
        return num;
    }

    public void setNum(Double num) {
        this.num = num;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Double getUnitNum() {
        return unitNum;
    }

    public void setUnitNum(Double unitNum) {
        this.unitNum = unitNum;
    }
}
