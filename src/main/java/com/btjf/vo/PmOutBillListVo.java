package com.btjf.vo;

public class PmOutBillListVo {

    private Integer id;//领料单ID
    private String orderNo;
    private String productNo;
    private String billNo;
    private String groupNmae;
    private String pmType;
    private String pmCheckItem;
    private Integer sum;
    private String billDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getGroupNmae() {
        return groupNmae;
    }

    public void setGroupNmae(String groupNmae) {
        this.groupNmae = groupNmae;
    }

    public String getPmType() {
        return pmType;
    }

    public void setPmType(String pmType) {
        this.pmType = pmType;
    }

    public String getPmCheckItem() {
        return pmCheckItem;
    }

    public void setPmCheckItem(String pmCheckItem) {
        this.pmCheckItem = pmCheckItem;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }
}
