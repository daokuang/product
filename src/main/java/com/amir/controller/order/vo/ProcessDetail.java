package com.amir.controller.order.vo;

/**
 * @Auther: liuyuqing
 * @Date: 2019-11-30 16:16
 * @Description:
 */
public class ProcessDetail {
    private String processName;

    private Integer num;

    private Double pencent;

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Double getPencent() {
        return pencent;
    }

    public void setPencent(Double pencent) {
        this.pencent = pencent;
    }
}
