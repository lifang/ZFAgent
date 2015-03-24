package com.comdosoft.financial.user.mapper.zhangfu;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.comdosoft.financial.user.domain.query.TradeReq;




public interface AgentTradeMapper {

    String getpcname(int pcid);

    List<Map<String, Object>> getTradeType();

    List<Integer> getAgentIds(TradeReq req);

    String getCode(int agentId);

    String getAgentName(int agentId);

    int getp1(TradeReq req);

    int getp2(@Param("code") String code);

    int getp3();

    List<Map<Object, Object>> getTerminals(@Param("code") String code);

    List<Map<Object, Object>> getAgents(@Param("code") String code);



}