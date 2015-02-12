package com.comdosoft.financial.user.mapper.zhangfu;

import java.util.List;
import java.util.Map;

import com.comdosoft.financial.user.domain.query.StockReq;



public interface StockMapper {

    int getStockTotal(StockReq req);

    List<Map<String, Object>> getStockList(StockReq req);

    void rename(StockReq req);


    int getHoitoryCount(StockReq req);

    int getOpenCount(StockReq req);

    int getAgentCount(StockReq req);

    int getTotalCount(StockReq req);

    String getAgentCode(int agents_id);

    List<Map<String, Object>> getSonAgent(StockReq req);

    String getLastPrepareTime(StockReq req);

    String getLastOpenTime(StockReq req);

    int getTerminalTotal(StockReq req);

    List<Map<String, Object>> getTerminalList(StockReq req);

    int getSonAgentCount(StockReq req);

}