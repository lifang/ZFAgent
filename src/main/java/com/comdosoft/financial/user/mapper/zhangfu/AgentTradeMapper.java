package com.comdosoft.financial.user.mapper.zhangfu;

import java.util.List;
import java.util.Map;

import com.comdosoft.financial.user.domain.query.TradeReq;




public interface AgentTradeMapper {

    String getpcname(int pcid);

    List<Map<String, Object>> getTradeType();

    List<Integer> getAgentIds(TradeReq req);

    String getCode(int agentId);

    String getAgentName(int agentId);

    int getp1(TradeReq req);

    int getp2(String code);

    int getp3();



}