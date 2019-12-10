package com.amir.converter;

import com.amir.enums.ContentEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.lang.reflect.InvocationTargetException;

public class StringToEnumConverterFactory implements ConverterFactory<String, ContentEnum> {
    public StringToEnumConverterFactory() {
    }

    @Override
    public <T extends ContentEnum> Converter<String, T> getConverter(Class<T> targetType) {
        return new StringToEnumConverterFactory.ValueToEnum(targetType);
    }

    private class ValueToEnum<T extends ContentEnum> implements Converter<String, T> {
        private final Class<T> enumType;

        public ValueToEnum(Class<T> enumType) {
            this.enumType = enumType;
        }

        @Override
        public T convert(String value) {
            if (StringUtils.isNotEmpty(value)) {
                try {
                    T[] enums = (T[]) this.enumType.getMethod("values").invoke(null);
                    if (null != enums && enums.length > 0) {
                        for (T e : enums) {
                            if (value.equals(e.getValue().toString())) {
                                return e;
                            }
                        }
                    }
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
                    e.printStackTrace();
                }

            }
            return null;
        }
    }
}