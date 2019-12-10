package com.amir.enums;

public interface ContentEnum {
    String getContent();

    Integer getValue();

    default boolean equalsValue(Integer value) {
        return value != null && value.equals(this.getValue());
    }
}
