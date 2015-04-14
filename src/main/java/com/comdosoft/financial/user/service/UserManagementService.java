package com.comdosoft.financial.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.comdosoft.financial.user.domain.zhangfu.Customer;
import com.comdosoft.financial.user.domain.zhangfu.CustomerAgentRelation;
import com.comdosoft.financial.user.domain.Paging;
import com.comdosoft.financial.user.domain.Response;
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
	 * 
	 * @param customerId
	 * @return
	 */
	public List<Map<String, Object>> getUser(int customerId, int status, int types, int offSetPage, int rows) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("customerId", customerId);
		map.put("status", status);
		map.put("types", types);
		map.put("offSetPage", offSetPage);
		map.put("pageSize", rows);
		return userManagementMapper.getUser(map);
	}

	/**
	 * 获得该代理商所有相关用户
	 * 
	 * @param customerId
	 * @return
	 */
	public List<Map<String, Object>> getWebUser(int agentId, int status, int types, String keys) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("agentId", agentId);
		map.put("status", status);
		map.put("types", types);
		map.put("keys", keys);
		return userManagementMapper.getWebUser(map);
	}

	/**
	 * 删除用户与代理商之间的关联
	 * 
	 * @param id
	 */
	public void delectAgentUser(int id, int customerId, int status,int types) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("customerId", customerId);
		map.put("status", status);
		map.put("types", types);
		userManagementMapper.delectAgentUser(map);
	}
	
	/**
	 * 判断用户下面所有终端是否全部注销或者已取消
	 * 
	 * @param id
	 * @return 
	 */
	public int TerminalStatus(int customerId, int status1,int status2) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("customerId", customerId);
		map.put("status1", status1);
		map.put("status2", status2);
		return userManagementMapper.TerminalStatus(map);
	}

	/**
	 * 获得该用户所用终端
	 * 
	 * @param customerId
	 * @return
	 */
	public List<Map<String, Object>> getTerminals(int customerId, Integer offSetPage, Integer pageSize) {
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
	 * 
	 * @param map
	 */
	public int findUname(Map<Object, Object> map) {
		return userManagementMapper.findUname(map);
	}

	/**
	 * 添加新用户
	 * 
	 * @param map
	 */
	@Transactional(value = "transactionManager-zhangfu")
	public void addUser(Customer customer) {
		userManagementMapper.addUser(customer);
	}

	
	/**
	 * 添加-修改新用户
	 * 
	 * @param map
	 */
	@Transactional(value = "transactionManager-zhangfu")
	public void updateCustomer(Customer customer) {
		userManagementMapper.updateCustomer(customer);
	}

	/**
	 * 为新添加用户绑定关系
	 * 
	 * @param map
	 * @return
	 */
	public void addCustomerOrAgent(CustomerAgentRelation customerAgentRelation) {
		userManagementMapper.addCustomerOrAgent(customerAgentRelation);
	}

	/**
	 * 为新添加用户绑定关系
	 * 
	 * @param map
	 * @return
	 */
	public Response addCustomerOrAgents(Map<Object, Object> map) {
		CustomerAgentRelation customerAgentRelation = new CustomerAgentRelation();
		if (userManagementMapper.findUname(map) > 0) {
			return Response.getError("用户已存在！");
		} else {
			// 添加新用户
			Customer customer = new Customer();
			customer.setUsername((String) map.get("username"));
			customer.setName((String) map.get("name"));
			customer.setPassword((String) map.get("pass1"));
			customer.setCityId((Integer) map.get("cityid"));
			customer.setTypes(Customer.TYPE_CUSTOMER);
			customer.setStatus(Customer.STATUS_NORMAL);
			customer.setIntegral(0);
			userManagementMapper.addUser(customer);
			// 对该代理商已改用户绑定关系
			customerAgentRelation.setCustomerId(customer.getId());
			customerAgentRelation.setAgentId((Integer) map.get("agentId"));
			customerAgentRelation.setTypes(CustomerAgentRelation.TYPES_USER_TO_AGENT);
			customerAgentRelation.setStatus(CustomerAgentRelation.STATUS_1);
			userManagementMapper.addCustomerOrAgent(customerAgentRelation);
			return Response.getSuccess(customer);
		}
	}

	/**
	 * 根据商户ID查找商户信息
	 * 
	 * @param map
	 * @return
	 */
	public Map<String, Object> queryMerchantInfo(int customerId) {
		Map<String, Object> result = userManagementMapper.queryMerchantInfo(customerId);
		if (result != null && !result.isEmpty()) {
			String province = result.get("provincename") == null ? "" : result.get("provincename").toString();
			String cityname = result.get("cityname") == null ? "" : result.get("cityname").toString();
			result.put("address", province + cityname);
			return result;
		}
		return null;
	}

}
