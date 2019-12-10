package com.amir.vo;

import com.amir.model.pm.PmOutBillDetail;
import com.amir.model.product.ProductPm;

public class BillPmVo {

    private String pmNo;
    private String pmName;
    private Double sum;//总数
    private String unit;
    private Double allowNum;//可领用
    private String pmBatchNo;//材料批次号
    private String remark;

    public BillPmVo(PmOutBillDetail pmOutBillDetail, Integer maxNum, ProductPm pm) {
        pmName = pmOutBillDetail.getPmName();
        pmNo = pmOutBillDetail.getPmNo();
        sum = maxNum * pm.getNum().doubleValue();
        unit = pmOutBillDetail.getUnit();
        allowNum = pmOutBillDetail.getNum().doubleValue();
        pmBatchNo = pmOutBillDetail.getPmBatchNo();
        remark = pm.getRemark();

    }

    public BillPmVo(PmOutBillDetail pmOutBillDetail) {

    }

    public BillPmVo() {

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

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Double getAllowNum() {
        return allowNum;
    }

    public void setAllowNum(Double allowNum) {
        this.allowNum = allowNum;
    }

    public String getPmBatchNo() {
        return pmBatchNo;
    }

    public void setPmBatchNo(String pmBatchNo) {
        this.pmBatchNo = pmBatchNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
