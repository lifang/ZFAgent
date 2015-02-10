package com.comdosoft.financial.user.mapper.zhangfu;

import java.util.List;
import java.util.Map;

import com.comdosoft.financial.user.domain.query.StockReq;



public interface StockMapper {

    int getStockTotal(StockReq req);

    List<Map<String, Object>> getStockList(StockReq req);

    void rename(StockReq req);

    List<Map<String, Object>> getInfo(StockReq req);

}