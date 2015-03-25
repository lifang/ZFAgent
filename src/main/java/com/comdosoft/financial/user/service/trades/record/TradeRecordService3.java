package com.comdosoft.financial.user.service.trades.record;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.binding.BindingException;
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
    private AgentTradeMapper agentTrade;

    public Map<String, Object> getTradeRecordsCount(TradeReq req) {
        req.setCode(agentTrade.getCode(req.getAgentId()));
        req.setAgentIds(SysUtils.Arry2Str(agentTrade.getAgentIds(req)));
        return tradeRecordMapper3.getTradeRecordsCount(req);
    }

    public List<Map<String, Object>> getTradeRecords(TradeReq req) {
        req.setCode(agentTrade.getCode(req.getAgentId()));
        req.setAgentIds(SysUtils.Arry2Str(agentTrade.getAgentIds(req)));
        List<Map<String, Object>> result = tradeRecordMapper3.getTradeRecords(req);
        for (Map<String, Object> map : result) {
            int agentid = SysUtils.Object2int(map.get("agent_id"));
            map.put("agent", agentTrade.getAgentName(agentid));
            if (1 == req.getIs_have_profit()) {
                //req.setAgentId(agentid);
                req.setId(SysUtils.Object2int(map.get("id")));
                req.setPcid(SysUtils.Object2int(map.get("pay_channel_id")));
                map.putAll(profit(req));
            }
        }
        return result;
    }

    private Map<String, Object> profit(TradeReq req) {
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> map = tradeRecordMapper3.getProfit(req.getId());
        if (map == null) {
            result.put("get", 0);
            result.put("pay", 0);
            return result;
        }
        int tid = SysUtils.Object2int(map.get("tid"));
        int cid = SysUtils.Object2int(map.get("cid"));
        int tpay = SysUtils.Object2int(map.get("profits_pay"));
        if (tid == req.getAgentId()) {
            result.put("get", tpay);
            result.put("pay", 0);
        } else {
            List<Integer> percent = new ArrayList<Integer>();
            int j1 = req.getCode().length() / 3;
            for (int i = 1; i < j1; i++) {
                String code2 = req.getCode().substring(0, req.getCode().length() - 3 * i);
                req.setCode(code2);
                percent.add(percent(req));
            }
            for (Integer integer : percent) {
                tpay = tpay * integer / 1000;
            }
            result.put("get", tpay);
            if (cid == req.getAgentId()) {
                result.put("pay", 0);
            } else {
                req.setCode(agentTrade.getCode(cid).substring(0, agentTrade.getCode(req.getAgentId()).length() + 3));
                result.put("pay", tpay * percent(req) / 1000);
            }
        }
        return result;
    }

    private int percent(TradeReq req) {
        try {
            int p1 = agentTrade.getp1(req);
            return p1;
        } catch (BindingException e) {
            try {
                int p2 = agentTrade.getp2(req.getCode());
                return p2;
            } catch (BindingException e1) {
                try {
                    int p3 = agentTrade.getp3();
                    return p3;
                } catch (BindingException e2) {
                    return 0;
                }
            }
        }
    }

    public List<Map<String, Object>> getTradeType() {
        return agentTrade.getTradeType();
    }

    public Map<String, Object> getTradeRecord(TradeReq req) {
        Map<String, Object> result = tradeRecordMapper3.getTradeRecord(req);
        int type = SysUtils.Object2int(result.get("types").toString());
        int pcid = SysUtils.Object2int(result.get("pay_channel_id").toString());
        result.put("paychannel", agentTrade.getpcname(pcid));
        Map<String, Object> result2 = null;
        switch (type) {
        case 2:
            result2 = tradeRecordMapper3.get23(req);
            break;
        case 3:
            result2 = tradeRecordMapper3.get23(req);
            break;
        case 4:
            result2 = tradeRecordMapper3.get4(req);
            break;
        case 5:
            result2 = tradeRecordMapper3.get5(req);
            break;
        default:
            break;
        }
        if (result2 != null) {
            result.putAll(result2);
        }
        return result;
    }

    public List<Map<String, Object>> getTradeStatistics(TradeReq req) {
        req.setCode(agentTrade.getCode(req.getAgentId()));
        req.setAgentIds(SysUtils.Arry2Str(agentTrade.getAgentIds(req)));
        List<Map<String, Object>> result = tradeRecordMapper3.getTradeStatistics(req);
        for (Map<String, Object> map : result) {
            int agentid = SysUtils.Object2int(map.get("agent_id"));
            map.put("agent", agentTrade.getAgentName(agentid));
            if (1 == req.getIs_have_profit()) {
               // req.setAgentId(agentid);
                req.setId(agentid);
                map.putAll(profit2(req));
            }
        }
        return result;
    }

    private Map<String, Object> profit2(TradeReq req) {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Map<String, Object>> maplist = tradeRecordMapper3.getProfitTotal(req);
        if (maplist == null || maplist.size() == 0) {
            result.put("get", 0);
            result.put("pay", 0);
            return result;
        } else {
            int totalget = 0;
            int totalpay = 0;
            for (Map<String, Object> map : maplist) {
                int tid = SysUtils.Object2int(map.get("tid"));
                int cid = SysUtils.Object2int(map.get("cid"));
                int tpay = SysUtils.Object2int(map.get("profits_pay"));
                int pcid = SysUtils.Object2int(map.get("pcid"));
                req.setPcid(pcid);
                if (tid == req.getAgentId()) {
                    totalget += tpay;
                    // result.put("get", tpay);
                    // result.put("pay", 0);
                } else {
                    List<Integer> percent = new ArrayList<Integer>();
                    int j1 = req.getCode().length() / 3;
                    for (int i = 1; i < j1; i++) {
                        String code2 = req.getCode().substring(0, req.getCode().length() - 3 * i);
                        req.setCode(code2);
                        percent.add(percent(req));
                    }
                    for (Integer integer : percent) {
                        tpay = tpay * integer / 1000;
                    }
                    // result.put("get", tpay);
                    totalget += tpay;
                    if (cid != req.getAgentId()) {
                        req.setCode(agentTrade.getCode(cid).substring(0, agentTrade.getCode(req.getAgentId()).length() + 3));
                        // result.put("pay", tpay * percent(req) / 1000);
                        totalpay += tpay * percent(req) / 1000;
                    }
                }
            }
            result.put("get", totalget);
            result.put("pay", totalpay);
            return result;
        }
    }

}