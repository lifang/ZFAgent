package com.comdosoft.financial.user.domain.zhangfu;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {

	public static  final byte ORDER_STATUS_PENDING = 1;//未付款
	public static  final byte ORDER_STATUS_PAD = 2;//已付款
	public static  final byte ORDER_STATUS_FINISH = 3;//已完成（已发货）
	public static  final byte ORDER_STATUS_COMMENT = 4;//已评价
	public static  final byte ORDER_STATUS_CANCEL = 5;//已取消
	public static  final byte ORDER_STATUS_CLOSE = 6;//交易关闭
	
	private Integer id;
	private String orderNumber;
	private Integer customerId;
	private Integer totalPrice;
	private Integer status;//订单状态
	private Date payedAt;
	private Byte types;
	private CustomerAddress customerAddress;
	private Boolean needInvoice;
	private Integer actualPrice;
	private Integer frontMoney;
	private Integer frontPayStatus;
	private Integer payStatus;
	private Integer createdUserId;
	private Byte createdUserType;
	private Integer processUserId;
	private String processUserName;
	private Date createdAt;
	private Date updatedAt;
	private Integer agentOrderMarksId;
	private Integer belongsTo;
	private Integer invoiceType;
	private String comment;
	private String invoiceInfo;
	private Integer totalQuantity;
	private Integer belongsUserId;
	private List<OrderGood> orderGoodsList = new ArrayList<OrderGood>();
	private List<CsOutStorage> csOutStorageList = new ArrayList<CsOutStorage>();
	private List<OrderMark> orderMarkList = new ArrayList<OrderMark>();
	private OrderPayment orderPayment;
	private OrderLogistic orderLogistic;
	
	
    /**
	 * @return the orderLogistic
	 */
	public OrderLogistic getOrderLogistic() {
		return orderLogistic;
	}

	/**
	 * @param orderLogistic the orderLogistic to set
	 */
	public void setOrderLogistic(OrderLogistic orderLogistic) {
		this.orderLogistic = orderLogistic;
	}

	/**  
     * 获取 belongsUserId  
     * @return belongsUserId
     */
    public Integer getBelongsUserId() {
        return belongsUserId;
    }

    /**  
     * 设置 belongsUserId  
     * @param belongsUserId
     */
    public void setBelongsUserId(Integer belongsUserId) {
        this.belongsUserId = belongsUserId;
    }

    /**  
     * 获取 csOutStorageList  
     * @return csOutStorageList
     */
    public List<CsOutStorage> getCsOutStorageList() {
        return csOutStorageList;
    }

    /**  
     * 设置 csOutStorageList  
     * @param csOutStorageList
     */
    public void setCsOutStorageList(List<CsOutStorage> csOutStorageList) {
        this.csOutStorageList = csOutStorageList;
    }

    /**  
     * 获取 orderPayment  
     * @return orderPayment
     */
    public OrderPayment getOrderPayment() {
        return orderPayment;
    }

    /**  
     * 设置 orderPayment  
     * @param orderPayment
     */
    public void setOrderPayment(OrderPayment orderPayment) {
        this.orderPayment = orderPayment;
    }

    /**  
     * 获取 orderMarkList  
     * @return orderMarkList
     */
    public List<OrderMark> getOrderMarkList() {
        return orderMarkList;
    }

    /**  
     * 设置 orderMarkList  
     * @param orderMarkList
     */
    public void setOrderMarkList(List<OrderMark> orderMarkList) {
        this.orderMarkList = orderMarkList;
    }

    /**  
     * 获取 customerAddress  
     * @return customerAddress
     */
    public CustomerAddress getCustomerAddress() {
        return customerAddress;
    }

    /**  
     * 设置 customerAddress  
     * @param customerAddress
     */
    public void setCustomerAddress(CustomerAddress customerAddress) {
        this.customerAddress = customerAddress;
    }

    /**  
     * 获取 totalQuantity  
     * @return totalQuantity
     */
    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    /**  
     * 设置 totalQuantity  
     * @param totalQuantity
     */
    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    /**  
     * 获取 orderGoodsList  
     * @return orderGoodsList
     */
    public List<OrderGood> getOrderGoodsList() {
        return orderGoodsList;
    }

    /**  
     * 设置 orderGoodsList  
     * @param orderGoodsList
     */
    public void setOrderGoodsList(List<OrderGood> orderGoodsList) {
        this.orderGoodsList = orderGoodsList;
    }

    /**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column orders.id
	 * @return  the value of orders.id
	 * @mbggenerated
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column orders.id
	 * @param id  the value for orders.id
	 * @mbggenerated
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column orders.order_number
	 * @return  the value of orders.order_number
	 * @mbggenerated
	 */
	public String getOrderNumber() {
		return orderNumber;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column orders.order_number
	 * @param orderNumber  the value for orders.order_number
	 * @mbggenerated
	 */
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column orders.customer_id
	 * @return  the value of orders.customer_id
	 * @mbggenerated
	 */
	public Integer getCustomerId() {
		return customerId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column orders.customer_id
	 * @param customerId  the value for orders.customer_id
	 * @mbggenerated
	 */
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column orders.total_price
	 * @return  the value of orders.total_price
	 * @mbggenerated
	 */
	public Integer getTotalPrice() {
		return totalPrice;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column orders.total_price
	 * @param totalPrice  the value for orders.total_price
	 * @mbggenerated
	 */
	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}

    public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column orders.payed_at
	 * @return  the value of orders.payed_at
	 * @mbggenerated
	 */
	public Date getPayedAt() {
		return payedAt;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column orders.payed_at
	 * @param payedAt  the value for orders.payed_at
	 * @mbggenerated
	 */
	public void setPayedAt(Date payedAt) {
		this.payedAt = payedAt;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column orders.types
	 * @return  the value of orders.types
	 * @mbggenerated
	 */
	public Byte getTypes() {
		return types;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column orders.types
	 * @param types  the value for orders.types
	 * @mbggenerated
	 */
	public void setTypes(Byte types) {
		this.types = types;
	}


	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column orders.need_invoice
	 * @return  the value of orders.need_invoice
	 * @mbggenerated
	 */
	public Boolean getNeedInvoice() {
		return needInvoice;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column orders.need_invoice
	 * @param needInvoice  the value for orders.need_invoice
	 * @mbggenerated
	 */
	public void setNeedInvoice(Boolean needInvoice) {
		this.needInvoice = needInvoice;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column orders.actual_price
	 * @return  the value of orders.actual_price
	 * @mbggenerated
	 */
	public Integer getActualPrice() {
		return actualPrice;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column orders.actual_price
	 * @param actualPrice  the value for orders.actual_price
	 * @mbggenerated
	 */
	public void setActualPrice(Integer actualPrice) {
		this.actualPrice = actualPrice;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column orders.front_money
	 * @return  the value of orders.front_money
	 * @mbggenerated
	 */
	public Integer getFrontMoney() {
		return frontMoney;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column orders.front_money
	 * @param frontMoney  the value for orders.front_money
	 * @mbggenerated
	 */
	public void setFrontMoney(Integer frontMoney) {
		this.frontMoney = frontMoney;
	}

	/**  
     * 获取 frontPayStatus  
     * @return frontPayStatus
     */
    public Integer getFrontPayStatus() {
        return frontPayStatus;
    }

    /**  
     * 设置 frontPayStatus  
     * @param frontPayStatus
     */
    public void setFrontPayStatus(Integer frontPayStatus) {
        this.frontPayStatus = frontPayStatus;
    }

 
    /**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column orders.created_user_id
	 * @return  the value of orders.created_user_id
	 * @mbggenerated
	 */
	public Integer getCreatedUserId() {
		return createdUserId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column orders.created_user_id
	 * @param createdUserId  the value for orders.created_user_id
	 * @mbggenerated
	 */
	public void setCreatedUserId(Integer createdUserId) {
		this.createdUserId = createdUserId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column orders.created_user_type
	 * @return  the value of orders.created_user_type
	 * @mbggenerated
	 */
	public Byte getCreatedUserType() {
		return createdUserType;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column orders.created_user_type
	 * @param createdUserType  the value for orders.created_user_type
	 * @mbggenerated
	 */
	public void setCreatedUserType(Byte createdUserType) {
		this.createdUserType = createdUserType;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column orders.process_user_id
	 * @return  the value of orders.process_user_id
	 * @mbggenerated
	 */
	public Integer getProcessUserId() {
		return processUserId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column orders.process_user_id
	 * @param processUserId  the value for orders.process_user_id
	 * @mbggenerated
	 */
	public void setProcessUserId(Integer processUserId) {
		this.processUserId = processUserId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column orders.process_user_name
	 * @return  the value of orders.process_user_name
	 * @mbggenerated
	 */
	public String getProcessUserName() {
		return processUserName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column orders.process_user_name
	 * @param processUserName  the value for orders.process_user_name
	 * @mbggenerated
	 */
	public void setProcessUserName(String processUserName) {
		this.processUserName = processUserName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column orders.created_at
	 * @return  the value of orders.created_at
	 * @mbggenerated
	 */
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column orders.created_at
	 * @param createdAt  the value for orders.created_at
	 * @mbggenerated
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column orders.updated_at
	 * @return  the value of orders.updated_at
	 * @mbggenerated
	 */
	public Date getUpdatedAt() {
		return updatedAt;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column orders.updated_at
	 * @param updatedAt  the value for orders.updated_at
	 * @mbggenerated
	 */
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column orders.agent_order_marks_id
	 * @return  the value of orders.agent_order_marks_id
	 * @mbggenerated
	 */
	public Integer getAgentOrderMarksId() {
		return agentOrderMarksId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column orders.agent_order_marks_id
	 * @param agentOrderMarksId  the value for orders.agent_order_marks_id
	 * @mbggenerated
	 */
	public void setAgentOrderMarksId(Integer agentOrderMarksId) {
		this.agentOrderMarksId = agentOrderMarksId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column orders.belongs_to
	 * @return  the value of orders.belongs_to
	 * @mbggenerated
	 */
	public Integer getBelongsTo() {
		return belongsTo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column orders.belongs_to
	 * @param belongsTo  the value for orders.belongs_to
	 * @mbggenerated
	 */
	public void setBelongsTo(Integer belongsTo) {
		this.belongsTo = belongsTo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column orders.invoice_type
	 * @return  the value of orders.invoice_type
	 * @mbggenerated
	 */
	public Integer getInvoiceType() {
		return invoiceType;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column orders.invoice_type
	 * @param invoiceType  the value for orders.invoice_type
	 * @mbggenerated
	 */
	public void setInvoiceType(Integer invoiceType) {
		this.invoiceType = invoiceType;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column orders.comment
	 * @return  the value of orders.comment
	 * @mbggenerated
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column orders.comment
	 * @param comment  the value for orders.comment
	 * @mbggenerated
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column orders.invoice_info
	 * @return  the value of orders.invoice_info
	 * @mbggenerated
	 */
	public String getInvoiceInfo() {
		return invoiceInfo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column orders.invoice_info
	 * @param invoiceInfo  the value for orders.invoice_info
	 * @mbggenerated
	 */
	public void setInvoiceInfo(String invoiceInfo) {
		this.invoiceInfo = invoiceInfo;
	}
}