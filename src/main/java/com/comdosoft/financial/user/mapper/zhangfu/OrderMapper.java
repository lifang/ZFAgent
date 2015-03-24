package com.comdosoft.financial.user.mapper.zhangfu;

import java.util.List;
import java.util.Map;

import com.comdosoft.financial.user.domain.query.OrderReq;
import com.comdosoft.financial.user.domain.zhangfu.Customer;
import com.comdosoft.financial.user.domain.zhangfu.GoodsPicture;
import com.comdosoft.financial.user.domain.zhangfu.MyOrderReq;
import com.comdosoft.financial.user.domain.zhangfu.Order;
import com.comdosoft.financial.user.domain.zhangfu.Terminal;

public interface OrderMapper {

    void addOrder(OrderReq orderreq);


    void addOrderGood(OrderReq orderreq);

    Map<String, Object> getGoodInfo(OrderReq orderreq);

// ----gch start --------------
    int countWholesaleOrder(Integer pid);
    int countProxyOrder(Integer pid);

    List<Order> getWholesaleOrder(MyOrderReq myOrderReq);
    List<Order> getProxyOrder(MyOrderReq myOrderReq);

    Order getWholesaleById(Integer id);
    Order getProxyById(Integer id);

    int cancelMyOrder(MyOrderReq myOrderReq);

    void comment(MyOrderReq myOrderReq);

    List<GoodsPicture> findPicByGoodId(Integer gid);

    List<Map<String, Object>> findTraceById(MyOrderReq myOrderReq);
    
    Customer findCustomerById(Customer person);
// ------gch end ---------------------

//根据订单id获取终端号
    List<Terminal>  getTerminsla(Integer id);
}
