package com.comdosoft.financial.user.mapper.trades.record;

import java.util.List;
import java.util.Map;

import com.comdosoft.financial.user.domain.query.PosProfitReq;

public interface GoodsProfitMapper {

	/**
	 * 根据代理商ID查询trades库中good_profits表
	 * 
	 * @param req
	 * @return
	 */
	public List<Map<String, Object>> getGoodsProfitListByAgentId(PosProfitReq req);
	
	public int getGoodsProfitCountByAgentId(PosProfitReq req);

}
