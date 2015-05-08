package com.comdosoft.financial.user.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.comdosoft.financial.user.domain.zhangfu.CustomerAddress;
import com.comdosoft.financial.user.mapper.zhangfu.CustomerAddressMapper;

@Service
public class CustomerAddressService {

	@Resource
	private CustomerAddressMapper customerAddressMapper;

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
			customerAddressMapper.updateDefaultAddress(param);
		}
		customerAddressMapper.insertAddress(param);

	}

	/**
	 * 删除地址
	 * 
	 * @param CustomerAddress
	 */
	public int deleteAddress(int param) {
		return customerAddressMapper.deleteAddress(param);
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
			customerAddressMapper.updateDefaultAddress(param);
		}
		customerAddressMapper.updateAddress(param);
	}

	/**
	 * 查询代理商收获地址
	 * 
	 * @param id
	 * @return
	 */
	public List<Map<String, Object>> queryAddress(int id) {
		return customerAddressMapper.queryAddress(id);
	}

	/**
	 * 获得代理商所有有用的收获地址
	 * 
	 * @param id
	 * @return
	 */
	public int countValidAddress(int id) {
		return customerAddressMapper.countValidAddress(id);
	}

	/**
	 * 设置默认地址
	 * 
	 * @param param
	 */
	@Transactional(value = "transactionManager-zhangfu")
	public void setDefaultAddress(Map<Object, Object> param) {
		param.put("is_default", CustomerAddress.ISDEFAULT_2); // 其它设置为非默认
		customerAddressMapper.updateDefaultAddress(param);
		customerAddressMapper.setNotDefaultAddress(param);
		customerAddressMapper.setDefaultAddress(param);
	}

	public List<Map<String, Object>> getcityname(Map<String, Object> param) {
		return customerAddressMapper.getcityname(param);
	}

}
