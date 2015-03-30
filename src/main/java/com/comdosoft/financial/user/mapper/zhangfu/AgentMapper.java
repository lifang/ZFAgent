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

    Map<Object, Object> getOne(Customer param);

    Customer getOneCustomer(Customer param);

    void updateCustomer(Customer param);

    void update(Agent param);

    List<Map<Object, Object>> getAddressList(Customer param);

    Map<Object, Object> getOneAddress(Customer param);

    void insertAddress(Map<Object, Object> param);

    void updateAddress(Map<Object, Object> param);

    void deleteAddress(Customer param);

}