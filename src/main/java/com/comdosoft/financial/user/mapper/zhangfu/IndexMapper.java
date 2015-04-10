package com.comdosoft.financial.user.mapper.zhangfu;

import java.util.List;
import java.util.Map;

import com.comdosoft.financial.user.domain.zhangfu.MyOrderReq;

public interface IndexMapper {

    List<Map<String, Object>> getFactoryList();

    List<Map<String, Object>> getPosList();

    List<Map<String, Object>> getParentCitiesList();
    
    List<Map<String, Object>> getAllCitiesList();

    List<Map<String, Object>> getChildrenCitiesList(String id);

    void changePhone(MyOrderReq req);

	List<Map<String, Object>> wxlist(MyOrderReq myOrderReq);
	
	List<Map<String, Object>> getRoleByAgentId(int agentId);

	Map<String, Object> findCityById(String id);

	List<Map<String, Object>> getCustomerMarks(String id);

	void saveViewCustomerViews(MyOrderReq req);

}
