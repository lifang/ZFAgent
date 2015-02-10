package com.comdosoft.financial.user.mapper.trades.record;

import java.util.List;
import java.util.Map;

/**
 * Dao层接口
 * 
 * @author zengguang
 * 
 */
public interface TradeRecordMapper {

    List<Map<Object, Object>> getTerminals(int customerId);

    List<Map<Object, Object>> getTradeRecords12(Map<Object, Object> query);

    List<Map<Object, Object>> getTradeRecords3(Map<Object, Object> query);

    List<Map<Object, Object>> getTradeRecords45(Map<Object, Object> query);

    Map<Object, Object> getTradeRecordTotal(Map<Object, Object> query);

}