package com.amir.vo.weixin;

import java.math.BigDecimal;
import java.util.List;

public class EmpWorkVo {

    private BigDecimal total;//计件工资
    private BigDecimal confirmed;
    private BigDecimal unConfirm;
    private List<EmpDayWorkVo> dayWorkVoList;

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(BigDecimal confirmed) {
        this.confirmed = confirmed;
    }

    public BigDecimal getUnConfirm() {
        return unConfirm;
    }

    public void setUnConfirm(BigDecimal unConfirm) {
        this.unConfirm = unConfirm;
    }

    public List<EmpDayWorkVo> getDayWorkVoList() {
        return dayWorkVoList;
    }

    public void setDayWorkVoList(List<EmpDayWorkVo> dayWorkVoList) {
        this.dayWorkVoList = dayWorkVoList;
    }
}