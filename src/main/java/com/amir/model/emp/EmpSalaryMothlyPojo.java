package com.amir.model.emp;

import java.io.Serializable;

/**
 * Created by liuyq on 2019/9/7.
 * 考勤excel导入model
 */
public class EmpSalaryMothlyPojo implements Serializable {

    private static final long serialVersionUID = 1L;


    private String name;

    private Boolean isBlack; //是否白班

    private String date;  //日期

    private Boolean isHoliday; //是否假期

    private Boolean isLegal; //是否法家

    private Double num;  //数量

    private String yearMonth;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsBlack() {
        return isBlack;
    }

    public void setIsBlack(Boolean black) {
        isBlack = black;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Boolean getIsHoliday() {
        return isHoliday;
    }

    public void setIsHoliday(Boolean holiday) {
        isHoliday = holiday;
    }

    public Boolean getIsLegal() {
        return isLegal;
    }

    public void setIsLegal(Boolean legal) {
        isLegal = legal;
    }

    public Double getNum() {
        return num;
    }

    public void setNum(Double num) {
        this.num = num;
    }

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }
}
