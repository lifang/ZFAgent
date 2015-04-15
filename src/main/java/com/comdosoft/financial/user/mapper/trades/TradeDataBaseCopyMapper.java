package com.comdosoft.financial.user.mapper.trades;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface TradeDataBaseCopyMapper {
	int tradeRecordsInit(@Param("tradeNumber") String tradeNumber,@Param("batchNumber") String batchNumber,@Param("terminalNumber") String terminalNumber,
			@Param("orderNumber") String orderNumber,@Param("agentId") String agentId,@Param("merchantNumber") String merchantNumber,
			@Param("merchantName") String merchantName,@Param("payChannelId") String payChannelId,@Param("profitPrice") String profitPrice,
			@Param("amount") String amount,@Param("poundage") String poundage,@Param("tradedAt") String tradedAt,@Param("sysOrderId") String sysOrderId,
			@Param("accountName") String accountName,@Param("accountNumber") String accountNumber,@Param("actualPaymentPrice") String actualPaymentPrice,
			@Param("paidResult") String paidResult,@Param("paidCode") String paidCode,@Param("paidErrorDescription") String paidErrorDescription,
			@Param("tradeTypeId") String tradeTypeId,
			@Param("tradedStatus") String tradedStatus,@Param("attachStatus") String attachStatus,@Param("customerId") String customerId,
			@Param("types") String types,@Param("cityId") String cityId,@Param("integralStatus") String integralStatus,
			@Param("payFromAccount") String payFromAccount);
}
