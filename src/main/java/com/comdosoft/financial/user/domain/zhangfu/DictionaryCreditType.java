package com.comdosoft.financial.user.domain.zhangfu;

import java.util.Date;

public class DictionaryCreditType {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column dictionary_credit_types.id
	 * @mbggenerated
	 */
	private Integer id;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column dictionary_credit_types.name
	 * @mbggenerated
	 */
	private String name;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column dictionary_credit_types.created_at
	 * @mbggenerated
	 */
	private Date createdAt;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column dictionary_credit_types.updated_at
	 * @mbggenerated
	 */
	private Date updatedAt;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column dictionary_credit_types.description
	 * @mbggenerated
	 */
	private String description;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column dictionary_credit_types.id
	 * @return  the value of dictionary_credit_types.id
	 * @mbggenerated
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column dictionary_credit_types.id
	 * @param id  the value for dictionary_credit_types.id
	 * @mbggenerated
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column dictionary_credit_types.name
	 * @return  the value of dictionary_credit_types.name
	 * @mbggenerated
	 */
	public String getName() {
		return name;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column dictionary_credit_types.name
	 * @param name  the value for dictionary_credit_types.name
	 * @mbggenerated
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column dictionary_credit_types.created_at
	 * @return  the value of dictionary_credit_types.created_at
	 * @mbggenerated
	 */
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column dictionary_credit_types.created_at
	 * @param createdAt  the value for dictionary_credit_types.created_at
	 * @mbggenerated
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column dictionary_credit_types.updated_at
	 * @return  the value of dictionary_credit_types.updated_at
	 * @mbggenerated
	 */
	public Date getUpdatedAt() {
		return updatedAt;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column dictionary_credit_types.updated_at
	 * @param updatedAt  the value for dictionary_credit_types.updated_at
	 * @mbggenerated
	 */
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column dictionary_credit_types.description
	 * @return  the value of dictionary_credit_types.description
	 * @mbggenerated
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column dictionary_credit_types.description
	 * @param description  the value for dictionary_credit_types.description
	 * @mbggenerated
	 */
	public void setDescription(String description) {
		this.description = description;
	}
}