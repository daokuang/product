package com.amir.product.common.enums;

public interface ContentEnum {
	
	public String getContent();
	
	public Integer getValue();
	
	public default boolean equalsValue(Integer value) {
		return value != null && value.equals(this.getValue());
	}
	
}
