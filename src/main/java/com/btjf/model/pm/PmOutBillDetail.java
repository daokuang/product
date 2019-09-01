package com.btjf.model.pm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PmOutBillDetail implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_PMout_Bill_Detail.id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_PMout_Bill_Detail.billId
     *
     * @mbg.generated
     */
    private Integer billId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_PMout_Bill_Detail.pmId
     *
     * @mbg.generated
     */
    private Integer pmId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_PMout_Bill_Detail.pmNo
     *
     * @mbg.generated
     */
    private String pmNo;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_PMout_Bill_Detail.pmName
     *
     * @mbg.generated
     */
    private String pmName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_PMout_Bill_Detail.pmBatchNo
     *
     * @mbg.generated
     */
    private String pmBatchNo;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_PMout_Bill_Detail.num
     *
     * @mbg.generated
     */
    private BigDecimal num;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_PMout_Bill_Detail.perNum
     *
     * @mbg.generated
     */
    private BigDecimal perNum;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_PMout_Bill_Detail.backNum
     *
     * @mbg.generated
     */
    private BigDecimal backNum;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_PMout_Bill_Detail.unit
     *
     * @mbg.generated
     */
    private String unit;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_PMout_Bill_Detail.remark
     *
     * @mbg.generated
     */
    private String remark;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_PMout_Bill_Detail.outInfo
     *
     * @mbg.generated
     */
    private String outInfo;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_PMout_Bill_Detail.receiver
     *
     * @mbg.generated
     */
    private String receiver;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_PMout_Bill_Detail.sender
     *
     * @mbg.generated
     */
    private String sender;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_PMout_Bill_Detail.operator
     *
     * @mbg.generated
     */
    private String operator;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_PMout_Bill_Detail.outDate
     *
     * @mbg.generated
     */
    private Date outDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_PMout_Bill_Detail.createTime
     *
     * @mbg.generated
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_PMout_Bill_Detail.lastModifyTime
     *
     * @mbg.generated
     */
    private Date lastModifyTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_PMout_Bill_Detail.isDelete
     *
     * @mbg.generated
     */
    private Integer isDelete;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_PMout_Bill_Detail
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_PMout_Bill_Detail.id
     *
     * @return the value of t_PMout_Bill_Detail.id
     *
     * @mbg.generated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_PMout_Bill_Detail.id
     *
     * @param id the value for t_PMout_Bill_Detail.id
     *
     * @mbg.generated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_PMout_Bill_Detail.billId
     *
     * @return the value of t_PMout_Bill_Detail.billId
     *
     * @mbg.generated
     */
    public Integer getBillId() {
        return billId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_PMout_Bill_Detail.billId
     *
     * @param billId the value for t_PMout_Bill_Detail.billId
     *
     * @mbg.generated
     */
    public void setBillId(Integer billId) {
        this.billId = billId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_PMout_Bill_Detail.pmId
     *
     * @return the value of t_PMout_Bill_Detail.pmId
     *
     * @mbg.generated
     */
    public Integer getPmId() {
        return pmId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_PMout_Bill_Detail.pmId
     *
     * @param pmId the value for t_PMout_Bill_Detail.pmId
     *
     * @mbg.generated
     */
    public void setPmId(Integer pmId) {
        this.pmId = pmId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_PMout_Bill_Detail.pmNo
     *
     * @return the value of t_PMout_Bill_Detail.pmNo
     *
     * @mbg.generated
     */
    public String getPmNo() {
        return pmNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_PMout_Bill_Detail.pmNo
     *
     * @param pmNo the value for t_PMout_Bill_Detail.pmNo
     *
     * @mbg.generated
     */
    public void setPmNo(String pmNo) {
        this.pmNo = pmNo == null ? null : pmNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_PMout_Bill_Detail.pmName
     *
     * @return the value of t_PMout_Bill_Detail.pmName
     *
     * @mbg.generated
     */
    public String getPmName() {
        return pmName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_PMout_Bill_Detail.pmName
     *
     * @param pmName the value for t_PMout_Bill_Detail.pmName
     *
     * @mbg.generated
     */
    public void setPmName(String pmName) {
        this.pmName = pmName == null ? null : pmName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_PMout_Bill_Detail.pmBatchNo
     *
     * @return the value of t_PMout_Bill_Detail.pmBatchNo
     *
     * @mbg.generated
     */
    public String getPmBatchNo() {
        return pmBatchNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_PMout_Bill_Detail.pmBatchNo
     *
     * @param pmBatchNo the value for t_PMout_Bill_Detail.pmBatchNo
     *
     * @mbg.generated
     */
    public void setPmBatchNo(String pmBatchNo) {
        this.pmBatchNo = pmBatchNo == null ? null : pmBatchNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_PMout_Bill_Detail.num
     *
     * @return the value of t_PMout_Bill_Detail.num
     *
     * @mbg.generated
     */
    public BigDecimal getNum() {
        return num;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_PMout_Bill_Detail.num
     *
     * @param num the value for t_PMout_Bill_Detail.num
     *
     * @mbg.generated
     */
    public void setNum(BigDecimal num) {
        this.num = num;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_PMout_Bill_Detail.perNum
     *
     * @return the value of t_PMout_Bill_Detail.perNum
     *
     * @mbg.generated
     */
    public BigDecimal getPerNum() {
        return perNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_PMout_Bill_Detail.perNum
     *
     * @param perNum the value for t_PMout_Bill_Detail.perNum
     *
     * @mbg.generated
     */
    public void setPerNum(BigDecimal perNum) {
        this.perNum = perNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_PMout_Bill_Detail.backNum
     *
     * @return the value of t_PMout_Bill_Detail.backNum
     *
     * @mbg.generated
     */
    public BigDecimal getBackNum() {
        return backNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_PMout_Bill_Detail.backNum
     *
     * @param backNum the value for t_PMout_Bill_Detail.backNum
     *
     * @mbg.generated
     */
    public void setBackNum(BigDecimal backNum) {
        this.backNum = backNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_PMout_Bill_Detail.unit
     *
     * @return the value of t_PMout_Bill_Detail.unit
     *
     * @mbg.generated
     */
    public String getUnit() {
        return unit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_PMout_Bill_Detail.unit
     *
     * @param unit the value for t_PMout_Bill_Detail.unit
     *
     * @mbg.generated
     */
    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_PMout_Bill_Detail.remark
     *
     * @return the value of t_PMout_Bill_Detail.remark
     *
     * @mbg.generated
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_PMout_Bill_Detail.remark
     *
     * @param remark the value for t_PMout_Bill_Detail.remark
     *
     * @mbg.generated
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_PMout_Bill_Detail.outInfo
     *
     * @return the value of t_PMout_Bill_Detail.outInfo
     *
     * @mbg.generated
     */
    public String getOutInfo() {
        return outInfo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_PMout_Bill_Detail.outInfo
     *
     * @param outInfo the value for t_PMout_Bill_Detail.outInfo
     *
     * @mbg.generated
     */
    public void setOutInfo(String outInfo) {
        this.outInfo = outInfo == null ? null : outInfo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_PMout_Bill_Detail.receiver
     *
     * @return the value of t_PMout_Bill_Detail.receiver
     *
     * @mbg.generated
     */
    public String getReceiver() {
        return receiver;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_PMout_Bill_Detail.receiver
     *
     * @param receiver the value for t_PMout_Bill_Detail.receiver
     *
     * @mbg.generated
     */
    public void setReceiver(String receiver) {
        this.receiver = receiver == null ? null : receiver.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_PMout_Bill_Detail.sender
     *
     * @return the value of t_PMout_Bill_Detail.sender
     *
     * @mbg.generated
     */
    public String getSender() {
        return sender;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_PMout_Bill_Detail.sender
     *
     * @param sender the value for t_PMout_Bill_Detail.sender
     *
     * @mbg.generated
     */
    public void setSender(String sender) {
        this.sender = sender == null ? null : sender.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_PMout_Bill_Detail.operator
     *
     * @return the value of t_PMout_Bill_Detail.operator
     *
     * @mbg.generated
     */
    public String getOperator() {
        return operator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_PMout_Bill_Detail.operator
     *
     * @param operator the value for t_PMout_Bill_Detail.operator
     *
     * @mbg.generated
     */
    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_PMout_Bill_Detail.outDate
     *
     * @return the value of t_PMout_Bill_Detail.outDate
     *
     * @mbg.generated
     */
    public Date getOutDate() {
        return outDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_PMout_Bill_Detail.outDate
     *
     * @param outDate the value for t_PMout_Bill_Detail.outDate
     *
     * @mbg.generated
     */
    public void setOutDate(Date outDate) {
        this.outDate = outDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_PMout_Bill_Detail.createTime
     *
     * @return the value of t_PMout_Bill_Detail.createTime
     *
     * @mbg.generated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_PMout_Bill_Detail.createTime
     *
     * @param createTime the value for t_PMout_Bill_Detail.createTime
     *
     * @mbg.generated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_PMout_Bill_Detail.lastModifyTime
     *
     * @return the value of t_PMout_Bill_Detail.lastModifyTime
     *
     * @mbg.generated
     */
    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_PMout_Bill_Detail.lastModifyTime
     *
     * @param lastModifyTime the value for t_PMout_Bill_Detail.lastModifyTime
     *
     * @mbg.generated
     */
    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_PMout_Bill_Detail.isDelete
     *
     * @return the value of t_PMout_Bill_Detail.isDelete
     *
     * @mbg.generated
     */
    public Integer getIsDelete() {
        return isDelete;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_PMout_Bill_Detail.isDelete
     *
     * @param isDelete the value for t_PMout_Bill_Detail.isDelete
     *
     * @mbg.generated
     */
    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_PMout_Bill_Detail
     *
     * @mbg.generated
     */
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
        PmOutBillDetail other = (PmOutBillDetail) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getBillId() == null ? other.getBillId() == null : this.getBillId().equals(other.getBillId()))
            && (this.getPmId() == null ? other.getPmId() == null : this.getPmId().equals(other.getPmId()))
            && (this.getPmNo() == null ? other.getPmNo() == null : this.getPmNo().equals(other.getPmNo()))
            && (this.getPmName() == null ? other.getPmName() == null : this.getPmName().equals(other.getPmName()))
            && (this.getPmBatchNo() == null ? other.getPmBatchNo() == null : this.getPmBatchNo().equals(other.getPmBatchNo()))
            && (this.getNum() == null ? other.getNum() == null : this.getNum().equals(other.getNum()))
            && (this.getPerNum() == null ? other.getPerNum() == null : this.getPerNum().equals(other.getPerNum()))
            && (this.getBackNum() == null ? other.getBackNum() == null : this.getBackNum().equals(other.getBackNum()))
            && (this.getUnit() == null ? other.getUnit() == null : this.getUnit().equals(other.getUnit()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getOutInfo() == null ? other.getOutInfo() == null : this.getOutInfo().equals(other.getOutInfo()))
            && (this.getReceiver() == null ? other.getReceiver() == null : this.getReceiver().equals(other.getReceiver()))
            && (this.getSender() == null ? other.getSender() == null : this.getSender().equals(other.getSender()))
            && (this.getOperator() == null ? other.getOperator() == null : this.getOperator().equals(other.getOperator()))
            && (this.getOutDate() == null ? other.getOutDate() == null : this.getOutDate().equals(other.getOutDate()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getLastModifyTime() == null ? other.getLastModifyTime() == null : this.getLastModifyTime().equals(other.getLastModifyTime()))
            && (this.getIsDelete() == null ? other.getIsDelete() == null : this.getIsDelete().equals(other.getIsDelete()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_PMout_Bill_Detail
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getBillId() == null) ? 0 : getBillId().hashCode());
        result = prime * result + ((getPmId() == null) ? 0 : getPmId().hashCode());
        result = prime * result + ((getPmNo() == null) ? 0 : getPmNo().hashCode());
        result = prime * result + ((getPmName() == null) ? 0 : getPmName().hashCode());
        result = prime * result + ((getPmBatchNo() == null) ? 0 : getPmBatchNo().hashCode());
        result = prime * result + ((getNum() == null) ? 0 : getNum().hashCode());
        result = prime * result + ((getPerNum() == null) ? 0 : getPerNum().hashCode());
        result = prime * result + ((getBackNum() == null) ? 0 : getBackNum().hashCode());
        result = prime * result + ((getUnit() == null) ? 0 : getUnit().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getOutInfo() == null) ? 0 : getOutInfo().hashCode());
        result = prime * result + ((getReceiver() == null) ? 0 : getReceiver().hashCode());
        result = prime * result + ((getSender() == null) ? 0 : getSender().hashCode());
        result = prime * result + ((getOperator() == null) ? 0 : getOperator().hashCode());
        result = prime * result + ((getOutDate() == null) ? 0 : getOutDate().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getLastModifyTime() == null) ? 0 : getLastModifyTime().hashCode());
        result = prime * result + ((getIsDelete() == null) ? 0 : getIsDelete().hashCode());
        return result;
    }
}