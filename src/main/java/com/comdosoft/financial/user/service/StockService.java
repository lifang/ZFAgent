package com.comdosoft.financial.user.service;




import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comdosoft.financial.user.domain.query.StockReq;
import com.comdosoft.financial.user.mapper.zhangfu.StockMapper;


@Service
public class StockService {

    @Autowired
    private StockMapper stockMapper;

    public Map<String, Object> getList(StockReq req) {
        Map<String, Object> map=new HashMap<String, Object>();
        int total=stockMapper.getStockTotal(req);
        map.put("total", total);
        List<Map<String, Object>> list=stockMapper.getStockList(req);
        map.put("list", list);
        return map;
    }

    public List<Map<String, Object>> getInfo(StockReq req) {
        return stockMapper.getInfo(req);
    }

    public int rename(StockReq req) {
        try {
            stockMapper.rename(req);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        
    }
    

    

}
