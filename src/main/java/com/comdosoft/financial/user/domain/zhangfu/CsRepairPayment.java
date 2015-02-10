package com.comdosoft.financial.user.domain.zhangfu;

public class CsRepairPayment {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column cs_repair_payments.id
	 * @mbggenerated
	 */
	private Integer id;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column cs_repair_payments.cs_repair_id
	 * @mbggenerated
	 */
	private Integer csRepairId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column cs_repair_payments.repair_price
	 * @mbggenerated
	 */
	private Integer repairPrice;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column cs_repair_payments.created_at
	 * @mbggenerated
	 */
	private Integer createdAt;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column cs_repair_payments.pay_types
	 * @mbggenerated
	 */
	private Integer payTypes;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column cs_repair_payments.process_user_id
	 * @mbggenerated
	 */
	private Integer processUserId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column cs_repair_payments.process_user_name
	 * @mbggenerated
	 */
	private String processUserName;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column cs_repair_payments.id
	 * @return  the value of cs_repair_payments.id
	 * @mbggenerated
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column cs_repair_payments.id
	 * @param id  the value for cs_repair_payments.id
	 * @mbggenerated
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column cs_repair_payments.cs_repair_id
	 * @return  the value of cs_repair_payments.cs_repair_id
	 * @mbggenerated
	 */
	public Integer getCsRepairId() {
		return csRepairId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column cs_repair_payments.cs_repair_id
	 * @param csRepairId  the value for cs_repair_payments.cs_repair_id
	 * @mbggenerated
	 */
	public void setCsRepairId(Integer csRepairId) {
		this.csRepairId = csRepairId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column cs_repair_payments.repair_price
	 * @return  the value of cs_repair_payments.repair_price
	 * @mbggenerated
	 */
	public Integer getRepairPrice() {
		return repairPrice;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column cs_repair_payments.repair_price
	 * @param repairPrice  the value for cs_repair_payments.repair_price
	 * @mbggenerated
	 */
	public void setRepairPrice(Integer repairPrice) {
		this.repairPrice = repairPrice;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column cs_repair_payments.created_at
	 * @return  the value of cs_repair_payments.created_at
	 * @mbggenerated
	 */
	public Integer getCreatedAt() {
		return createdAt;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column cs_repair_payments.created_at
	 * @param createdAt  the value for cs_repair_payments.created_at
	 * @mbggenerated
	 */
	public void setCreatedAt(Integer createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column cs_repair_payments.pay_types
	 * @return  the value of cs_repair_payments.pay_types
	 * @mbggenerated
	 */
	public Integer getPayTypes() {
		return payTypes;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column cs_repair_payments.pay_types
	 * @param payTypes  the value for cs_repair_payments.pay_types
	 * @mbggenerated
	 */
	public void setPayTypes(Integer payTypes) {
		this.payTypes = payTypes;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column cs_repair_payments.process_user_id
	 * @return  the value of cs_repair_payments.process_user_id
	 * @mbggenerated
	 */
	public Integer getProcessUserId() {
		return processUserId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column cs_repair_payments.process_user_id
	 * @param processUserId  the value for cs_repair_payments.process_user_id
	 * @mbggenerated
	 */
	public void setProcessUserId(Integer processUserId) {
		this.processUserId = processUserId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column cs_repair_payments.process_user_name
	 * @return  the value of cs_repair_payments.process_user_name
	 * @mbggenerated
	 */
	public String getProcessUserName() {
		return processUserName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column cs_repair_payments.process_user_name
	 * @param processUserName  the value for cs_repair_payments.process_user_name
	 * @mbggenerated
	 */
	public void setProcessUserName(String processUserName) {
		this.processUserName = processUserName;
	}
}