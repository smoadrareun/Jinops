package com.hebeu.common.annotation;

public enum ActionClass {
	CART("购物车信息"),CLIENT("客户信息"),GOODS("商品信息"),ORDER("交易信息"),VENDOR("商户信息"),NONE("未定义");
	
	private String value;
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	private ActionClass() {
		// TODO Auto-generated constructor stub
	}
	
	private ActionClass(String value) {
		this.value = value;
	}
}
