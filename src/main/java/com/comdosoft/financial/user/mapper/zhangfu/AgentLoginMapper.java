package com.comdosoft.financial.user.mapper.zhangfu;



import java.util.List;
import java.util.Map;

import com.comdosoft.financial.user.domain.zhangfu.Agent;
import com.comdosoft.financial.user.domain.zhangfu.Customer;



/**
 * 用户登陆
 * @author xianfeihu
 *
 */
public interface AgentLoginMapper {
	
	/**
	 * 用户登陆
	 * @param customer
	 * @return
	 */
	Object doLogin(Customer customer);
	
	/**
	 * 找回密码
	 * @param customer
	 */
	void updatePassword(Customer customer);
	
	/**
	 * 添加用户
	 * @param customer
	 */
	void addUser(Customer customer);
	
	/**
	 * 查找用户
	 * @param customer
	 * @return
	 */
	int findUname(Customer customer);
	
	/**
	 * 修改登录时间
	 * @param customer
	 */
	void updateLastLoginedAt(Customer customer);
	
	/**
	 * 判断该城市是否有代理商
	 * @param customer
	 * @return
	 */
	int judgeCityId(Customer customer);
	
	/**
	 * 获取代理商编号
	 * @return
	 */
	Object getAgentCode();
	
	/**
	 * 添加代理商
	 * @param agent
	 */
	void addAgent(Agent agent);
	
	/**
	 * 代理商登陆后获得权限
	 * @param customer
	 * @return
	 */
	List<Map<String, String>> Toestemming(Customer customer);
}
