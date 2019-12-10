package com.amir.controller.emp.vo;

import java.io.Serializable;

/**
 * Created by liuyq on 2019/9/22.
 */
public class MapVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String type;

    private Integer num;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
