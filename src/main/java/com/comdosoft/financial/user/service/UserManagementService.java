package com.comdosoft.financial.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.comdosoft.financial.user.domain.zhangfu.Customer;
import com.comdosoft.financial.user.domain.Paging;
import com.comdosoft.financial.user.mapper.zhangfu.OpeningApplyMapper;
import com.comdosoft.financial.user.mapper.zhangfu.TerminalsMapper;
import com.comdosoft.financial.user.mapper.zhangfu.UserManagementMapper;

@Service
public class UserManagementService {
	@Resource
	private UserManagementMapper userManagementMapper;
	
	@Resource
	private OpeningApplyMapper openingApplyMapper;
	
	@Resource
	private TerminalsMapper terminalsMapper;
	
	/**
	 * 获得该代理商所有相关用户
	 * @param customerId
	 * @return
	 */
	public List<Map<String, Object>> getUser(int customerId,int status){
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("customerId", customerId);
		map.put("status", status);
		return userManagementMapper.getUser(map);
	}

	/**
	 * 删除用户与代理商之间的关联
	 * @param id
	 */
	public void delectAgentUser(int id,int customerId,int status){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("customerId", customerId);
		map.put("status", status);
		userManagementMapper.delectAgentUser(map);
	}
	
	/**
	 * 获得该用户所用终端
	 * @param customerId
	 * @return
	 */
	public List<Map<String, Object>> getTerminals(int customerId,Integer offSetPage, Integer pageSize) {
		Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", customerId);
			map.put("offSetPage", offSetPage);
			map.put("pageSize", pageSize);
		return userManagementMapper.getTerminals(map);
	}

	public Map<Object, Object> queryCustomer(int id) {
		 return userManagementMapper.queryCustomer(id);
	}

	public int getTerminalListTotalCount(int customerId) {
		return userManagementMapper.getTerminalListTotalCount(customerId);
	}

	public List<Map<String, Object>> getTerminalList(int customerId, int page, int rows) {
		Map<Object, Object> query = new HashMap<Object, Object>();
		query.put("id", customerId);
		Paging paging = new Paging(page, rows);
		query.put("offSetPage", paging.getOffset());
		query.put("pageSize", paging.getRows());
		return userManagementMapper.getTerminalList(query);
	}
	
	/**
	 * 判断用户是否存在
	 * @param map
	 */
	public int findUname(Map<Object, Object> map){
		return userManagementMapper.findUname(map);
	}
	
	/**
	 * 添加新用户
	 * @param map
	 */
	public void addUser(Customer customer){
		userManagementMapper.addUser(customer);
	}

	/**
	 * 根据商户ID查找商户信息
	 * 
	 * @param map
	 * @return
	 */
	public Map<String, Object> queryMerchantInfo(int customerId) {
		return userManagementMapper.queryMerchantInfo(customerId);
	}
	
}
