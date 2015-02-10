package com.comdosoft.financial.user.mapper.zhangfu;



import com.comdosoft.financial.user.domain.zhangfu.Customer;



/**
 * 用户登陆
 * @author xianfeihu
 *
 */
public interface UserLoginMapper {
	
	/**
	 * 用户登陆
	 * @param customer
	 * @return
	 */
	int doLogin(Customer customer);
	
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
}
