package com.btjf.model.salary;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SalaryMonthly implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_Salary_Monthly.id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_Salary_Monthly.yearMonth
     *
     * @mbg.generated
     */
    private String yearMonth;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_Salary_Monthly.expectWorkDay
     *
     * @mbg.generated
     */
    private Integer expectWorkDay;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_Salary_Monthly.realityWorkDay
     *
     * @mbg.generated
     */
    private Integer realityWorkDay;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_Salary_Monthly.basicSalary
     *
     * @mbg.generated
     */
    private BigDecimal basicSalary;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_Salary_Monthly.hourlyWage
     *
     * @mbg.generated
     */
    private BigDecimal hourlyWage;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_Salary_Monthly.createTime
     *
     * @mbg.generated
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_Salary_Monthly.lastModifyTime
     *
     * @mbg.generated
     */
    private Date lastModifyTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_Salary_Monthly.isDelete
     *
     * @mbg.generated
     */
    private Integer isDelete;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_Salary_Monthly
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_Salary_Monthly.id
     *
     * @return the value of t_Salary_Monthly.id
     *
     * @mbg.generated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_Salary_Monthly.id
     *
     * @param id the value for t_Salary_Monthly.id
     *
     * @mbg.generated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_Salary_Monthly.yearMonth
     *
     * @return the value of t_Salary_Monthly.yearMonth
     *
     * @mbg.generated
     */
    public String getYearMonth() {
        return yearMonth;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_Salary_Monthly.yearMonth
     *
     * @param yearMonth the value for t_Salary_Monthly.yearMonth
     *
     * @mbg.generated
     */
    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth == null ? null : yearMonth.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_Salary_Monthly.expectWorkDay
     *
     * @return the value of t_Salary_Monthly.expectWorkDay
     *
     * @mbg.generated
     */
    public Integer getExpectWorkDay() {
        return expectWorkDay;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_Salary_Monthly.expectWorkDay
     *
     * @param expectWorkDay the value for t_Salary_Monthly.expectWorkDay
     *
     * @mbg.generated
     */
    public void setExpectWorkDay(Integer expectWorkDay) {
        this.expectWorkDay = expectWorkDay;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_Salary_Monthly.realityWorkDay
     *
     * @return the value of t_Salary_Monthly.realityWorkDay
     *
     * @mbg.generated
     */
    public Integer getRealityWorkDay() {
        return realityWorkDay;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_Salary_Monthly.realityWorkDay
     *
     * @param realityWorkDay the value for t_Salary_Monthly.realityWorkDay
     *
     * @mbg.generated
     */
    public void setRealityWorkDay(Integer realityWorkDay) {
        this.realityWorkDay = realityWorkDay;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_Salary_Monthly.basicSalary
     *
     * @return the value of t_Salary_Monthly.basicSalary
     *
     * @mbg.generated
     */
    public BigDecimal getBasicSalary() {
        return basicSalary;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_Salary_Monthly.basicSalary
     *
     * @param basicSalary the value for t_Salary_Monthly.basicSalary
     *
     * @mbg.generated
     */
    public void setBasicSalary(BigDecimal basicSalary) {
        this.basicSalary = basicSalary;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_Salary_Monthly.hourlyWage
     *
     * @return the value of t_Salary_Monthly.hourlyWage
     *
     * @mbg.generated
     */
    public BigDecimal getHourlyWage() {
        return hourlyWage;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_Salary_Monthly.hourlyWage
     *
     * @param hourlyWage the value for t_Salary_Monthly.hourlyWage
     *
     * @mbg.generated
     */
    public void setHourlyWage(BigDecimal hourlyWage) {
        this.hourlyWage = hourlyWage;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_Salary_Monthly.createTime
     *
     * @return the value of t_Salary_Monthly.createTime
     *
     * @mbg.generated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_Salary_Monthly.createTime
     *
     * @param createTime the value for t_Salary_Monthly.createTime
     *
     * @mbg.generated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_Salary_Monthly.lastModifyTime
     *
     * @return the value of t_Salary_Monthly.lastModifyTime
     *
     * @mbg.generated
     */
    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_Salary_Monthly.lastModifyTime
     *
     * @param lastModifyTime the value for t_Salary_Monthly.lastModifyTime
     *
     * @mbg.generated
     */
    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_Salary_Monthly.isDelete
     *
     * @return the value of t_Salary_Monthly.isDelete
     *
     * @mbg.generated
     */
    public Integer getIsDelete() {
        return isDelete;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_Salary_Monthly.isDelete
     *
     * @param isDelete the value for t_Salary_Monthly.isDelete
     *
     * @mbg.generated
     */
    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_Salary_Monthly
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
        SalaryMonthly other = (SalaryMonthly) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getYearMonth() == null ? other.getYearMonth() == null : this.getYearMonth().equals(other.getYearMonth()))
            && (this.getExpectWorkDay() == null ? other.getExpectWorkDay() == null : this.getExpectWorkDay().equals(other.getExpectWorkDay()))
            && (this.getRealityWorkDay() == null ? other.getRealityWorkDay() == null : this.getRealityWorkDay().equals(other.getRealityWorkDay()))
            && (this.getBasicSalary() == null ? other.getBasicSalary() == null : this.getBasicSalary().equals(other.getBasicSalary()))
            && (this.getHourlyWage() == null ? other.getHourlyWage() == null : this.getHourlyWage().equals(other.getHourlyWage()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getLastModifyTime() == null ? other.getLastModifyTime() == null : this.getLastModifyTime().equals(other.getLastModifyTime()))
            && (this.getIsDelete() == null ? other.getIsDelete() == null : this.getIsDelete().equals(other.getIsDelete()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_Salary_Monthly
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getYearMonth() == null) ? 0 : getYearMonth().hashCode());
        result = prime * result + ((getExpectWorkDay() == null) ? 0 : getExpectWorkDay().hashCode());
        result = prime * result + ((getRealityWorkDay() == null) ? 0 : getRealityWorkDay().hashCode());
        result = prime * result + ((getBasicSalary() == null) ? 0 : getBasicSalary().hashCode());
        result = prime * result + ((getHourlyWage() == null) ? 0 : getHourlyWage().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getLastModifyTime() == null) ? 0 : getLastModifyTime().hashCode());
        result = prime * result + ((getIsDelete() == null) ? 0 : getIsDelete().hashCode());
        return result;
    }
}