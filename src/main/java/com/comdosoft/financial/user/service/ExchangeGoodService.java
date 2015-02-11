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
            if(null!=req.getSerial_nums()&&req.getSerial_nums().length>0){
                StringBuilder sb = new StringBuilder();
                for (String serial_num : req.getSerial_nums()) {
                    req.setSerial_num(serial_num);
                    exchangeGoodMapper.upTerminal_AgentId(req);
                    sb.append(serial_num+",");
                }
                sb.deleteCharAt(sb.length()-1);
                req.setQuantity(req.getSerial_nums().length);
                req.setTerminal_list(sb.toString());
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

    
    

    

}
