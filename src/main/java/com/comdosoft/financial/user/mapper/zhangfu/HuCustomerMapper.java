package com.comdosoft.financial.user.mapper.zhangfu;

import java.util.List;
import java.util.Map;

import com.comdosoft.financial.user.domain.zhangfu.Customer;
import com.comdosoft.financial.user.domain.zhangfu.MyOrderReq;

public interface HuCustomerMapper {
    Map<Object, Object> getSysConfig(String paramName);

    Map<Object, Object> getOne(int id);

    void update(Map<Object, Object> param);

    void updatePassword(Map<Object, Object> param);

    int getTradeRecordsCount(int customerId);

    List<Map<Object, Object>> getIntegralList(Map<Object, Object> param);

    Map<Object, Object> getIntegralTotal(int customerId);

    void insertIntegralConvert(Map<Object, Object> param);

    void updateIntegralTotal(Map<Object, Object> param);

    void insertIntegralRecord(Map<Object, Object> param);

    List<Map<Object, Object>> getAddressList(int customerId);

    Map<Object, Object> getOneAddress(int id);

    void insertAddress(Map<Object, Object> param);

    void updateAddress(Map<Object, Object> param);

    void updateDefaultAddress(Map<Object, Object> param);

    void setDefaultAddress(Map<Object, Object> param);

    void deleteAddress(int id);

    Map<String, Object> findCustById(MyOrderReq req);

    void cust_update(Customer c);

    Object getjifen(MyOrderReq req);

    List<Map<String, Object>> findUsername(String p);



}
