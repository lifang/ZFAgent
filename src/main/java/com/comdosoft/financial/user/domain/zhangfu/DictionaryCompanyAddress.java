package com.comdosoft.financial.user.domain.zhangfu;

import java.util.Date;

public class DictionaryCompanyAddress {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column dictionary_company_addresses.id
	 * @mbggenerated
	 */
	private Integer id;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column dictionary_company_addresses.company_address
	 * @mbggenerated
	 */
	private String companyAddress;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column dictionary_company_addresses.created_at
	 * @mbggenerated
	 */
	private Date createdAt;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column dictionary_company_addresses.id
	 * @return  the value of dictionary_company_addresses.id
	 * @mbggenerated
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column dictionary_company_addresses.id
	 * @param id  the value for dictionary_company_addresses.id
	 * @mbggenerated
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column dictionary_company_addresses.company_address
	 * @return  the value of dictionary_company_addresses.company_address
	 * @mbggenerated
	 */
	public String getCompanyAddress() {
		return companyAddress;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column dictionary_company_addresses.company_address
	 * @param companyAddress  the value for dictionary_company_addresses.company_address
	 * @mbggenerated
	 */
	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column dictionary_company_addresses.created_at
	 * @return  the value of dictionary_company_addresses.created_at
	 * @mbggenerated
	 */
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column dictionary_company_addresses.created_at
	 * @param createdAt  the value for dictionary_company_addresses.created_at
	 * @mbggenerated
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
}