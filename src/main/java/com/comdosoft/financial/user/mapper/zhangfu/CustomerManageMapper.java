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
}
