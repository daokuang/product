package com.amir.enums;

public enum PlatformSourceEnum implements ContentEnum {
    APP("App", 0),
    WEB("网站", 1),
    BACKSTAGE_SYSTEM("后台", 2);

    private String content;
    private Integer value;

    private PlatformSourceEnum(String content, Integer value) {
        this.content = content;
        this.value = value;
    }

    public static PlatformSourceEnum getByValue(Integer value) {
        PlatformSourceEnum[] types = values();
        PlatformSourceEnum[] var2 = types;
        int var3 = types.length;

        for (int var4 = 0; var4 < var3; ++var4) {
            PlatformSourceEnum type = var2[var4];
            if (type.getValue().equals(value)) {
                return type;
            }
        }

        return null;
    }

    public String getContent() {
        return this.content;
    }

    public Integer getValue() {
        return this.value;
    }
}
