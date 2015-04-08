package com.comdosoft.financial.user.mapper.zhangfu;

import java.util.List;
import java.util.Map;

import com.comdosoft.financial.user.domain.zhangfu.Customer;

/**
 * 用户终端管理
 * 
 * @author xianfeihu
 *
 */
public interface UserManagementMapper {

	/**
	 * 获得该代理商所有相关用户
	 * @param customerId
	 * @return
	 */
	List<Map<String, Object>> getUser(Map<Object, Object> map);
	
	/**
	 * 删除用户与代理商之间的关联
	 * @param id
	 */
	void delectAgentUser(Map<String, Object> map);
	
	/**
	 * 获得该用户所有终端
	 * @param id
	 * @return
	 */
	List<Map<String, Object>> getTerminals(Map<String, Object> map);

	Map<Object, Object> queryCustomer(int id);

	int getTerminalListTotalCount(int id);

	List<Map<String, Object>> getTerminalList(Map<Object, Object> query);
	
	 /**
     * 判断用户是否存在
     * @param map
     */
    int findUname(Map<Object, Object> map);
    
    /**
     * 添加新用户
     * @param map
     */
    void addUser(Customer customer);

    /**
	 * 根据商户ID查找商户信息
	 * 
	 * @param map
	 * @return
	 */
	Map<String, Object> queryMerchantInfo(int customerId);
	
}
