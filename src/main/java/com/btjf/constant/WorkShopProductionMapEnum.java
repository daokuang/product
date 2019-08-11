package com.btjf.constant;

import com.btjf.common.enums.ContentEnum;

/**
 * Created by liuyq on 2019/8/9.
 */
public enum WorkShopProductionMapEnum implements ContentEnum {
    BLANK_ZERO(0, "下料车间"),
    BLANK_ONE(1, "下料车间"),
    BLANK_TWO(2, "下料车间"),
    BLANK_THREE(3, "下料车间"),
    GROUNDFLOOR(4, "一车间"),
    BACKASSIST(7, "后道车间-车工"),
    BACKCENTERASSIST(5, "后道车间-中辅工"),
    BACKBIGASSIST(6, "后道车间-大辅工"),
    INSPECTION(8, "质检部-成品质检"),
    ASSIST(9, "外协质检"),
    PACKING(10, "包装车间");

    private Integer value;
    private String content;

    WorkShopProductionMapEnum(Integer value, String content) {
        this.value = value;
        this.content = content;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    public static WorkShopProductionMapEnum get(Integer value) {
        WorkShopProductionMapEnum[] list = values();
        for (Integer i = 0; i < list.length; i++) {
            if (list[i].getValue().equals(value)) {
                return list[i];
            }
        }
        return null;
    }
}
