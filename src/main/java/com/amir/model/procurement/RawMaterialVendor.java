package com.amir.model.procurement;

import java.io.Serializable;
import java.util.Date;

public class RawMaterialVendor implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String vendorName;
    private Integer productType;
    private String contactPerson;
    private String factoryAddress;
    private String contactPhone;
    private String contactFax;
    private String contactEMail;
    private String legalRepresentative;
    private String bankName;
    private String bankAccountNum;
    private String vendorShortName;
    private String layerOneCorrugated;
    private String layerTwoCorrugated;
    private String layerThreeCorrugated;
    private String remark;
    private Integer shortNameIndex;
    private Date createTime;
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName == null ? null : vendorName.trim();
    }

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson == null ? null : contactPerson.trim();
    }

    public String getFactoryAddress() {
        return factoryAddress;
    }

    public void setFactoryAddress(String factoryAddress) {
        this.factoryAddress = factoryAddress == null ? null : factoryAddress.trim();
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone == null ? null : contactPhone.trim();
    }

    public String getContactFax() {
        return contactFax;
    }

    public void setContactFax(String contactFax) {
        this.contactFax = contactFax == null ? null : contactFax.trim();
    }

    public String getContactEMail() {
        return contactEMail;
    }

    public void setContactEMail(String contactEMail) {
        this.contactEMail = contactEMail == null ? null : contactEMail.trim();
    }

    public String getLegalRepresentative() {
        return legalRepresentative;
    }

    public void setLegalRepresentative(String legalRepresentative) {
        this.legalRepresentative = legalRepresentative == null ? null : legalRepresentative.trim();
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    public String getBankAccountNum() {
        return bankAccountNum;
    }

    public void setBankAccountNum(String bankAccountNum) {
        this.bankAccountNum = bankAccountNum == null ? null : bankAccountNum.trim();
    }

    public String getVendorShortName() {
        return vendorShortName;
    }

    public void setVendorShortName(String vendorShortName) {
        this.vendorShortName = vendorShortName == null ? null : vendorShortName.trim();
    }

    public String getLayerOneCorrugated() {
        return layerOneCorrugated;
    }

    public void setLayerOneCorrugated(String layerOneCorrugated) {
        this.layerOneCorrugated = layerOneCorrugated == null ? null : layerOneCorrugated.trim();
    }

    public String getLayerTwoCorrugated() {
        return layerTwoCorrugated;
    }

    public void setLayerTwoCorrugated(String layerTwoCorrugated) {
        this.layerTwoCorrugated = layerTwoCorrugated == null ? null : layerTwoCorrugated.trim();
    }

    public String getLayerThreeCorrugated() {
        return layerThreeCorrugated;
    }

    public void setLayerThreeCorrugated(String layerThreeCorrugated) {
        this.layerThreeCorrugated = layerThreeCorrugated == null ? null : layerThreeCorrugated.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getShortNameIndex() {
        return shortNameIndex;
    }

    public void setShortNameIndex(Integer shortNameIndex) {
        this.shortNameIndex = shortNameIndex;
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
        RawMaterialVendor other = (RawMaterialVendor) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getVendorName() == null ? other.getVendorName() == null : this.getVendorName().equals(other.getVendorName()))
                && (this.getProductType() == null ? other.getProductType() == null : this.getProductType().equals(other.getProductType()))
                && (this.getContactPerson() == null ? other.getContactPerson() == null : this.getContactPerson().equals(other.getContactPerson()))
                && (this.getFactoryAddress() == null ? other.getFactoryAddress() == null : this.getFactoryAddress().equals(other.getFactoryAddress()))
                && (this.getContactPhone() == null ? other.getContactPhone() == null : this.getContactPhone().equals(other.getContactPhone()))
                && (this.getContactFax() == null ? other.getContactFax() == null : this.getContactFax().equals(other.getContactFax()))
                && (this.getContactEMail() == null ? other.getContactEMail() == null : this.getContactEMail().equals(other.getContactEMail()))
                && (this.getLegalRepresentative() == null ? other.getLegalRepresentative() == null : this.getLegalRepresentative().equals(other.getLegalRepresentative()))
                && (this.getBankName() == null ? other.getBankName() == null : this.getBankName().equals(other.getBankName()))
                && (this.getBankAccountNum() == null ? other.getBankAccountNum() == null : this.getBankAccountNum().equals(other.getBankAccountNum()))
                && (this.getVendorShortName() == null ? other.getVendorShortName() == null : this.getVendorShortName().equals(other.getVendorShortName()))
                && (this.getLayerOneCorrugated() == null ? other.getLayerOneCorrugated() == null : this.getLayerOneCorrugated().equals(other.getLayerOneCorrugated()))
                && (this.getLayerTwoCorrugated() == null ? other.getLayerTwoCorrugated() == null : this.getLayerTwoCorrugated().equals(other.getLayerTwoCorrugated()))
                && (this.getLayerThreeCorrugated() == null ? other.getLayerThreeCorrugated() == null : this.getLayerThreeCorrugated().equals(other.getLayerThreeCorrugated()))
                && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
                && (this.getShortNameIndex() == null ? other.getShortNameIndex() == null : this.getShortNameIndex().equals(other.getShortNameIndex()))
                && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
                && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getVendorName() == null) ? 0 : getVendorName().hashCode());
        result = prime * result + ((getProductType() == null) ? 0 : getProductType().hashCode());
        result = prime * result + ((getContactPerson() == null) ? 0 : getContactPerson().hashCode());
        result = prime * result + ((getFactoryAddress() == null) ? 0 : getFactoryAddress().hashCode());
        result = prime * result + ((getContactPhone() == null) ? 0 : getContactPhone().hashCode());
        result = prime * result + ((getContactFax() == null) ? 0 : getContactFax().hashCode());
        result = prime * result + ((getContactEMail() == null) ? 0 : getContactEMail().hashCode());
        result = prime * result + ((getLegalRepresentative() == null) ? 0 : getLegalRepresentative().hashCode());
        result = prime * result + ((getBankName() == null) ? 0 : getBankName().hashCode());
        result = prime * result + ((getBankAccountNum() == null) ? 0 : getBankAccountNum().hashCode());
        result = prime * result + ((getVendorShortName() == null) ? 0 : getVendorShortName().hashCode());
        result = prime * result + ((getLayerOneCorrugated() == null) ? 0 : getLayerOneCorrugated().hashCode());
        result = prime * result + ((getLayerTwoCorrugated() == null) ? 0 : getLayerTwoCorrugated().hashCode());
        result = prime * result + ((getLayerThreeCorrugated() == null) ? 0 : getLayerThreeCorrugated().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getShortNameIndex() == null) ? 0 : getShortNameIndex().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }
}