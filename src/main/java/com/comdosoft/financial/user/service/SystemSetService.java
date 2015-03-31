package com.comdosoft.financial.user.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comdosoft.financial.user.domain.Paging;
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
	 * 代理商创建用户
	 * 
	 * @param empReq
	 */
	public void addCustomer(EmpReq empReq) {
		customerMapper.addCustomer(empReq);
	}

	public void insertCustomer(Customer customer) {
		customerMapper.insertCustomer(customer);
	}

	/**
	 * 验证用户名是否重复
	 * 
	 * @param empReq
	 * @return
	 */
	public Map<String, Object> checkAccount(EmpReq empReq) {
		return customerMapper.checkAccount(empReq);
	}

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
	 * 根据ID查询代理商所有权限
	 * 
	 * @param req
	 * @return
	 */
	public List<Object> getWholeRightsByAgentId(MyAccountReq req) {
		return customerMapper.getWholeRightsByAgentId(req);
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

	public int updateCustomerStatus(int id) {
		return customerMapper.updateStatus(id);
	}

	public int getListCount(int customerId) {
		return customerMapper.count(customerId);
	}

	/**
	 * 获取代理商旗下的用户
	 * 
	 * @param customerId
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<Map<String, Object>> getList(int customerId, int page, int rows) {
		Map<Object, Object> query = new HashMap<Object, Object>();
		query.put("customerId", customerId);
		Paging paging = new Paging(page, rows);
		query.put("offset", paging.getOffset());
		query.put("rows", paging.getRows());

		return customerMapper.getAccountList(query);
	}

	public void insertCustomerRights(CustomerRoleRelation cr) {
		customerMapper.insertCustomerRights(cr);
	}

	/**
	 * 记录操作记录
	 * 
	 * @param content
	 *            操作内容
	 * @param operateUserId
	 *            操作人ID
	 */
	public int operateRecord(String content, int operateUserId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("content", content);
		map.put("operateUserId", operateUserId);
		return sysConfigMapper.operateRecord(map);
	}

	public int resetPassword(Map<String, Object> map) {
		return customerMapper.resetPassword(map);
	}

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

	public int editCustomerInfo(EmpReq req) {
		return customerMapper.editCustomerInfo(req);
	}

	public int editCustomerRights(int customer_id, int right_id) {
		return customerMapper.editCustomerRights(customer_id, right_id);
	}

	public List<Map<String, Object>> getCustomerRights(EmpReq req) {
		return customerMapper.getCustomerRights(req);
	}

	public int updateRights(int customer_id, int role_id) {
		return customerMapper.updateRights(customer_id, role_id);
	}

	public int countCustomerRightsByRoleId(int customer_id, int role_id) {
		return customerMapper.countCustomerRightsByRoleId(customer_id, role_id);
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

	public void insertRights(EmpReq req) {
		customerMapper.batchInsertRights(req);
	}

}
