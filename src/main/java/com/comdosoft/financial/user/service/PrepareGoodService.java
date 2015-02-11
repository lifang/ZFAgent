package com.comdosoft.financial.user.service;




import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comdosoft.financial.user.domain.query.PrepareGoodReq;
import com.comdosoft.financial.user.mapper.zhangfu.PrepareGoodMapper;
import com.comdosoft.financial.user.utils.Param;


@Service
public class PrepareGoodService {

    @Autowired
    private PrepareGoodMapper prepareGoodMapper;
    
    
    
    public Map<String, Object> getList(PrepareGoodReq req) {
        req.setStartTime(Param.setDay(req.getStartTime()));
        req.setEndTime(Param.setDay(req.getEndTime()));
        Map<String, Object> map=new HashMap<String, Object>();
        int total=prepareGoodMapper.getPrepareGoodTotal(req);
        map.put("total", total);
        List<Map<String, Object>> list=prepareGoodMapper.getPrepareGoodList(req);
        map.put("list", list);
        return map;
    }

    public Map<String, Object> getInfo(PrepareGoodReq req) {
        return prepareGoodMapper.getInfo(req);
    }

    public int add(PrepareGoodReq req) {
        try {
            if(null!=req.getSerial_nums()&&req.getSerial_nums().length>0){
                StringBuilder sb = new StringBuilder();
                for (String serial_num : req.getSerial_nums()) {
                    req.setSerial_num(serial_num);
                    prepareGoodMapper.upTerminal_AgentId(req);
                    sb.append(serial_num+",");
                }
                sb.deleteCharAt(sb.length()-1);
                req.setTerminal_list(sb.toString());
                req.setQuantity(req.getSerial_nums().length);
                prepareGoodMapper.add(req);
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
