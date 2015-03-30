package com.comdosoft.financial.user.domain.query;

/**
 * 用于APP用户管理
 * @author yangyibin
 *
 */
public class CustomerManageReq {
	//当前登录代理商ID
	private int agentsId;
	//员工ID
	private int customerId;
	
	public int getAgentsId() {
		return agentsId;
	}

	public void setAgentsId(int agentsId) {
		this.agentsId = agentsId;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	private int page;
    private int rows;
    private int offset;
    
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
