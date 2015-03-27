package com.comdosoft.financial.user.mapper.trades.record;

import java.util.List;
import java.util.Map;

import com.comdosoft.financial.user.domain.query.TradeReq;
import com.comdosoft.financial.user.domain.zhangfu.MyOrderReq;

/**
 * Dao层接口
 * 
 * @author zengguang
 * 
 */
public interface TradeRecordMapper {


    Map<Object, Object> getTradeRecordTotal(Map<Object, Object> query);

    int getTradeRecordTotalByAgentId(int id);

    List<Map<Object, Object>> getTradeRecords12(TradeReq req);

    List<Map<Object, Object>> getTradeRecords3(TradeReq req);

    List<Map<Object, Object>> getTradeRecords45(TradeReq req);

    Integer getTradeRecordsCount(TradeReq req);

	List<Map<String, Object>> getSevenDynamic(MyOrderReq myOrderReq);
    
    

}