package com.comdosoft.financial.user.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comdosoft.financial.user.domain.query.StockReq;
import com.comdosoft.financial.user.mapper.zhangfu.GoodMapper;
import com.comdosoft.financial.user.mapper.zhangfu.StockMapper;
import com.comdosoft.financial.user.utils.SysUtils;

@Service
public class StockService {

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private GoodMapper goodMapper;

    public Map<String, Object> getList(StockReq req) {
        String code = stockMapper.getAgentCode(req.getAgentId());
        if (null == code || code.length() < 3) {
            return null;
        }
        req.setTopAgentId(stockMapper.getAgentId(code.substring(0,3)));
        req.setCode(code);
        Map<String, Object> map = new HashMap<String, Object>();
        int total = stockMapper.getStockTotal(req);
        map.put("total", total);
        List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
        if (total > 0) {
            list = stockMapper.getStockList(req);
            for (Map<String, Object> map2 : list) {
                List<String> goodPics = goodMapper.getgoodPics(SysUtils.Object2int(map2.get("good_id")));
                if (null != goodPics && goodPics.size() > 0) {
                    map2.put("picurl", goodPics.get(0));
                }
                req.setGoodId(SysUtils.Object2int(map2.get("good_id")));
                req.setPaychannelId(SysUtils.Object2int(map2.get("paychannel_id")));
                map2.put("hoitoryCount", stockMapper.getHoitoryCount(req));
                map2.put("openCount", stockMapper.getOpenCount(req));
                map2.put("agentCount", stockMapper.getAgentCount(req));
                map2.put("totalCount", stockMapper.getTotalCount(req));
            }
        }
        map.put("list", list);
        return map;
    }

 
    
    public Map<String, Object> getInfo(StockReq req) {
        Map<String, Object> resultmap = new HashMap<String, Object>();
        if (null == req.getAgentname()) {
            req.setAgentname("");
        }
        int total = stockMapper.getSonAgentCount(req);
        resultmap.put("total", total);
        List<Map<String, Object>> list = stockMapper.getSonAgent(req);
        if (null != list && list.size() > 0) {
            for (Map<String, Object> map : list) {
                req.setAgentId(SysUtils.Object2int(map.get("agent_id")));
                req.setCode(stockMapper.getAgentCode(req.getAgentId()));
                // map.put("hoitoryCount", stockMapper.getHoitoryCount(req));
                map.put("openCount", stockMapper.getOpenCount(req));
                map.put("lastPrepareTime", stockMapper.getLastPrepareTime(req));
                map.put("lastOpenTime", stockMapper.getLastOpenTime(req));
            }
        }
        resultmap.put("list", list);
        return resultmap;
    }

    public int rename(StockReq req) {
        try {
            if(stockMapper.renameCount(req)==0){
                stockMapper.renameAdd(req);
            }else{
                stockMapper.rename(req);
            }
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }

    public Map<String, Object> getTerminalList(StockReq req) {
        Map<String, Object> map = new HashMap<String, Object>();
        int total = stockMapper.getTerminalTotal(req);
        map.put("total", total);
        List<Map<String, Object>> list = stockMapper.getTerminalList(req);
        map.put("list", list);
        return map;
    }

    public Map<String, Object> getInfo_web(StockReq req) {
        String code = stockMapper.getAgentCode(req.getAgentId());
        if (null == code || code.length() < 3) {
            return null;
        }
        req.setCode(code);
        Map<String, Object> map =  stockMapper.getStock(req);
        map.put("hoitoryCount", stockMapper.getHoitoryCount(req));
        map.put("openCount", stockMapper.getOpenCount(req));
        map.put("agentCount", stockMapper.getAgentCount(req));
        map.put("totalCount", stockMapper.getTotalCount(req));
        return map;
    }

}
