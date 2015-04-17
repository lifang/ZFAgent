package com.comdosoft.financial.user.mapper.zhangfu;

import java.util.List;
import java.util.Map;

import com.comdosoft.financial.user.domain.query.PosProfitReq;

public interface PosProfitMapper {

	int getPosProfitCount(PosProfitReq req);

	List<Map<String, Object>> getPosProfitList(PosProfitReq req);

	/**
	 * 根据订单ID查询订单明细
	 * 
	 * @param req
	 * @return
	 */
	public List<Map<String, Object>> getOrderListByOrderIds(PosProfitReq req);

}
