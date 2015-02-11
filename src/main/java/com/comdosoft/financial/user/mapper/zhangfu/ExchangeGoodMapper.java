package com.comdosoft.financial.user.mapper.zhangfu;

import java.util.List;
import java.util.Map;

import com.comdosoft.financial.user.domain.query.ExchangeGoodReq;





public interface ExchangeGoodMapper {

    int getExchangeGoodTotal(ExchangeGoodReq req);

    List<Map<String, Object>> getExchangeGoodList(ExchangeGoodReq req);

    void add(ExchangeGoodReq req);

    Map<String, Object> getInfo(ExchangeGoodReq req);

    void upTerminal_AgentId(ExchangeGoodReq req);
    

}