package com.comdosoft.financial.user.domain.query;

public class PayReq {
	private String out_trade_no;//订单号
	private String payPrice;//支付金额
	private String status;//支付后的状态
	private String trade_no;//支付宝 返回 交易流水号
	
	/** 
	 * 获取 trade_no 
	 * @return trade_no
	 */
	public String getTrade_no() {
		return trade_no;
	}
	/** 
	 *  设置 trade_no 
	 *  @param trade_no
	 */
	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}
	/** 
	 * 获取 out_trade_no 
	 * @return out_trade_no
	 */
	public String getOut_trade_no() {
		return out_trade_no;
	}
	/** 
	 *  设置 out_trade_no 
	 *  @param out_trade_no
	 */
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	/** 
	 * 获取 payPrice 
	 * @return payPrice
	 */
	public String getPayPrice() {
		return payPrice;
	}
	/** 
	 *  设置 payPrice 
	 *  @param payPrice
	 */
	public void setPayPrice(String payPrice) {
		this.payPrice = payPrice;
	}
	/** 
	 * 获取 status 
	 * @return status
	 */
	public String getStatus() {
		return status;
	}
	/** 
	 *  设置 status 
	 *  @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	/* (non-Javadoc)
	* <p>Title: toString</p> 
	* <p>Description: </p> 
	* @return 
	* @see java.lang.Object#toString() 
	 */
	@Override
	public String toString() {
		return "PayReq [out_trade_no=" + out_trade_no + ", payPrice="
				+ payPrice + ", status=" + status + ", trade_no=" + trade_no
				+ "]";
	}
	public PayReq() {
	}
	
}
