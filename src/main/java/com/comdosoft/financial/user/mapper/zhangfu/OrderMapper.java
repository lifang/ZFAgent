package com.comdosoft.financial.user.mapper.zhangfu;

import java.util.List;
import java.util.Map;

import com.comdosoft.financial.user.domain.query.OrderReq;
import com.comdosoft.financial.user.domain.zhangfu.CsOutStorage;
import com.comdosoft.financial.user.domain.zhangfu.Customer;
import com.comdosoft.financial.user.domain.zhangfu.GoodsPicture;
import com.comdosoft.financial.user.domain.zhangfu.MyOrderReq;
import com.comdosoft.financial.user.domain.zhangfu.Order;
import com.comdosoft.financial.user.domain.zhangfu.OrderGood;
import com.comdosoft.financial.user.domain.zhangfu.OrderPayment;
import com.comdosoft.financial.user.domain.zhangfu.Terminal;

public interface OrderMapper {

    void addOrder(OrderReq orderreq);


    void addOrderGood(OrderReq orderreq);

    Map<String, Object> getGoodInfo(OrderReq orderreq);
    
    Map<String, Object> getOrderByMumber(OrderReq orderreq);

    void payFinish(OrderReq orderreq);

    void upOrder(OrderReq orderreq);
 

// ----gch start --------------
    int countWholesaleOrder(MyOrderReq myOrderReq);//批购查询
    
    int countProxyOrder(MyOrderReq myOrderReq);//代购查询

    List<Order> getWholesaleOrder(MyOrderReq myOrderReq);//批购
    
    List<Order> getProxyOrder(MyOrderReq myOrderReq);//代购
    
    List<Order> getWholesaleById(Integer id);
    
    Order getProxyById(Integer id);

    int cancelMyOrder(MyOrderReq myOrderReq);

    void comment(MyOrderReq myOrderReq);

    List<GoodsPicture> findPicByGoodId(Integer gid);

    List<Map<String, Object>> findTraceById(MyOrderReq myOrderReq);
    
    Customer findCustomerById(Customer person);
    
    List<OrderGood> findGoodsByPOrderId(Integer id);
    List<OrderGood> findGoodsByWOrderId(Integer id);
// ------gch end ---------------------

//根据订单id获取终端号
//    List<Terminal>  getTerminsla(Integer id);
    List<Terminal> getTerminsla(Integer order_id,Integer goods_id);
    
//根据id查订单
    List<Map<String, Object>>  findOrderById(MyOrderReq myOrderReq);

//代购 归还库存
	void updateGoods(MyOrderReq myOrderReq);


	void update_goods_stock(String good_id, String quantity);

	List<CsOutStorage> getOutStorageByOrderId(Integer id);

	List<OrderPayment> getOrderPayByOrderId(Integer id);

	List<Order> findOrderByNumber(String number);

	int insertOrderPayment(OrderPayment op);

	/**
	 *
	* @Title: paySuccessUpdateOrder 
	* @Description:   
	*  i int     1为定金付款   2 为余额支付  3  付款金额= 剩下的钱
	* @throws
	 */
	int paySuccessUpdateOrder(Integer orderId,byte status, int i);

	//查询是否存在多条相同的支付记录
	int countOrderPaymentByNum(String no);


	Order getOrderById(MyOrderReq req);
  
}
