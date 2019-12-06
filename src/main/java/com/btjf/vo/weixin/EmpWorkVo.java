package com.btjf.vo.weixin;

import java.util.List;

public class EmpWorkVo {

    private Double total;//计件工资
    private Double confirmed;
    private Double unConfirm;
    private List<EmpDayWorkVo> dayWorkVoList;

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public List<EmpDayWorkVo> getDayWorkVoList() {
        return dayWorkVoList;
    }

    public void setDayWorkVoList(List<EmpDayWorkVo> dayWorkVoList) {
        this.dayWorkVoList = dayWorkVoList;
    }

    public Double getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Double confirmed) {
        this.confirmed = confirmed;
    }

    public Double getUnConfirm() {
        return unConfirm;
    }

    public void setUnConfirm(Double unConfirm) {
        this.unConfirm = unConfirm;
    }
}
