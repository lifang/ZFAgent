package com.comdosoft.financial.user.mapper.zhangfu;

import java.util.List;
import java.util.Map;

import com.comdosoft.financial.user.domain.query.CustomerManageReq;

/**
 * 用于app员工管理
 * @author yangyibin
 *
 */
public interface CustomerManageMapper {
	
	int getTotal(CustomerManageReq req);
    
	List<Map<String, Object>> getCustomerList(CustomerManageReq req);
	
	int getCusAgentInfo(CustomerManageReq req);
	
	int creCusAgentRelation(CustomerManageReq req);

	int getCusRoleInfo(CustomerManageReq req);
	
	int creCusRoleRelation(CustomerManageReq req);
	
	int delCusAgentRel(CustomerManageReq req);
	
	int delCusRoleRel(CustomerManageReq req);
	
	int delCustomer(CustomerManageReq req);
	
	int changePwd(CustomerManageReq req);
	
	int getCustomerIdByLoginId(CustomerManageReq req);
	
	List<Map<String, Object>> getInfo(CustomerManageReq req);
}
