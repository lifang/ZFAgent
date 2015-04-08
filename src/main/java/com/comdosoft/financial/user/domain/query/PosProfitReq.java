package com.comdosoft.financial.user.domain.query;

import java.util.Date;

public class PosProfitReq {

	private int agent_id;// 代理商ID
	private Date saleTime;// 销售日期
	private String posName;// POS机名称
	private String modelNumber;// 终端号
	private String types;// 订单类型

	private int page;
	private int rows;
	private int offset;

	private String startTime;
	private String endTime;
	public int getAgent_id() {
		return agent_id;
	}

	public void setAgent_id(int agent_id) {
		this.agent_id = agent_id;
	}

	public Date getSaleTime() {
		return saleTime;
	}

	public void setSaleTime(Date saleTime) {
		this.saleTime = saleTime;
	}

	public String getPosName() {
		return posName;
	}

	public void setPosName(String posName) {
		this.posName = posName;
	}

	public String getModelNumber() {
		return modelNumber;
	}

	public void setModelNumber(String modelNumber) {
		this.modelNumber = modelNumber;
	}

	public int getPage() {
		return page;
	}

	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
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

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
}
