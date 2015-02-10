package com.comdosoft.financial.user.controller.api;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.comdosoft.financial.user.domain.Response;
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
     * [01]获取用户终端列表
     * 
     * @param customerId
     * @return
     */
    @RequestMapping(value = "getTerminals/{customerId}", method = RequestMethod.GET)
    public Response getAPPLatestVersion(@PathVariable int customerId) {
        Response sysResponse = null;
        try {
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
    @RequestMapping(value = "getTradeRecords/{tradeTypeId}/{terminalNumber}/{startTime}/{endTime}/{page}/{rows}", method = RequestMethod.GET)
    public Response getTradeRecords(@PathVariable int tradeTypeId,
                                    @PathVariable String terminalNumber,
                                    @PathVariable String startTime,
                                    @PathVariable String endTime,
                                    @PathVariable int page,
                                    @PathVariable int rows) {
        Response sysResponse = null;
        try {
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
    @RequestMapping(value = "getTradeRecordTotal/{tradeTypeId}/{terminalNumber}/{startTime}/{endTime}", method = RequestMethod.GET)
    public Response getTradeRecordTotal(@PathVariable int tradeTypeId,
                                        @PathVariable String terminalNumber,
                                        @PathVariable String startTime,
                                        @PathVariable String endTime) {
        Response sysResponse = null;
        try {
            sysResponse = Response.getSuccess(tradeRecordService.getTradeRecordTotal(tradeTypeId, terminalNumber, startTime, endTime));
        } catch (Exception e) {
            logger.error("统计交易流水信息失败", e);
            sysResponse = Response.getError("统计交易流水失败:系统异常");
        }
        return sysResponse;
    }

}