package com.comdosoft.financial.user.domain.zhangfu;

import java.util.Arrays;
import java.util.List;

import com.comdosoft.financial.user.domain.query.PayReq;

public class MyOrderReq {
	private Integer id;// 业务id
	private String[] ids;
	private Integer page;// 当前页数
	private Integer offset;
	private String search;// 搜索条件
	private String phone;// 手机号
	private String userName;
	private String email;
	private String q;// 状态值
	private String p;// 订单类型  5批购  4代理商代租赁 3代理商代购
	private Integer rows;// 每页大小
	private Integer customerId;// 用户id
	private String content;// 内容
	private PayType payType;
	private OrderStatus orderStatus;
	private RepairStatus repairStatus;
	private ServiceStatus serviceStatus;// 售后状态;
	private UpdateStatus updateStatus;
	private Integer score;// 分数
	private Integer good_id;
	private String computer_name;
	private String track_number;
	private List<CommentsJson> json;
	private String agentId;
	private CancelType cancel_flag; //取消类型
	//支付回调信息
	private PayReq req;
	private String webPrice;//web页面上的金额
	 
	public String getWebPrice() {
		return webPrice;
	}

	public void setWebPrice(String webPrice) {
		this.webPrice = webPrice;
	}

	public CancelType getCancel_flag() {
		return cancel_flag;
	}

	public void setCancel_flag(CancelType cancel_flag) {
		this.cancel_flag = cancel_flag;
	}

	/** 
	 * 获取 req 
	 * @return req
	 */
	public PayReq getReq() {
		return req;
	}

