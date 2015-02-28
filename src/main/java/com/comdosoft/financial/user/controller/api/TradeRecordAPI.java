package com.comdosoft.financial.user.controller.api;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.comdosoft.financial.user.domain.Response;
import com.comdosoft.financial.user.domain.query.TradeRecordQuery;
import com.comdosoft.financial.user.service.trades.record.TradeRecordService;

/**
 * 交易流水<br>
 * <功能描述>
 *
 * @author zengguang
 * 
 */
@RestController
@RequestMapping(value = "api/trade/record")
public class TradeRecordAPI {

    @Resource
    private TradeRecordService tradeRecordService;

    /**
     * 日志记录器
     */
    private static final Logger logger = Logger.getLogger(TradeRecordAPI.class);

    /**
     * [01]获取终端列表
     * 
     * @param customerId
     * @return
     */
    @RequestMapping(value = "getTerminals", method = RequestMethod.POST)
    public Response getAPPLatestVersion(@RequestBody TradeRecordQuery query) {
        Response sysResponse = null;
        try {
            int customerId = query.getCustomerId();
            sysResponse = Response.getSuccess(tradeRecordService.getTerminals(customerId));
        } catch (Exception e) {
            logger.error("获取终端列表信息失败", e);
            sysResponse = Response.getError("获取终端列表信息失败:系统异常");
        }
        return sysResponse;
    }

    /**
     * [02]查询交易流水
     * 
     * @param tradeTypeId
     * @param terminalNumber
     * @param startTime
     * @param endTime
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(value = "getTradeRecords", method = RequestMethod.POST)
    public Response getTradeRecords(@RequestBody TradeRecordQuery query) {
        Response sysResponse = null;
        try {
            int tradeTypeId = query.getTradeTypeId();
            String terminalNumber = query.getTerminalNumber();
            String startTime = query.getStartTime();
            String endTime = query.getEndTime();
            int page = query.getPage();
            int rows = query.getRows();
            sysResponse = Response.getSuccess(tradeRecordService.getTradeRecords(tradeTypeId, terminalNumber, startTime, endTime, page, rows));
        } catch (Exception e) {
            logger.error("查询交易流水信息失败", e);
            sysResponse = Response.getError("查询交易流水失败:系统异常");
        }
        return sysResponse;
    }

    /**
     * [03]统计交易流水
     * 
     * @param tradeTypeId
     * @param terminalNumber
     * @param startTime
     * @param endTime
     * @return
     */
    @RequestMapping(value = "getTradeRecordTotal", method = RequestMethod.POST)
    public Response getTradeRecordTotal(@RequestBody TradeRecordQuery query) {
        Response sysResponse = null;
        try {
            int tradeTypeId = query.getTradeTypeId();
            String terminalNumber = query.getTerminalNumber();
            String startTime = query.getStartTime();
            String endTime = query.getEndTime();
            sysResponse = Response.getSuccess(tradeRecordService.getTradeRecordTotal(tradeTypeId, terminalNumber, startTime, endTime));
        } catch (Exception e) {
            logger.error("统计交易流水信息失败", e);
            sysResponse = Response.getError("统计交易流水失败:系统异常");
        }
        return sysResponse;
    }

    /**
     * [04]获取交易流水详情
     * 
     * @param tradeRecordId
     * @return
     */
    @RequestMapping(value = "getTradeRecord", method = RequestMethod.GET)
    public Response getTradeRecord(@RequestBody TradeRecordQuery query) {
        Response sysResponse = null;
        try {
            int tradeRecordId = query.getTradeRecordId();
            sysResponse = Response.getSuccess(tradeRecordService.getTradeRecord(tradeRecordId));
        } catch (Exception e) {
            logger.error("获取交易流水详情失败", e);
            sysResponse = Response.getError("获取交易流水详情失败:系统异常");
        }
        return sysResponse;
    }

}