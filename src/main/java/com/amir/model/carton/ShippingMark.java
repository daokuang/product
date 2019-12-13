package com.amir.model.carton;

import java.io.Serializable;
import java.util.Date;

public class ShippingMark implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private Integer customerId;
    private String productNo;
    private Integer outsideMarkId;
    private Integer insideMarkId;
    private Date createTime;
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo == null ? null : productNo.trim();
    }

    public Integer getOutsideMarkId() {
        return outsideMarkId;
    }

    public void setOutsideMarkId(Integer outsideMarkId) {
        this.outsideMarkId = outsideMarkId;
    }

    public Integer getInsideMarkId() {
        return insideMarkId;
    }

    public void setInsideMarkId(Integer insideMarkId) {
        this.insideMarkId = insideMarkId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        ShippingMark other = (ShippingMark) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getCustomerId() == null ? other.getCustomerId() == null : this.getCustomerId().equals(other.getCustomerId()))
                && (this.getProductNo() == null ? other.getProductNo() == null : this.getProductNo().equals(other.getProductNo()))
                && (this.getOutsideMarkId() == null ? other.getOutsideMarkId() == null : this.getOutsideMarkId().equals(other.getOutsideMarkId()))
                && (this.getInsideMarkId() == null ? other.getInsideMarkId() == null : this.getInsideMarkId().equals(other.getInsideMarkId()))
                && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
                && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCustomerId() == null) ? 0 : getCustomerId().hashCode());
        result = prime * result + ((getProductNo() == null) ? 0 : getProductNo().hashCode());
        result = prime * result + ((getOutsideMarkId() == null) ? 0 : getOutsideMarkId().hashCode());
        result = prime * result + ((getInsideMarkId() == null) ? 0 : getInsideMarkId().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }
}