package com.amir.constant;

import com.amir.enums.ContentEnum;

/**
 * Created by liuyq on 2019/9/11.
 * 补贴枚举
 */
public enum SubsidyTypeEnum implements ContentEnum {

    PHONE(1, "电话费补贴"),
    FIX_HOUS(2, "固定工住房补贴"),
    COUNT_HOUS(3, "计件工住房补贴"),
    SALARY(4, "工资补贴"),
    SOCIAL(5, "社保补贴"),
    WORK_AGE(6, "工龄补贴"),
    CAR_CHCK_WORK(7, "车工出勤补贴"),
    NEW_CAR_CHECK_WORK(8, "新车工出勤补贴"),
    OTHER(9, "其他扣款");

    private Integer value;
    private String content;

    SubsidyTypeEnum(Integer value, String content) {
        this.value = value;
        this.content = content;
    }

    public static SubsidyTypeEnum getByValue(Integer value) {
        SubsidyTypeEnum[] subsidyTypeEnums = SubsidyTypeEnum.values();
        for (Integer i = 0; i < subsidyTypeEnums.length; i++) {
            if (subsidyTypeEnums[i].getValue().equals(value)) {
                return subsidyTypeEnums[i];
            }
        }
        return null;
    }

    @Override
    public String getContent() {
        return this.content;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }
}
