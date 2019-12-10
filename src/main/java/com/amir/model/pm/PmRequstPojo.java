package com.amir.model.pm;

import java.io.Serializable;

/**
 * Created by liuyq on 2019/7/30.
 */
public class PmRequstPojo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String pmNo;
    private String name;
    private String colour;
    private String norms;
    private String material;
    private String callStr;
    private String type;

    public String getPmNo() {
        return pmNo;
    }

    public void setPmNo(String pmNo) {
        this.pmNo = pmNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getNorms() {
        return norms;
    }

    public void setNorms(String norms) {
        this.norms = norms;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getCallStr() {
        return callStr;
    }

    public void setCallStr(String callStr) {
        this.callStr = callStr;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
