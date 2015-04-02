package com.comdosoft.financial.user.mapper.zhangfu;

import java.util.List;
import java.util.Map;

import com.comdosoft.financial.user.domain.zhangfu.CustomerAddress;

/***
 * 收货地址
 * 
 * @author huashuo
 * 
 */

public interface CustomeraddressMapper {

	int insertadderss(CustomerAddress req);

	Map<String, Object> queryaddress(int id);

	int setisDefault(CustomerAddress cus);

	void updateDefault(int oidDefault, int Default);

	/**
	 * 查询代理商收获地址
	 * 
	 * @param id
	 * @return
	 */
	List<Map<String, Object>> queryAddress(int id);

	/**
	 * 修改默认地址
	 * 
	 * @param param
	 */
	void updateDefaultAddress(Map<Object, Object> param);

	/**
	 * 插入新地址
	 * 
	 * @param param
	 */
	void insertAddress(Map<Object, Object> param);

	/**
	 * 修改收获地址
	 * 
	 * @param param
	 */
	void updateAddress(Map<Object, Object> param);

	/**
	 * 删除地址
	 * 
	 * @param param
	 * @return
	 */
	int deleteAddress(int param);

	/**
	 * 取消默认地址
	 * 
	 * @param param
	 */
	void setNotDefaultAddress(Map<Object, Object> param);

	/**
	 * 设置默认地址
	 * 
	 * @param param
	 */
	void setDefaultAddress(Map<Object, Object> param);

}
