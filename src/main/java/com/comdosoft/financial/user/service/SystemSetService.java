package com.comdosoft.financial.user.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comdosoft.financial.user.domain.query.EmpReq;
import com.comdosoft.financial.user.domain.query.MyAccountReq;
import com.comdosoft.financial.user.domain.zhangfu.CustomerAgentRelation;
import com.comdosoft.financial.user.mapper.zhangfu.CustomerMapper;
import com.comdosoft.financial.user.utils.Exception.AccountRepetitionException;
import com.comdosoft.financial.user.utils.page.Page;
import com.comdosoft.financial.user.utils.page.PageRequest;

@Service
public class SystemSetService {
	@Autowired
	private CustomerMapper customerMapper;

	/**
	 * 获取代理商下所有的用户
	 * 
	 * @param req
	 * @return
	 */
	public Page<Object> getAllAccountlist(MyAccountReq req) {
		PageRequest request = new PageRequest(req.getPage(), req.getRows());
		int count = customerMapper.countCustomes(req.getAgentId());
		List<CustomerAgentRelation> list = customerMapper
				.getAllAccountlist(req);
		List<Object> result = new ArrayList<Object>();
		Map<String, Object> map = null;
		Map<String, Object> subMap = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (CustomerAgentRelation c : list) {
			map = new LinkedHashMap<String, Object>();
			map.put("agent_id", c.getAgentId().toString());
			map.put("customer_id", c.getCustomerId().toString());
			String d = sdf.format(c.getCreatedAt());
			map.put("order_createTime", d);

			subMap = customerMapper.getEmpInfoById(c.getCustomerId());
			if (subMap != null) {
				map.put("username", subMap.get("username").toString());
				map.put("name", subMap.get("name").toString());
				// map.put("password", subMap.get("password").toString());
				result.add(map);
			}

		}
		return new Page<Object>(request, result, count);
	}

	/**
	 * 代理商创建用户
	 * 
	 * @param empReq
	 */
	public void addCustomer(EmpReq empReq) throws AccountRepetitionException {
		customerMapper.addCustomer(empReq);
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

	public void insertCustomerAgentRelations(EmpReq empReq) {
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
	public Map<String, Object> deleteEmpInfoFromAgent(EmpReq req) {
		return customerMapper.deleteEmpInfoFromAgent(req);
	}
}
