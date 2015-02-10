package com.comdosoft.financial.user.service.trades.record;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.comdosoft.financial.user.domain.Paging;
import com.comdosoft.financial.user.domain.trades.TradeRecord;
import com.comdosoft.financial.user.mapper.trades.record.TradeRecordMapper;
import com.comdosoft.financial.user.mapper.zhangfu.TerminalsMapper;

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
    private TerminalsMapper terminalsMapper;

    public List<Map<Object, Object>> getTerminals(int customerId) {
        return terminalsMapper.getTerminals(customerId);
    }

    public List<Map<Object, Object>> getTradeRecords(int tradeTypeId, String terminalNumber, String startTime, String endTime, int page, int rows) {
        Map<Object, Object> query = new HashMap<Object, Object>();
        query.put("tradeTypeId", tradeTypeId);
        query.put("terminalNumber", terminalNumber);
        query.put("startTime", startTime);
        query.put("endTime", endTime);

        Paging paging = new Paging(page, rows);
        query.put("offset", paging.getOffset());
        query.put("rows", paging.getRows());

        List<Map<Object, Object>> list = null;
        switch (tradeTypeId) {
        case TradeRecord.TRADETYPEID_1:
            list = tradeRecordMapper.getTradeRecords12(query);
            break;
        case TradeRecord.TRADETYPEID_2:
            list = tradeRecordMapper.getTradeRecords12(query);
            break;
        case TradeRecord.TRADETYPEID_3:
            list = tradeRecordMapper.getTradeRecords3(query);
            break;
        case TradeRecord.TRADETYPEID_4:
            list = tradeRecordMapper.getTradeRecords45(query);
            break;
        case TradeRecord.TRADETYPEID_5:
            list = tradeRecordMapper.getTradeRecords45(query);
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

}