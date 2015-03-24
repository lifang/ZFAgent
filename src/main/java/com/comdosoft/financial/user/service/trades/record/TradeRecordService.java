package com.comdosoft.financial.user.service.trades.record;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.comdosoft.financial.user.domain.query.TradeReq;
import com.comdosoft.financial.user.domain.trades.TradeRecord;
import com.comdosoft.financial.user.mapper.trades.record.TradeRecordMapper;
import com.comdosoft.financial.user.mapper.zhangfu.AgentTradeMapper;
import com.comdosoft.financial.user.utils.SysUtils;

/**
 * 业务层实现类<br>
 * <功能描述>
 *
 * @author zengguang
 *
 */
@Service
public class TradeRecordService {

    @Resource
    private TradeRecordMapper tradeRecordMapper;

    @Resource
    private  AgentTradeMapper agentTrade;

    public List<Map<Object, Object>> getTerminals(int agentId) {
        String code=agentTrade.getCode(agentId);
        return agentTrade.getTerminals(code);
    }
    
    public List<Map<Object, Object>> getAgents(int agentId) {
        String code=agentTrade.getCode(agentId);
        return agentTrade.getAgents(code);
    }

    public Integer getTradeRecordTotal(TradeReq req) {
        req.setCode(agentTrade.getCode(req.getAgentId()));
        req.setAgentIds(SysUtils.Arry2Str(agentTrade.getAgentIds(req)));
        return tradeRecordMapper.getTradeRecordsCount(req);
    }
    
    public List<Map<Object, Object>> getTradeRecords(TradeReq req) {
        req.setCode(agentTrade.getCode(req.getAgentId()));
        req.setAgentIds(SysUtils.Arry2Str(agentTrade.getAgentIds(req)));
        List<Map<Object, Object>> list = null;
        switch (req.getTradeTypeId()) {
        case TradeRecord.TRADETYPEID_1:
            list = tradeRecordMapper.getTradeRecords12(req);
            break;
        case TradeRecord.TRADETYPEID_2:
            list = tradeRecordMapper.getTradeRecords12(req);
            break;
        case TradeRecord.TRADETYPEID_3:
            list = tradeRecordMapper.getTradeRecords3(req);
            break;
        case TradeRecord.TRADETYPEID_4:
            list = tradeRecordMapper.getTradeRecords45(req);
            break;
        case TradeRecord.TRADETYPEID_5:
            list = tradeRecordMapper.getTradeRecords45(req);
            break;
        default:
            list = new ArrayList<Map<Object, Object>>();
            break;
        }
        return list;
    }

    public Map<Object, Object> getTradeRecordTotal(int tradeTypeId, String terminalNumber, String startTime, String endTime) {
        Map<Object, Object> query = new HashMap<Object, Object>();
        query.put("tradeTypeId", tradeTypeId);
        query.put("terminalNumber", terminalNumber);
        query.put("startTime", startTime);
        query.put("endTime", endTime);
        return tradeRecordMapper.getTradeRecordTotal(query);
    }

    public Object getTradeRecord(int tradeRecordId) {
        // TODO Auto-generated method stub
        return null;
    }

   

    

}