package com.comdosoft.financial.user.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comdosoft.financial.user.domain.query.EmpReq;
import com.comdosoft.financial.user.domain.query.MyAccountReq;
import com.comdosoft.financial.user.domain.zhangfu.Customer;
import com.comdosoft.financial.user.domain.zhangfu.CustomerAgentRelation;
import com.comdosoft.financial.user.domain.zhangfu.CustomerRoleRelation;
import com.comdosoft.financial.user.mapper.zhangfu.CustomerMapper;
import com.comdosoft.financial.user.mapper.zhangfu.SysconfigMapper;

@Service
public class SystemSetService {
	@Autowired
	private CustomerMapper customerMapper;

	@Autowired
	private SysconfigMapper sysConfigMapper;

	/**
	 * 插入用户信息(customers表)
	 * @param customer
	 */
	public void insertCustomer(Customer customer) {
		customerMapper.insertCustomer(customer);
	}

	/**
	 * 插入用户信息(customer_agent_relations表)
	 * @param empReq
	 */
	public void insertCustomerAgentRelations(CustomerAgentRelation empReq) {
		customerMapper.insertCustomerAgentRelations(empReq);
	}

	/**
	 * 根据员工ID查询员工信息
	 * 
	 * @param myAccountReq
	 * @return
	 */
	public Map<String, Object> getEmpInfoById(int id) {
		return customerMapper.getEmpInfoById(id);
	}

	/**
	 * 根据员工登陆名查询员工信息
	 * 
	 * @param myAccountReq
	 * @return
	 */
	public Map<String, Object> getEmpInfoByUsername(String userName) {
		return customerMapper.getEmpInfoByUsername(userName);
	}

	/**
	 * 根据ID删除用户信息(逻辑删除)
	 * 
	 * @param req
	 * @return
	 */
	public int deleteEmpInfoFromAgent(int id) {
		return customerMapper.deleteEmpInfoFromAgent(id);
	}

	/**
	 * 更新用户状态为不可用
	 * @param id
	 * @return
	 */
	public int updateCustomerStatus(int id) {
		return customerMapper.updateStatus(id);
	}

	public void insertCustomerRights(CustomerRoleRelation cr) {
		customerMapper.insertCustomerRights(cr);
	}

	/**
	 * 记录操作记录
	 * 
	 * @param content 操作内容<br/>
	 * @param operateUserId 操作人ID<br/>
	 */
	public int operateRecord(String content, int operateUserId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("content", content);
		map.put("operateUserId", operateUserId);
		return sysConfigMapper.operateRecord(map);
	}

	/**
	 * 重置密码
	 * @param map
	 * @return
	 */
	public int resetPassword(Map<String, Object> map) {
		return customerMapper.resetPassword(map);
	}

	/**
	 * 获得代理商员工信息
	 * @param customerId
	 * @return
	 */
	public Map<String, Object> getEmpInfoFromAgent(int customerId) {
		Map<String, Object> result = null;
		List<Map<String, Object>> list = customerMapper.getDetailInfoById(customerId);

		if (list != null && !list.isEmpty()) {
			int customer_id = 0;
			int role_id = 0;
			String role_name = "";
			String name = null;
			String username = null;
			String password = "";
			Map<String, Object> map = null;
			List<Integer> roleIds = new ArrayList<Integer>();
			List<String> roleNames = new ArrayList<String>();
			result = new HashMap<String, Object>();
			for (int i = 0, j = list.size(); i < j; i++) {
				map = list.get(i);
				if (map.get("role_id") != null) {
					role_id = Integer.parseInt(map.get("role_id").toString());
					role_name = getRoleName(role_id);
					roleIds.add(role_id);
					roleNames.add(role_name);
				}

				customer_id = Integer.parseInt(map.get("id").toString());
				name = map.get("name").toString();
				username = map.get("username").toString();
				password = map.get("password").toString();

			}
			result.put("customer_id", customer_id);
			result.put("name", name);
			result.put("username", username);
			result.put("rightIds", roleIds);
			result.put("roleNames", roleNames);
			result.put("password", password);

		}
		return result;
	}

	/**
	 * 获得权限名
	 * 
	 * @param role_id
	 * @return
	 */
	private String getRoleName(int role_id) {
		String role_name = "";
		if (role_id == 1) {
			role_name = CustomerRoleRelation.Right1;
		} else if (role_id == 2) {
			role_name = CustomerRoleRelation.Right2;
		} else if (role_id == 3) {
			role_name = CustomerRoleRelation.Right3;
		} else if (role_id == 4) {
			role_name = CustomerRoleRelation.Right4;
		} else if (role_id == 5) {
			role_name = CustomerRoleRelation.Right5;
		} else if (role_id == 6) {
			role_name = CustomerRoleRelation.Right6;
		} else if (role_id == 7) {
			role_name = CustomerRoleRelation.Right7;
		} else if (role_id == 8) {
			role_name = CustomerRoleRelation.Right8;
		} else if (role_id == 9) {
			role_name = CustomerRoleRelation.Right9;
		}
		return role_name;
	}

	/**
	 * 编辑用户信息
	 * @param req
	 * @return
	 */
	public int editCustomerInfo(EmpReq req) {
		return customerMapper.editCustomerInfo(req);
	}

	/**
	 * 删除用户权限
	 * 
	 * @param req
	 * @return
	 */
	public int deleteCustomerRights(EmpReq req) {
		return customerMapper.deleteCustomerRights(req);
	}

	/**
	 * 插入员工权限
	 * @param req
	 */
	public void insertRights(EmpReq req) {
		customerMapper.batchInsertRights(req);
	}

	/**
	 * 账户列表
	 * @param req
	 * @return
	 */
	public Map<String, Object> getAccountList(MyAccountReq req) {
		Map<String, Object> result = new HashMap<String, Object>();
		int total = customerMapper.count(req);
		List<Map<String, Object>> list = customerMapper.getAccountList(req);
		result.put("total", total);
		result.put("list", list);
		return result;
	}

}
