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
	 * 代理商登陆
	 * @param customer
	 * @return
	 */
	Map<Object, Object> doLogin(Customer customer);
	
	/**
	 * 员工登陆
	 * @param customer
	 * @return
	 */
	Map<Object, Object> doLoginPersn(Customer customer);
	/**
	 * 判断是代理商还是代理商下面员工
	 * @param username
	 * @return
	 */
	Map<Object, Object> isAgentOrPerson(Map<Object, Object> map);
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
	 * 修改验证码
	 * @param customer
	 */
	void updateDentcode(Customer customer);
	
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
	Object getAgentCode(int parentId);
	
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
	List<Object> Toestemming(Customer customer);
	
	/**
	 * 获取验证码
	 * @param customer
	 * @return
	 */
	String findCode(Customer customer);
	
	/**
	 * 修改密码
	 * @param customer	
	 */
	int modifyPassword(Customer customer);

	/**
	 * 查找旧密码
	 * @param id
	 * @return
	 */
	String findCustomerPwdById(Integer id);
}
