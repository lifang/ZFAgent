package com.comdosoft.financial.user.mapper.zhangfu;

import java.util.List;
import java.util.Map;

import com.comdosoft.financial.user.domain.zhangfu.MyOrderReq;

public interface CsAgentsMapper {

    List<Map<String, Object>> findAll(MyOrderReq myOrderReq);

    int changeStatus(MyOrderReq myOrderReq);

    Map<String, Object> findById(MyOrderReq myOrderReq);

    List<Map<String, Object>> findTraceById(MyOrderReq myOrderReq);

    int count(MyOrderReq myOrderReq);

	int countSearch(MyOrderReq myOrderReq);

	List<Map<String, Object>> search(MyOrderReq myOrderReq);
	
    void addMark(MyOrderReq myOrderReq);

}
