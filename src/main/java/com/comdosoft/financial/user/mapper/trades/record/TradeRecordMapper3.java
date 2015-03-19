package com.comdosoft.financial.user.mapper.trades.record;

import java.util.List;
import java.util.Map;

import com.comdosoft.financial.user.domain.query.TradeReq;


public interface TradeRecordMapper3 {

    Map<String, Object> getTradeRecordsCount(TradeReq req);
    
    List<Map<Object, Object>> getTradeRecords(TradeReq req);

    Map<String, Object> getTradeRecord(TradeReq req);

    Map<String, Object> get23(TradeReq req);

    Map<String, Object> get4(TradeReq req);

    Map<String, Object> get5(TradeReq req);


}