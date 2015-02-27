package com.comdosoft.financial.user.domain.zhangfu;

import java.util.Date;

public class Agent {

    /**
     * 第一级代理商父代理商为0
     */
    public static final int PARENT_ID = 0;

    /**
     * 代理商类型（1公司，2个人）
     */
    public static final int TYPES_1 = 1;

    /**
     * 代理商类型（1公司，2个人）
     */
    public static final int TYPES_2 = 2;

    /**
     * 未激活
     */
    public static final int STATUS_1 = 1;

    /**
     * 正常
     */
    public static final int STATUS_2 = 2;

    /**
     * 停用
     */
    public static final int STATUS_3 = 3;

    /**
     * 创建类型(1注册)
     */
    public static final int FROM_TYPE_1 = 1;

    /**
     * 创建类型(2运营创建)
     */
    public static final int FROM_TYPE_2 = 2;

    /**
     * 是否有分润(无)
     */
    public static final int IS_HAVE_PROFIT_N = 1;

    /**
     * 是否有分润(有)
     */
    public static final int IS_HAVE_PROFIT_Y = 2;

    private String username;
    private String password;
    private Integer cityId;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column agents.id
     * 
     * @mbggenerated
     */
    private Integer id;

    /**
     * 当前登录的代理商code（父代理商）
     * 
     * This field was generated by MyBatis Generator. This field corresponds to the database column agents.agent_code
     * 
     * @mbggenerated
     */
    private String agentCode;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column agents.name
     * 
     * @mbggenerated
     */
    private String name;

    /**
     * 代理商code
     * 
     * This field was generated by MyBatis Generator. This field corresponds to the database column agents.code
     * 
     * @mbggenerated
     */
    private String code;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column agents.card_id
     * 
     * @mbggenerated
     */
    private String cardId;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column agents.types
     * 
     * @mbggenerated
     */
    private Integer types;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column agents.company_name
     * 
     * @mbggenerated
     */
    private String companyName;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column agents.business_license
     * 
     * @mbggenerated
     */
    private String businessLicense;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column agents.phone
     * 
     * @mbggenerated
     */
    private String phone;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column agents.email
     * 
     * @mbggenerated
     */
    private String email;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column agents.customer_id
     * 
     * @mbggenerated
     */
    private Integer customerId;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column agents.address
     * 
     * @mbggenerated
     */
    private String address;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column agents.form_types
     * 
     * @mbggenerated
     */
    private Integer formTypes;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column agents.parent_id
     * 
     * @mbggenerated
     */
    private Integer parentId;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column agents.status
     * 
     * @mbggenerated
     */
    private Integer status;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column agents.is_have_profit
     * 
     * @mbggenerated
     */
    private Integer isHaveProfit = 1;

    /**
     * 默认分润比例default_profit
     */
    private Integer defaultProfit;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column agents.created_at
     * 
     * @mbggenerated
     */
    private Date createdAt;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column agents.updated_at
     * 
     * @mbggenerated
     */
    private Date updatedAt;

    private String cardIdPhotoPath;

    private String taxRegisteredNo;

    private String licenseNoPicPath;

    private String taxNoPicPath;

    public String getCardIdPhotoPath() {
        return cardIdPhotoPath;
    }

    public void setCardIdPhotoPath(String cardIdPhotoPath) {
        this.cardIdPhotoPath = cardIdPhotoPath;
    }

    public String getTaxRegisteredNo() {
        return taxRegisteredNo;
    }

    public void setTaxRegisteredNo(String taxRegisteredNo) {
        this.taxRegisteredNo = taxRegisteredNo;
    }

    public String getLicenseNoPicPath() {
        return licenseNoPicPath;
    }

    public void setLicenseNoPicPath(String licenseNoPicPath) {
        this.licenseNoPicPath = licenseNoPicPath;
    }

    public String getTaxNoPicPath() {
        return taxNoPicPath;
    }

