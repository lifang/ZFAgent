package com.comdosoft.financial.user.mapper.zhangfu;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.comdosoft.financial.user.domain.query.StockReq;



public interface StockMapper {

    int getStockTotal(StockReq req);

    List<Map<String, Object>> getStockList(StockReq req);

    void rename(StockReq req);


    int getHoitoryCount(StockReq req);

    int getOpenCount(StockReq req);

    int getAgentCount(StockReq req);

    int getTotalCount(StockReq req);

    String getAgentCode(int agentId);

    List<Map<String, Object>> getSonAgent(StockReq req);

    String getLastPrepareTime(StockReq req);

    String getLastOpenTime(StockReq req);

    int getTerminalTotal(StockReq req);

    List<Map<String, Object>> getTerminalList(StockReq req);

    int getSonAgentCount(StockReq req);

    List<Integer> getAgents(@Param("code") String code);

    Map<String, Object> getStock(StockReq req);

    int renameCount(StockReq req);

    void renameAdd(StockReq req);

    int getAgentId(@Param("code") String code);

}