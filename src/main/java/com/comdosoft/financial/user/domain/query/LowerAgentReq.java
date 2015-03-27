package com.comdosoft.financial.user.domain.query;

/**
 * 下级代理商
 * @author yyb
 *
 */
public class LowerAgentReq {
	
	private int id;
    
    private String[] serial_nums;
    //当前登陆的iD
    private int agents_id;
    //操作的子代理商id
    private int son_agents_id;
    
    private int customer_id;

    private String startTime;
    
    private String endTime;
    
    private int quantity;
    
    private String serial_num;
    
    private String terminal_list;
    
	//新增表单中的字段
    //代理商类型
    private int agentType;
    //代理商名字
    private String agentName;
    //代理商身份证号
    private String agentCardId;
    //公司全称
    private String companyName;
    //公司营业执照登记号
    private String companyId;
    //手机号
    private String phoneNum;
    //邮箱
    private String emailStr;
    //地址
    private String addressStr;
    //登陆Id
    private String loginId;
    //密码
    private String pwd;
    //分润
    private int isProfit;
    //存放页面修该分润比例的值  格式为   precent_tradeId|precent_tradeId
    private String profitPercent;
    //分润比例设置   是修该还是新增    0为修该   1为新增
    private int sign;
    //支付通道 id channelId
    private int channelId;
    //tradeTypeId 
    private int tradeTypeId;
    //precent分润比例
    private int precent;
    
    
    
    
	public int getTradeTypeId() {
		return tradeTypeId;
	}

	public void setTradeTypeId(int tradeTypeId) {
		this.tradeTypeId = tradeTypeId;
	}

	public int getPrecent() {
		return precent;
	}

	public void setPrecent(int precent) {
		this.precent = precent;
	}

	public int getSign() {
		return sign;
	}

	public void setSign(int sign) {
		this.sign = sign;
	}

	public int getChannelId() {
		return channelId;
	}

	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}

	public String getProfitPercent() {
		return profitPercent;
	}

	public void setProfitPercent(String profitPercent) {
		this.profitPercent = profitPercent;
	}

	public int getAgentType() {
		return agentType;
	}

	public void setAgentType(int agentType) {
		this.agentType = agentType;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getAgentCardId() {
		return agentCardId;
	}

	public void setAgentCardId(String agentCardId) {
		this.agentCardId = agentCardId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getEmailStr() {
		return emailStr;
	}

	public void setEmailStr(String emailStr) {
		this.emailStr = emailStr;
	}

	public String getAddressStr() {
		return addressStr;
	}

	public void setAddressStr(String addressStr) {
		this.addressStr = addressStr;
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

	public int getIsProfit() {
		return isProfit;
	}

	public void setIsProfit(int isProfit) {
		this.isProfit = isProfit;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String[] getSerial_nums() {
		return serial_nums;
	}

	public void setSerial_nums(String[] serial_nums) {
		this.serial_nums = serial_nums;
	}

	public int getAgents_id() {
		return agents_id;
	}

	public void setAgents_id(int agents_id) {
		this.agents_id = agents_id;
	}

	public int getSon_agents_id() {
		return son_agents_id;
	}

	public void setSon_agents_id(int son_agents_id) {
		this.son_agents_id = son_agents_id;
	}

	public int getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
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

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getSerial_num() {
		return serial_num;
	}

	public void setSerial_num(String serial_num) {
		this.serial_num = serial_num;
	}

	public String getTerminal_list() {
		return terminal_list;
	}

	public void setTerminal_list(String terminal_list) {
		this.terminal_list = terminal_list;
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
