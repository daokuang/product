package com.amir.vo.weixin;

import java.math.BigDecimal;

public class EmpDayWorkVo {

    private String date;
    private BigDecimal sum;
    private BigDecimal confirmedSum;
    private BigDecimal unConfirmSum;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public BigDecimal getConfirmedSum() {
        return confirmedSum;
    }

    public void setConfirmedSum(BigDecimal confirmedSum) {
        this.confirmedSum = confirmedSum;
    }

    public BigDecimal getUnConfirmSum() {
        return unConfirmSum;
    }

    public void setUnConfirmSum(BigDecimal unConfirmSum) {
        this.unConfirmSum = unConfirmSum;
    }
}