package com.comdosoft.financial.user.mapper.zhangfu;

import java.util.List;
import java.util.Map;

import com.comdosoft.financial.user.domain.query.PrepareGoodReq;





public interface PrepareGoodMapper {

    int getPrepareGoodTotal(PrepareGoodReq req);

    List<Map<String, Object>> getPrepareGoodList(PrepareGoodReq req);

    void add(PrepareGoodReq req);

    Map<String, Object> getInfo(PrepareGoodReq req);


    void upTerminal_AgentId(PrepareGoodReq req);

   

    

}