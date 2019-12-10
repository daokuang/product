package com.amir.constant;

import com.btjf.common.enums.ContentEnum;

/**
 * Created by liuyq on 2019/8/9.
 */
public enum WorkShopProductionMapEnum implements ContentEnum {
    BLANK_ZERO(0, "下料车间"),
    BLANK_ONE(1, "下料车间"),
    BLANK_TWO(2, "下料车间"),
    BLANK_THREE(3, "仓库"),
    GROUNDFLOOR(4, "一车间"),
    BACKASSIST(7, "后道车间-车工"),
    BACKCENTERASSIST(5, "后道车间-中辅工"),
    BACKBIGASSIST(6, "后道车间-大辅工"),
    INSPECTION(8, "质检部-成品质检"),
    ASSIST(9, "一车间"),
    PACKING(10, "包装车间"),
    BACKSMALLASSIST(11, "后道车间-小辅工");

    private Integer value;
    private String content;

    WorkShopProductionMapEnum(Integer value, String content) {
        this.value = value;
        this.content = content;
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

    public static WorkShopProductionMapEnum getByName(String name) {
        WorkShopProductionMapEnum[] list = values();
        for (Integer i = 0; i < list.length; i++) {
            if (list[i].getContent().equals(name)) {
                return list[i];
            }
        }
        return null;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
