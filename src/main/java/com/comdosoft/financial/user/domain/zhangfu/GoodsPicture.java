package com.comdosoft.financial.user.domain.zhangfu;

import java.util.Date;

public class GoodsPicture {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column goods_pictures.id
	 * @mbggenerated
	 */
	private Integer id;
	private String urlPath;
	private Integer goodId;
	private Date createdAt;
	private Good good;
	
	/**  
     * 获取 good  
     * @return good
     */
    public Good getGood() {
        return good;
    }

    /**  
     * 设置 good  
     * @param good
     */
    public void setGood(Good good) {
        this.good = good;
    }

    /**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column goods_pictures.id
	 * @return  the value of goods_pictures.id
	 * @mbggenerated
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column goods_pictures.id
	 * @param id  the value for goods_pictures.id
	 * @mbggenerated
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column goods_pictures.url_path
	 * @return  the value of goods_pictures.url_path
	 * @mbggenerated
	 */
	public String getUrlPath() {
		return urlPath;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column goods_pictures.url_path
	 * @param urlPath  the value for goods_pictures.url_path
	 * @mbggenerated
	 */
	public void setUrlPath(String urlPath) {
		this.urlPath = urlPath;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column goods_pictures.good_id
	 * @return  the value of goods_pictures.good_id
	 * @mbggenerated
	 */
	public Integer getGoodId() {
		return goodId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column goods_pictures.good_id
	 * @param goodId  the value for goods_pictures.good_id
	 * @mbggenerated
	 */
	public void setGoodId(Integer goodId) {
		this.goodId = goodId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column goods_pictures.created_at
	 * @return  the value of goods_pictures.created_at
	 * @mbggenerated
	 */
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column goods_pictures.created_at
	 * @param createdAt  the value for goods_pictures.created_at
	 * @mbggenerated
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
}