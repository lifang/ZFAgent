package com.comdosoft.financial.user.domain.zhangfu;
/**
 * 1 用户 2代理  3 运营
 */
public enum CancelType {
	USER(1,"用户"),AGETN(2,"代理商"),MANAGER(3,"运营");
	private int code;
	private String name;
	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	private CancelType(int code, String name) {
		this.code = code;
		this.name = name;
	}
	
}
