package com.btjf.controller.order.vo;

/**
 * @Auther: liuyuqing
 * @Date: 2019-11-30 16:16
 * @Description:
 */
public class ProcessDetail {
    private String processName;

    private Integer num;

    private Integer pencent;

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

    public Integer getPencent() {
        return pencent;
    }

    public void setPencent(Integer pencent) {
        this.pencent = pencent;
    }
}
