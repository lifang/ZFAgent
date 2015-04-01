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

    int updateCustomer(Customer param);

    void update(Agent param);

    List<Map<Object, Object>> getAddressList(Customer param);

    Map<Object, Object> getOneAddress(Customer param);

    void insertAddress(Map<Object, Object> param);

    void updateAddress(Map<Object, Object> param);

    void deleteAddress(Customer param);
    
    /***
     * 
     * 查询商户
     * @param id
     * @return
     */
    Map<Object, Object>  queryAgent( int id);
    /**
     * 根据customerid查询商户信息
     * @param id
     * @return
     */
    Map<String, Object>   findAgentByCustomerId( int customer_id);
    
    void setDefaultAddress(Map<Object, Object> param);
    void setNotDefaultAddress(Map<Object, Object> param);

	void updatePhoneNumber(Customer param);

	void updateEmailAddr(Customer param);

    /**
     * 查询代理商下的商户总数
     * @param param
     * @return
     */
	Map<String, Object> getCommercialTenantCount(Map<Object, Object> param);

	/**
	 * 查询代理商下的商户列表
	 * @param param
	 * @return
	 */
	List<Map<String, Object>> getCommercialTenantList(Map<Object, Object> param);

	/**
	 * 删除单个商户(更新customers表)
	 * @param param
	 * @return
	 */
	int deleteCommercial(Map<Object, Object> param);
	
	/**
	 * 删除单个商户(更新customer_agent_relations表)
	 * @param param
	 * @return
	 */
	int updateCommercialStatus(Map<Object, Object> param);

}
