package com.comdosoft.financial.user.controller.api;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.comdosoft.financial.user.domain.Response;
import com.comdosoft.financial.user.domain.query.TradeReq;
import com.comdosoft.financial.user.service.trades.record.TradeRecordService3;


@RestController
@RequestMapping(value = "api/trade")
public class TradeRecordAPI2 {

    
    
    @Resource
    private TradeRecordService3 tradeRecordService3;

    /**
     * 日志记录器
     */
    private static final Logger logger = Logger.getLogger(TradeRecordAPI2.class);

    @RequestMapping(value = "getTradeRecords", method = RequestMethod.POST)
    public Response getTradeRecords(@RequestBody TradeReq req) {
        Response sysResponse = null;
        try {
            Map<String, Object> result = tradeRecordService3.getTradeRecordsCount(req);
            result.put("list", tradeRecordService3.getTradeRecords(req));
            sysResponse = Response.getSuccess(result);
        }catch (NullPointerException e) {
            logger.error("查询交易流水信息失败", e);
            sysResponse = Response.getError("查询交易流水失败:数据异常");
        } catch (Exception e) {
            logger.error("查询交易流水信息失败", e);
            sysResponse = Response.getError("查询交易流水失败:系统异常");
        }
        return sysResponse;
    }
    
    @RequestMapping(value = "getTradeRecord", method = RequestMethod.POST)
    public Response getTradeRecord(@RequestBody TradeReq req) {
        Response sysResponse = null;
        try {
            Map<String, Object> result = tradeRecordService3.getTradeRecord(req);
            if(null==result){
                sysResponse = Response.getError("查询交易流水失败:流水不存在");
            }else{
                sysResponse = Response.getSuccess(result);
            }
        } catch (Exception e) {
            logger.error("查询交易流水信息失败", e);
            sysResponse = Response.getError("查询交易流水失败:系统异常");
        }
        return sysResponse;
    }
    
    @RequestMapping(value = "getTradeType", method = RequestMethod.POST)
    public Response getTradeType() {
        Response sysResponse = null;
        try {
            List<Map<String,Object>> result=tradeRecordService3.getTradeType();
            sysResponse = Response.getSuccess(result);
        } catch (Exception e) {
            logger.error("查询交易流水信息失败", e);
            sysResponse = Response.getError("查询交易流水失败:系统异常");
        }
        return sysResponse;
    }
    

    @RequestMapping(value = "getTradeStatistics", method = RequestMethod.POST)
    public Response getTradeStatistics(@RequestBody TradeReq req) {
        Response sysResponse = null;
        try {
            List<Map<String,Object>> result=tradeRecordService3.getTradeStatistics(req);
            sysResponse = Response.getSuccess(result);
        } catch (Exception e) {
            logger.error("查询交易流水统计失败", e);
            sysResponse = Response.getError("查询交易流水统计失败:系统异常");
        }
        return sysResponse;
    }

}