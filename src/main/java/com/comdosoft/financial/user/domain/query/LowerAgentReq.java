package com.comdosoft.financial.user.domain.query;

/**
 * 下级代理商
 * @author yyb
 *
 */
public class LowerAgentReq {
	
	private int id;
    
    private String[] serialNums;
    //当前登陆的iD
    private int agentsId;
    
	//操作的子代理商id
    private int sonAgentsId;
    //agents表关联customer表的id
    private int customerId;

    private String startTime;
    
    private String endTime;
    
    private int quantity;
    
    private String serialNum;
    
    private String terminalList;
    
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
    //确认密码
    private String pwd1;
    //分润    是否有分润（1无2有）
    private int isProfit;
    //存放页面修该分润比例的值  格式为   precent_tradeId|precent_tradeId
    private String profitPercent;
    //分润比例设置   是修该还是新增    0为修改   1为新增
    private int sign;
    //支付通道 id channelId
    private int payChannelId;
    //tradeTypeId 
    private int tradeTypeId;
    //precent分润比例
    private float precent;
    //代理商状态
    private int status;
    //cityId 
    private int cityId;
    private int provinceId;
    //
    private String cityName;
    private String provinceName;
    //默认分润比例
    private float defaultProfit;
    //法人身份证图片
    private String cardPhotoPath;
    //营业执照照片
    private String licensePhotoPath;
    //税务登记证照片
    private String taxPhotoPath;
    //公司税务登记证号
    private String taxNumStr;
    private String code;
    //下级代理商名字
    private String lowerAgentName;
    //权限id
    private int roleId;
    
    public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getLowerAgentName() {
		return lowerAgentName;
	}

	public void setLowerAgentName(String lowerAgentName) {
		this.lowerAgentName = lowerAgentName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getAgentsId() {
		return agentsId;
	}

	public void setAgentsId(int agentsId) {
		this.agentsId = agentsId;
	}
    
	public String getTaxNumStr() {
		if(taxNumStr==null){
			taxNumStr=" ";
		}
		return taxNumStr;
	}

	public void setTaxNumStr(String taxNumStr) {
		this.taxNumStr = taxNumStr;
	}

	public String getCardPhotoPath() {
		if(cardPhotoPath==null){
			cardPhotoPath=" ";
		}
		return cardPhotoPath;
	}

	public void setCardPhotoPath(String cardPhotoPath) {
		this.cardPhotoPath = cardPhotoPath;
	}

	public String getLicensePhotoPath() {
		if(licensePhotoPath==null){
			licensePhotoPath=" ";
		}
		return licensePhotoPath;
	}

	public void setLicensePhotoPath(String licensePhotoPath) {
		this.licensePhotoPath = licensePhotoPath;
	}

	public String getTaxPhotoPath() {
		if(taxPhotoPath==null){
			taxPhotoPath=" ";
		}
		return taxPhotoPath;
	}

	public void setTaxPhotoPath(String taxPhotoPath) {
		this.taxPhotoPath = taxPhotoPath;
	}



	public float getDefaultProfit() {
		return defaultProfit;
	}

	public void setDefaultProfit(float defaultProfit) {
		this.defaultProfit = defaultProfit;
	}

	public String getPwd1() {
		return pwd1;
	}

	public void setPwd1(String pwd1) {
		this.pwd1 = pwd1;
	}

	public int getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(int provinceId) {
		this.provinceId = provinceId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getTradeTypeId() {
		return tradeTypeId;
	}
	public void setTradeTypeId(int tradeTypeId) {
		this.tradeTypeId = tradeTypeId;
	}

	
	public float getPrecent() {
		return precent;
	}

	public void setPrecent(float precent) {
		this.precent = precent;
	}

	public int getSign() {
		return sign;
	}

	public void setSign(int sign) {
		this.sign = sign;
	}

	public int getPayChannelId() {
		return payChannelId;
	}

	public void setPayChannelId(int payChannelId) {
		this.payChannelId = payChannelId;
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

	
	public String[] getSerialNums() {
		return serialNums;
	}

	public void setSerialNums(String[] serialNums) {
		this.serialNums = serialNums;
	}

	
	public int getSonAgentsId() {
		return sonAgentsId;
	}

	public void setSonAgentsId(int sonAgentsId) {
		this.sonAgentsId = sonAgentsId;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}

	public String getTerminalList() {
		return terminalList;
	}

	public void setTerminalList(String terminalList) {
		this.terminalList = terminalList;
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
