package com.comdosoft.financial.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comdosoft.financial.user.domain.query.CustomerManageReq;
import com.comdosoft.financial.user.mapper.zhangfu.CustomerManageMapper;

/**
 * 用于App用户管理
 * @author yangyibin
 *
 */
@Service
public class CustomerManageService {
	@Autowired
	private CustomerManageMapper customerManageMapper;
	
	@Autowired
	private SystemSetService sys;
	
	public Map<String, Object> getList(CustomerManageReq req) {
        Map<String, Object> map=new HashMap<String, Object>();
        int total=customerManageMapper.getTotal(req);
        map.put("total", total);
        List<Map<String, Object>> list=customerManageMapper.getCustomerList(req);
        map.put("list", list);
        return map;
    }
}
