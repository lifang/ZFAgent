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
	 * 添加用户权限
	 * 
	 * @param empReq
	 */
	public void addCustomerRights(EmpReq empReq);

	/**
	 * 根据ID删除用户信息
	 * 
	 * @param req
	 * @return
	 */
	public int deleteEmpInfoFromAgent(int id);

	/**
	 * 更新用户状态为不可用
	 * @param id
	 * @return
	 */
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

	public void insertCustomer(Customer customer);

	public void insertCustomerRights(CustomerRoleRelation cr);

	/**
	 * 重置用户密码
	 * 
	 * @param map
	 * @return
	 */
	public int resetPassword(Map<String, Object> map);

	public List<Map<String, Object>> getDetailInfoById(int id);

	/**
	 * 编辑用户姓名以及密码信息
	 * 
	 * @param req
	 * @return
	 */
	public int editCustomerInfo(EmpReq req);

	public int editCustomerRights(int customer_id, int right_id);

	public List<Map<String, Object>> getCustomerRights(EmpReq req);

	/**
	 * 根据agent_id查询代理商下所有的状态为2的用户
	 * 
	 * @param req
	 * @return
	 */
	public int count(MyAccountReq req);

	/**
	 * 根据代理商ID查询代理商旗下的用户
	 * 
	 * @param req
	 * @return
	 */
	public List<Map<String, Object>> getAccountList(MyAccountReq req);

	/**
	 * 删除用户所有权限
	 * 
	 * @param req
	 * @return
	 */
	public int deleteCustomerRights(EmpReq req);

	/**
	 * 插入用户权限
	 * @param req
	 */
	public void batchInsertRights(EmpReq req);

	public int updateDevice(Integer id, String deviceCode);

}
