package com.comdosoft.financial.user.domain.query;

/**
 * 代理商创建用户表单
 * 
 * @author maomao
 *
 */
public class EmpReq {

	private int id;
	private int customer_id;
	private String username;// 姓名
	private String name;// 账号
	private String password;// 密码
	private String comfirmPwd;// 确认密码
	private String[] rights;// 权限数组

	private int city_id;
	private int account_type;
	private int types;
	private int integral;
	private int status;
	private String dentcode;

	private int role_id;
	private String role_name;
	private int agent_id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getComfirmPwd() {
		return comfirmPwd;
	}

	public void setComfirmPwd(String comfirmPwd) {
		this.comfirmPwd = comfirmPwd;
	}

	public String[] getRights() {
		return rights;
	}

	public void setRights(String[] rights) {
		this.rights = rights;
	}

	public int getCity_id() {
		return city_id;
	}

	public void setCity_id(int city_id) {
		this.city_id = city_id;
	}

	public int getAccount_type() {
		return account_type;
	}

	public void setAccount_type(int account_type) {
		this.account_type = account_type;
	}

	public int getTypes() {
		return types;
	}

	public void setTypes(int types) {
		this.types = types;
	}

	public int getIntegral() {
		return integral;
	}

	public void setIntegral(int integral) {
		this.integral = integral;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDentcode() {
		return dentcode;
	}

	public void setDentcode(String dentcode) {
		this.dentcode = dentcode;
	}

	public int getRole_id() {
		return role_id;
	}

	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}

	public String getRole_name() {
		return role_name;
	}

	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}

	public int getAgent_id() {
		return agent_id;
	}

	public void setAgent_id(int agent_id) {
		this.agent_id = agent_id;
	}

	public int getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}

}
