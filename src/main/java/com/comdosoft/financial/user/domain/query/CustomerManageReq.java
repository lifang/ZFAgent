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
	//权限集合    权限1，权限 2，权限 3
	private String roles;
	//权限
	private int roleId;
	//登陆ID
	private String loginId;
	//密码
	private String pwd;
	//确认再次输入的密码
	private String pwd1;
	//姓名
	private String userName;
	// 1用户与代理商关系   2员工与代理商关系
	private int types;
	// 1正常 2删除
	private int status;
	//批量删除 用户iD    id1,id2,id3
	private String customerIds;
	
	
	
	
	public String getCustomerIds() {
		return customerIds;
	}

	public void setCustomerIds(String customerIds) {
		this.customerIds = customerIds;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getTypes() {
		return types;
	}

	public void setTypes(int types) {
		this.types = types;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getPwd1() {
		return pwd1;
	}

	public void setPwd1(String pwd1) {
		this.pwd1 = pwd1;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

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
