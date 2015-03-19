package com.comdosoft.financial.user.service.trades.record;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.comdosoft.financial.user.domain.query.TradeReq;
import com.comdosoft.financial.user.mapper.trades.record.TradeRecordMapper3;
import com.comdosoft.financial.user.mapper.zhangfu.PaychannelMapper;
import com.comdosoft.financial.user.utils.SysUtils;

@Service
public class TradeRecordService3 {

    @Resource
    private TradeRecordMapper3 tradeRecordMapper3;

    @Resource
    private PaychannelMapper pcMapper;

    public Map<String, Object> getTradeRecordsCount(TradeReq req) {
        return tradeRecordMapper3.getTradeRecordsCount(req);
    }

    public List<Map<Object, Object>> getTradeRecords(TradeReq req) {
        return tradeRecordMapper3.getTradeRecords(req);
    }

    public List<Map<String, Object>> getTradeType() {
        return pcMapper.getTradeType();
    }

    public Map<String, Object> getTradeRecord(TradeReq req) {
        Map<String, Object> result = tradeRecordMapper3.getTradeRecord(req);
        int type = SysUtils.Object2int(result.get("types").toString());
        int pcid = SysUtils.Object2int(result.get("pay_channel_id").toString());
        result.put("paychannel", pcMapper.getpcname(pcid));
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