	/** 
	 *  设置 req 
	 *  @param req
	 */
	public void setReq(PayReq req) {
		this.req = req;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public ServiceStatus getServiceStatus() {
		return serviceStatus;
	}

	public void setServiceStatus(ServiceStatus serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getP() {
		return p;
	}

	public void setP(String p) {
		this.p = p;
	}

	/**
	 * 获取 email
	 * 
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * 设置 email
	 * 
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 获取 phone
	 * 
	 * @return phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * 设置 phone
	 * 
	 * @param phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * 获取 json
	 * 
	 * @return json
	 */
	public List<CommentsJson> getJson() {
		return json;
	}

	/**
	 * 设置 json
	 * 
	 * @param json
	 */
	public void setJson(List<CommentsJson> json) {
		this.json = json;
	}

	/**
	 * 获取 search
	 * 
	 * @return search
	 */
	public String getSearch() {
		return search;
	}

	/**
	 * 设置 search
	 * 
	 * @param search
	 */
	public void setSearch(String search) {
		this.search = search;
	}

	/**
	 * 获取 q
	 * 
	 * @return q
	 */
	public String getQ() {
		return q;
	}

	/**
	 * 设置 q
	 * 
	 * @param q
	 */
	public void setQ(String q) {
		this.q = q;
	}

	/**
	 * 获取 updateStatus
	 * 
	 * @return updateStatus
	 */
	public UpdateStatus getUpdateStatus() {
		return updateStatus;
	}

	/**
	 * 设置 updateStatus
	 * 
	 * @param updateStatus
	 */
	public void setUpdateStatus(UpdateStatus updateStatus) {
		this.updateStatus = updateStatus;
	}

	/**
	 * 获取 computer_name
	 * 
	 * @return computer_name
	 */
	public String getComputer_name() {
		return computer_name;
	}

	/**
	 * 设置 computer_name
	 * 
	 * @param computer_name
	 */
	public void setComputer_name(String computer_name) {
		this.computer_name = computer_name;
	}

	/**
	 * 获取 track_number
	 * 
	 * @return track_number
	 */
	public String getTrack_number() {
		return track_number;
	}

	/**
	 * 设置 track_number
	 * 
	 * @param track_number
	 */
	public void setTrack_number(String track_number) {
		this.track_number = track_number;
	}

	/**
	 * 获取 score
	 * 
	 * @return score
	 */
	public Integer getScore() {
		return score;
	}

	/**
	 * 设置 score
	 * 
	 * @param score
	 */
	public void setScore(Integer score) {
		this.score = score;
	}

	/**
	 * 获取 good_id
	 * 
	 * @return good_id
	 */
	public Integer getGood_id() {
		return good_id;
	}

	/**
	 * 设置 good_id
	 * 
	 * @param good_id
	 */
	public void setGood_id(Integer good_id) {
		this.good_id = good_id;
	}

	/**
	 * 获取 content
	 * 
	 * @return content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 设置 content
	 * 
	 * @param content
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 获取 repairStatus
	 * 
	 * @return repairStatus
	 */
	public RepairStatus getRepairStatus() {
		return repairStatus;
	}

	/**
	 * 设置 repairStatus
	 * 
	 * @param repairStatus
	 */
	public void setRepairStatus(RepairStatus repairStatus) {
		this.repairStatus = repairStatus;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	/**
	 * 获取 orderStatus
	 * 
	 * @return orderStatus
	 */
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	/**
	 * 设置 orderStatus
	 * 
	 * @param orderStatus
	 */
	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	/**
	 * 获取 payStatus
	 * 
	 * @return payStatus
	 */
	public PayType getPayStatus() {
		return payType;
	}

	/**
	 * 设置 payStatus
	 * 
	 * @param payType
	 */
	public void setPayStatus(PayType payType) {
		this.payType = payType;
	}

	/**
	 * 获取 ids
	 * 
	 * @return ids
	 */
	public String[] getIds() {
		return ids;
	}

	/**
	 * 设置 ids
	 * 
	 * @param ids
	 */
	public void setIds(String[] ids) {
		this.ids = ids;
	}

	/**
	 * 获取 id
	 * 
	 * @return id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * 设置 id
	 * 
	 * @param id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 获取 page
	 * 
	 * @return page
	 */
	public Integer getPage() {
		if (null == page)
			page = 0;
		return page;
	}

	/**
	 * 设置 page
	 * 
	 * @param page
	 */
	public void setPage(Integer page) {
		this.page = page;
	}

	/**
	 * 获取 pageSize
	 * 
	 * @return pageSize
	 */
	public Integer getRows() {
		if (null == rows)
			rows = 10;
		return rows;
	}

	/**
	 * 设置 pageSize
	 * 
	 * @param pageSize
	 */
	public void setPageSize(Integer pageSize) {
		this.rows = pageSize;
	}

	/**
	 * 获取 payType
	 * 
	 * @return payType
	 */
	public PayType getPayType() {
		return payType;
	}

	/**
	 * 设置 payType
	 * 
	 * @param payType
	 */
	public void setPayType(PayType payType) {
		this.payType = payType;
	}

	public MyOrderReq() {
		super();
	}

	public int getOffset() {
		if (page > 0) {
			return (page - 1) * rows;
		}
		return 0;
	}

	/* (non-Javadoc)
	* <p>Title: toString</p> 
	* <p>Description: </p> 
	* @return 
	* @see java.lang.Object#toString() 
	 */
	@Override
	public String toString() {
		return "MyOrderReq [id=" + id + ", ids=" + Arrays.toString(ids)
				+ ", page=" + page + ", offset=" + offset + ", search="
				+ search + ", phone=" + phone + ", userName=" + userName
				+ ", email=" + email + ", q=" + q + ", p=" + p + ", rows="
				+ rows + ", customerId=" + customerId + ", content=" + content
				+ ", payType=" + payType + ", orderStatus=" + orderStatus
				+ ", repairStatus=" + repairStatus + ", serviceStatus="
				+ serviceStatus + ", updateStatus=" + updateStatus + ", score="
				+ score + ", good_id=" + good_id + ", computer_name="
				+ computer_name + ", track_number=" + track_number + ", json="
				+ json + ", agentId=" + agentId + ", out_trade_no="
				+ "]";
	}


}
