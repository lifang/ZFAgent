package com.comdosoft.financial.user.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
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
	public int insertaddress(CustomerAddress req) {
		return customer_addressesMapper.insertadderss(req);
	}

	/**
	 * 删除地址
	 * 
	 * @param CustomerAddress
	 */
	public int deletetaddress(int param) {
		return customer_addressesMapper.deleteadderss(param);
	}

	public Map<String, Object> queryaddress(int id) {
		return customer_addressesMapper.queryaddress(id);
	}

	public void updateadderss(CustomerAddress req, int id) {
		customer_addressesMapper.upadderss(req, id);
	}

	public List<Map<String, Object>> queryadderss(int id) {
		return customer_addressesMapper.queryadderss(id);
	}

	public int setisDefault(CustomerAddress cus) {
		// param.put("is_default", CustomerAddress.ISDEFAULT_2);

		return customer_addressesMapper.setisDefault(cus);
	}

	public void updeteDefault(int oidDefault, int Default) {
		// param.put("is_default", CustomerAddress.ISDEFAULT_2);
		customer_addressesMapper.updateDefault(oidDefault, Default);
	}
}
