package com.comdosoft.financial.user.domain.zhangfu;

import java.util.Date;

public class DictionaryReturnReason {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column dictionary_return_reasons.id
	 * @mbggenerated
	 */
	private Integer id;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column dictionary_return_reasons.return_reason
	 * @mbggenerated
	 */
	private String returnReason;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column dictionary_return_reasons.created_at
	 * @mbggenerated
	 */
	private Date createdAt;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column dictionary_return_reasons.id
	 * @return  the value of dictionary_return_reasons.id
	 * @mbggenerated
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column dictionary_return_reasons.id
	 * @param id  the value for dictionary_return_reasons.id
	 * @mbggenerated
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column dictionary_return_reasons.return_reason
	 * @return  the value of dictionary_return_reasons.return_reason
	 * @mbggenerated
	 */
	public String getReturnReason() {
		return returnReason;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column dictionary_return_reasons.return_reason
	 * @param returnReason  the value for dictionary_return_reasons.return_reason
	 * @mbggenerated
	 */
	public void setReturnReason(String returnReason) {
		this.returnReason = returnReason;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column dictionary_return_reasons.created_at
	 * @return  the value of dictionary_return_reasons.created_at
	 * @mbggenerated
	 */
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column dictionary_return_reasons.created_at
	 * @param createdAt  the value for dictionary_return_reasons.created_at
	 * @mbggenerated
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
}