    public void setTaxNoPicPath(String taxNoPicPath) {
        this.taxNoPicPath = taxNoPicPath;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     *            the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     *            the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the cityId
     */
    public Integer getCityId() {
        return cityId;
    }

    /**
     * @param cityId
     *            the cityId to set
     */
    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    /**
     * @return the types1
     */
    public static int getTypes1() {
        return TYPES_1;
    }

    /**
     * @return the types2
     */
    public static int getTypes2() {
        return TYPES_2;
    }

    /**
     * @return the status1
     */
    public static int getStatus1() {
        return STATUS_1;
    }

    /**
     * @return the status2
     */
    public static int getStatus2() {
        return STATUS_2;
    }

    /**
     * @return the status3
     */
    public static int getStatus3() {
        return STATUS_3;
    }

    /**
     * @return the fromType1
     */
    public static int getFromType1() {
        return FROM_TYPE_1;
    }

    /**
     * @return the fromType2
     */
    public static int getFromType2() {
        return FROM_TYPE_2;
    }

    /**
     * @return the defaultProfit
     */
    public Integer getDefaultProfit() {
        return defaultProfit;
    }

    /**
     * @param defaultProfit
     *            the defaultProfit to set
     */
    public void setDefaultProfit(Integer defaultProfit) {
        this.defaultProfit = defaultProfit;
    }

    /**
     * @return the isHaveProfitN
     */
    public static int getIsHaveProfitN() {
        return IS_HAVE_PROFIT_N;
    }

    /**
     * @return the isHaveProfitY
     */
    public static int getIsHaveProfitY() {
        return IS_HAVE_PROFIT_Y;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column agents.id
     * 
     * @return the value of agents.id
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column agents.id
     * 
     * @param id
     *            the value for agents.id
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column agents.agent_code
     * 
     * @return the value of agents.agent_code
     * @mbggenerated
     */
    public String getAgentCode() {
        return agentCode;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column agents.agent_code
     * 
     * @param agentCode
     *            the value for agents.agent_code
     * @mbggenerated
     */
    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column agents.name
     * 
     * @return the value of agents.name
     * @mbggenerated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column agents.name
     * 
     * @param name
     *            the value for agents.name
     * @mbggenerated
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column agents.card_id
     * 
     * @return the value of agents.card_id
     * @mbggenerated
     */
    public String getCardId() {
        return cardId;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column agents.card_id
     * 
     * @param cardId
     *            the value for agents.card_id
     * @mbggenerated
     */
    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column agents.types
     * 
     * @return the value of agents.types
     * @mbggenerated
     */
    public Integer getTypes() {
        return types;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column agents.types
     * 
     * @param types
     *            the value for agents.types
     * @mbggenerated
     */
    public void setTypes(Integer types) {
        this.types = types;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column agents.company_name
     * 
     * @return the value of agents.company_name
     * @mbggenerated
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column agents.company_name
     * 
     * @param companyName
     *            the value for agents.company_name
     * @mbggenerated
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * agents.business_license
     * 
     * @return the value of agents.business_license
     * @mbggenerated
     */
    public String getBusinessLicense() {
        return businessLicense;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column agents.business_license
     * 
     * @param businessLicense
     *            the value for agents.business_license
     * @mbggenerated
     */
    public void setBusinessLicense(String businessLicense) {
        this.businessLicense = businessLicense;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column agents.phone
     * 
     * @return the value of agents.phone
     * @mbggenerated
     */
    public String getPhone() {
        return phone;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column agents.phone
     * 
     * @param phone
     *            the value for agents.phone
     * @mbggenerated
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column agents.email
     * 
     * @return the value of agents.email
     * @mbggenerated
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column agents.email
     * 
     * @param email
     *            the value for agents.email
     * @mbggenerated
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column agents.customer_id
     * 
     * @return the value of agents.customer_id
     * @mbggenerated
     */
    public Integer getCustomerId() {
        return customerId;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column agents.customer_id
     * 
     * @param customerId
     *            the value for agents.customer_id
     * @mbggenerated
     */
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column agents.address
     * 
     * @return the value of agents.address
     * @mbggenerated
     */
    public String getAddress() {
        return address;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column agents.address
     * 
     * @param address
     *            the value for agents.address
     * @mbggenerated
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column agents.form_types
     * 
     * @return the value of agents.form_types
     * @mbggenerated
     */
    public Integer getFormTypes() {
        return formTypes;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column agents.form_types
     * 
     * @param formTypes
     *            the value for agents.form_types
     * @mbggenerated
     */
    public void setFormTypes(Integer formTypes) {
        this.formTypes = formTypes;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column agents.parent_id
     * 
     * @return the value of agents.parent_id
     * @mbggenerated
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column agents.parent_id
     * 
     * @param parentId
     *            the value for agents.parent_id
     * @mbggenerated
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column agents.status
     * 
     * @return the value of agents.status
     * @mbggenerated
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column agents.status
     * 
     * @param status
     *            the value for agents.status
     * @mbggenerated
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * agents.is_have_profit
     * 
     * @return the value of agents.is_have_profit
     * @mbggenerated
     */
    public Integer getIsHaveProfit() {
        return isHaveProfit;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column agents.is_have_profit
     * 
     * @param isHaveProfit
     *            the value for agents.is_have_profit
     * @mbggenerated
     */
    public void setIsHaveProfit(Integer isHaveProfit) {
        this.isHaveProfit = isHaveProfit;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column agents.created_at
     * 
     * @return the value of agents.created_at
     * @mbggenerated
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column agents.created_at
     * 
     * @param createdAt
     *            the value for agents.created_at
     * @mbggenerated
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column agents.updated_at
     * 
     * @return the value of agents.updated_at
     * @mbggenerated
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column agents.updated_at
     * 
     * @param updatedAt
     *            the value for agents.updated_at
     * @mbggenerated
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column agents.code
     * 
     * @return the value of agents.code
     * @mbggenerated
     */
    public String getCode() {
        return code;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column agents.code
     * 
     * @param code
     *            the value for agents.code
     * @mbggenerated
     */
    public void setCode(String code) {
        this.code = code;
    }

}