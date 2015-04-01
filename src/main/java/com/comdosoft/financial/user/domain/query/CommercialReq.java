package com.comdosoft.financial.user.domain.query;

public class CommercialReq {
	private int agentId;// 代理商ID
	private String name;// 商户姓名
	private String username;// 商户登陆姓名
	private String email;// 商户邮箱
	private String phone;// 商户电话
	private int page;
	private int rows;
	private int offset;

	public int getAgentId() {
		return agentId;
	}

	public void setAgentId(int agentId) {
		this.agentId = agentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		if (rows <= 0) {
			rows = 10;
		}
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getOffset() {
		if (page > 0 && rows > 0) {
			offset = (page - 1) * rows;
		} else {
			offset = 0;
		}
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

}
