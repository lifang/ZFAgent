package com.comdosoft.financial.user.mapper.zhangfu;

import java.util.List;
import java.util.Map;

import com.comdosoft.financial.user.domain.zhangfu.Agent;
import com.comdosoft.financial.user.domain.zhangfu.Customer;

/**
 * 代理商 - 数据层
 * 
 * @author
 *
 */
public interface AgentMapper {

    Map<Object, Object> getOne(int customerId);

    Customer getOneCustomer(int customerId);

    void updateCustomer(Customer param);

    void update(Agent param);

    List<Map<Object, Object>> getAddressList(int customerId);

    Map<Object, Object> getOneAddress(int id);

    void insertAddress(Map<Object, Object> param);

    void updateAddress(Map<Object, Object> param);

    void deleteAddress(int id);

}