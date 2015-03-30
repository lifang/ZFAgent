package com.comdosoft.financial.user.mapper.zhangfu;

import java.util.List;
import java.util.Map;

import com.comdosoft.financial.user.domain.query.EmpReq;
import com.comdosoft.financial.user.domain.query.MyAccountReq;
import com.comdosoft.financial.user.domain.zhangfu.Customer;
import com.comdosoft.financial.user.domain.zhangfu.CustomerAgentRelation;
import com.comdosoft.financial.user.domain.zhangfu.CustomerRoleRelation;

public interface CustomerMapper {

	/**
	 * 根据代理商id查询该代理商所有的用户
	 * 
	 * @param req
	 * @return
	 */
	public List<CustomerAgentRelation> getAllAccountlist(MyAccountReq req);

	/**
	 * 根据代理商id计算该代理商下所有的用户的个数
	 * 
	 * @param req
	 * @return
	 */
	public int countCustomes(Integer agentId);

	/**
	 * 检查用户名是否重复
	 * 
	 * @param req
	 * @return
	 */
	public Map<String, Object> checkAccount(EmpReq req);

	/**
	 * 创建用户
	 * 
	 * @param empReq
	 */
	public void addCustomer(EmpReq empReq);

	/**
	 * 添加用户权限
	 * 
	 * @param empReq
	 */
	public void addCustomerRights(EmpReq empReq);

	/**
	 * 根据ID查询代理商所有权限
	 * 
	 * @param req
	 * @return
	 */
	public List<Object> getWholeRightsByAgentId(MyAccountReq req);

	/**
	 * 根据ID删除用户信息
	 * 
	 * @param req
	 * @return
	 */
	public int deleteEmpInfoFromAgent(int id);

	public int updateStatus(int id);

	/**
	 * 向customer_agent_relations插入信息
	 * 
	 * @param req
	 */
	public void insertCustomerAgentRelations(CustomerAgentRelation req);

	/**
	 * 按照用戶ID查詢用戶信息
	 * 
	 * @param myAccountReq
	 * @return
	 */
	public Map<String, Object> getEmpInfoById(int id);

	/**
	 * 根据员工登陆名查询员工信息
	 * 
	 * @param myAccountReq
	 * @return
	 */
	public Map<String, Object> getEmpInfoByUsername(String userName);

	public List<Map<Object, Object>> getList(Map<Object, Object> query);

	public void insertCustomer(Customer customer);

	public void insertCustomerRights(CustomerRoleRelation cr);

	public int resetPassword(int customer_id, String password);

	public List<Map<String, Object>> getDetailInfoById(int id);

	public int editCustomerInfo(EmpReq req);

	public int editCustomerRights(int customer_id, int right_id);

	public List<Map<String, Object>> getCustomerRights(int customer_id);

	public int updateRights(int customer_id, int role_id);

	public int countCustomerRightsByRoleId(int customer_id, int role_id);
}
