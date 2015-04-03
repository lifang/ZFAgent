package com.comdosoft.financial.user.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.comdosoft.financial.user.domain.zhangfu.CustomerAddress;
import com.comdosoft.financial.user.mapper.zhangfu.CustomeraddressMapper;

@Service
public class CustomeraddressService {

	@Resource
	private CustomeraddressMapper customer_addressesMapper;

	/**
	 * 添加地址
	 * 
	 * @param req
	 * @return
	 */
	@Transactional(value = "transactionManager-zhangfu")
	public void insertAddress(Map<Object, Object> param) {
		int isDefault = Integer.parseInt(param.get("isDefault").toString());
		if (isDefault == CustomerAddress.ISDEFAULT_1) {
			param.put("is_default", CustomerAddress.ISDEFAULT_2);
			customer_addressesMapper.updateDefaultAddress(param);
		}
		customer_addressesMapper.insertAddress(param);

	}

	/**
	 * 删除地址
	 * 
	 * @param CustomerAddress
	 */
	public int deleteAddress(int param) {
		return customer_addressesMapper.deleteAddress(param);
	}

	/**
	 * 修改收获地址
	 * 
	 * @param param
	 */
	@Transactional(value = "transactionManager-zhangfu")
	public void updateAddress(Map<Object, Object> param) {
		int isDefault = Integer.parseInt(param.get("isDefault").toString());
		if (isDefault == CustomerAddress.ISDEFAULT_1) {
			param.put("is_default", CustomerAddress.ISDEFAULT_2);
			customer_addressesMapper.updateDefaultAddress(param);
		}
		customer_addressesMapper.updateAddress(param);
	}

	/**
	 * 查询代理商收获地址
	 * 
	 * @param id
	 * @return
	 */
	public List<Map<String, Object>> queryAddress(int id) {
		return customer_addressesMapper.queryAddress(id);
	}

	/**
	 * 获得代理商所有有用的收获地址
	 * 
	 * @param id
	 * @return
	 */
	public int countValidAddress(int id) {
		return customer_addressesMapper.countValidAddress(id);
	}

	/**
	 * 设置默认地址
	 * 
	 * @param param
	 */
	@Transactional(value = "transactionManager-zhangfu")
	public void setDefaultAddress(Map<Object, Object> param) {
		param.put("is_default", CustomerAddress.ISDEFAULT_2); // 其它设置为非默认
		customer_addressesMapper.updateDefaultAddress(param);
		customer_addressesMapper.setNotDefaultAddress(param);
		customer_addressesMapper.setDefaultAddress(param);
	}

}
