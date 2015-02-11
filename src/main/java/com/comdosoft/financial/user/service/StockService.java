package com.comdosoft.financial.user.service;




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
        Map<String, Object> map=new HashMap<String, Object>();
        int total=stockMapper.getStockTotal(req);
        map.put("total", total);
        List<Map<String, Object>> list=stockMapper.getStockList(req);
        if(total>0){
            for (Map<String, Object> map2 : list) {
                List<String> goodPics = goodMapper.getgoodPics(SysUtils.Object2int(map2.get("good_id")));
                if (null != goodPics && goodPics.size() > 0) {
                    map2.put("picurl", goodPics.get(0));
                }
                req.setGood_id(SysUtils.Object2int(map2.get("good_id")));
                req.setPaychannel_id(SysUtils.Object2int(map2.get("paychannel_id")));
                map2.put("hoitoryCount", stockMapper.getHoitoryCount(req));
                map2.put("openCount", stockMapper.getOpenCount(req));
                map2.put("agentCount", stockMapper.getAgentCount(req));
                map2.put("totalCount", stockMapper.getTotalCount(req));
            }
        }
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
