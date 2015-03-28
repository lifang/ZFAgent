package com.comdosoft.financial.user.service;




import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comdosoft.financial.user.domain.query.ExchangeGoodReq;
import com.comdosoft.financial.user.mapper.zhangfu.ExchangeGoodMapper;
import com.comdosoft.financial.user.utils.Param;


@Service
public class ExchangeGoodService {

    @Autowired
    private ExchangeGoodMapper exchangeGoodMapper;
    
    
    
    public Map<String, Object> getList(ExchangeGoodReq req) {
        req.setStartTime(Param.setDay(req.getStartTime()));
        req.setEndTime(Param.setDay(req.getEndTime()));
        Map<String, Object> map=new HashMap<String, Object>();
        int total=exchangeGoodMapper.getExchangeGoodTotal(req);
        map.put("total", total);
        List<Map<String, Object>> list=exchangeGoodMapper.getExchangeGoodList(req);
        map.put("list", list);
        return map;
    }

    public Map<String, Object> getInfo(ExchangeGoodReq req) {
        return exchangeGoodMapper.getInfo(req);
    }

    public int add(ExchangeGoodReq req) {
        try {
            if(null!=req.getSerialNums()&&req.getSerialNums().length>0){
                StringBuilder sb = new StringBuilder();
                for (String serialNum : req.getSerialNums()) {
                    req.setSerialNum(serialNum);
                    exchangeGoodMapper.upTerminal_AgentId(req);
                    sb.append(serialNum+",");
                }
                sb.deleteCharAt(sb.length()-1);
                req.setQuantity(req.getSerialNums().length);
                req.setTerminalList(sb.toString());
                exchangeGoodMapper.add(req);
                return 1;
            }else{
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public Map<String, Object> getTerminalsList(ExchangeGoodReq req) {
        Map<String, Object> map=new HashMap<String, Object>();
        int total=exchangeGoodMapper.getTerminalTotal(req);
        map.put("total", total);
        List<Map<String, Object>> list=exchangeGoodMapper.getTerminalList(req);
        map.put("list", list);
        return map;
    }

    
    

    

}
