package com.comdosoft.financial.user.service.trades.record;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.comdosoft.financial.user.domain.query.TradeReq;
import com.comdosoft.financial.user.mapper.trades.record.TradeRecordMapper3;
import com.comdosoft.financial.user.mapper.zhangfu.AgentTradeMapper;
import com.comdosoft.financial.user.utils.SysUtils;

@Service
public class TradeRecordService3 {

    @Resource
    private TradeRecordMapper3 tradeRecordMapper3;

    @Resource
    private  AgentTradeMapper agentTrade;
    
    

    public Map<String, Object> getTradeRecordsCount(TradeReq req) {
        req.setCode(agentTrade.getCode(req));
        req.setAgentIds(SysUtils.Arry2Str(agentTrade.getAgentIds(req)));
        return tradeRecordMapper3.getTradeRecordsCount(req);
    }

    public List<Map<String, Object>> getTradeRecords(TradeReq req) {
        req.setCode(agentTrade.getCode(req));
        req.setAgentIds(SysUtils.Arry2Str(agentTrade.getAgentIds(req)));
        List<Map<String, Object>> result=tradeRecordMapper3.getTradeRecords(req);
        for (Map<String, Object> map : result) {
            int agentid=SysUtils.Object2int(map.get("agent_id"));
            map.put("agent", agentTrade.getAgentName(agentid));
            if(1==req.getIs_have_profit()){
                req.setId(SysUtils.Object2int(map.get("id")));
                map.putAll(profit(req));
            }
        }
        return result;
    }
    
    private Map<String, Object> profit(TradeReq req){
        Map<String, Object> map=tradeRecordMapper3.getProfit(req.getId());
        int tid=SysUtils.Object2int(map.get("tid"));
        int cid=SysUtils.Object2int(map.get("cid"));
        Map<String, Object> result=new HashMap<String, Object>();
        result.put("profits_get", 1);
        result.put("profits_pay", 1);
        if(tid==req.getAgentId()){
            result.put("profits_get", map.get("profits_get"));
        }else{
            
        }
        if(cid==req.getAgentId()){
            result.put("profits_pay", 0);
        }else{
            
        }
        return null;
    }

    public List<Map<String, Object>> getTradeType() {
        return agentTrade.getTradeType();
    }

    public Map<String, Object> getTradeRecord(TradeReq req) {
        Map<String, Object> result = tradeRecordMapper3.getTradeRecord(req);
        int type = SysUtils.Object2int(result.get("types").toString());
        int pcid = SysUtils.Object2int(result.get("pay_channel_id").toString());
        result.put("paychannel", agentTrade.getpcname(pcid));
        Map<String, Object> result2=null;
        switch (type) {
        case 2:
            result2=tradeRecordMapper3.get23(req);
            break;
        case 3:
            result2=tradeRecordMapper3.get23(req);
            break;
        case 4:
            result2=tradeRecordMapper3.get4(req);
            break;
        case 5:
            result2=tradeRecordMapper3.get5(req);
            break;
        default:
            break;
        }
        if(result2!=null){
            result.putAll(result2);
        }
        return result;
    }

}