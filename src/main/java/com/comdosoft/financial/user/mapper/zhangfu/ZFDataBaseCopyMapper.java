package com.comdosoft.financial.user.mapper.zhangfu;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

public interface ZFDataBaseCopyMapper {
	Map<String, Object> getPayChannelById(@Param("id") int id);
	int updatePayChannelById(@Param("id") int id,@Param("channelName") String channelName);
	int channelNameInit(@Param("id") int id,@Param("channelName") String channelName);
	
	int goodsInit(@Param("id") int id,@Param("goodName") String goodName);
	Map<String, Object> getGoodsById(@Param("id") int id);
	int updateGoodsById(@Param("id") int id,@Param("goodName") String goodName);
	
	int goodsChannelRelationInit(@Param("goodId") int goodId,@Param("channelId") int channelId);
	Map<String, Object> getGoodPayChannelById(@Param("goodId") int goodId,@Param("channelId") int channelId);
	
	Map<String, Object> getCustomersByUserName(@Param("userName") String userName);
	
	//#{id},#{userName},#{accoutType},#{phone},#{name},#{status},#{pwd},#{types}
	int customersInit(@Param("userName") String userName,@Param("accoutType") int accoutType,@Param("phone") String phone,
			@Param("name") String name,@Param("status") int status,@Param("pwd") String pwd,@Param("types") int types);

	int merchantsInit(@Param("idNumber") String idNumber,@Param("customerId") int customerId);
	
	Map<String, Object> getChannelIdByGoodId(@Param("goodId") int goodId);
//	#{id},#{status},#{serialNum},#{baseRate},#{goodId},#{channelId}
	int terminalInit(@Param("status") int status,@Param("serialNum") String serialNum,
			@Param("baseRate") String baseRate,@Param("goodId") int goodId,@Param("channelId") int channelId,
			@Param("merchantId") int merchantId,@Param("customerId") int customerId);
	
	
	Map<String, Object> getMerchantsIdByCustomerId(@Param("customerId") int customerId);
//	#{terminalId},#{merchantId},#{merchantName},#{bankNum},#{bankName},#{bankCode})
	int openingAppliesInit(@Param("terminalId") int terminalId,@Param("merchantId") int merchantId,@Param("merchantName") String merchantName,
			@Param("bankNum") String bankNum,@Param("bankName") String bankName,@Param("bankCode") String bankCode,
			@Param("customerId") int customerId,@Param("types") String types,@Param("taxNo") String taxNo,@Param("occn") String occn);
	
	int updateMerchantsById(@Param("id") int id,@Param("merchantName") String merchantName,@Param("bankName") String bankName,
			@Param("bankNum") String bankNum);
	
	int updateTerminalBySerialNum(@Param("customerId") int customerId,@Param("status") int status,@Param("serialNum") String serialNum);

	Map<String, Object> getCustomerIdByUserName(@Param("username") String userName);
//	#{companyName},#{businessLicense},#{customerId},#{cardId}
	int agentsInit(@Param("companyName") String companyName,@Param("businessLicense") String businessLicense,@Param("customerId") int customerId,
			@Param("cardId") String cardId,@Param("parentId") String parentId,@Param("phone") String phone);
//	#{cityId},#{address},#{zipCode},#{receiver},#{moblePhone},#{telPhone},#{customerId}
	int customerAddressesInit(@Param("cityId") String cityId,@Param("address") String address,@Param("zipCode") String zipCode,
			@Param("receiver") String receiver,@Param("moblePhone") String moblePhone,@Param("telPhone") String telPhone,
			@Param("customerId") int customerId);

	Map<String, Object> getCityIdByCityName(@Param("cityName") String cityName);
	
	int updateGoodsQuantity(@Param("id") String id,@Param("quantity") String quantity);
	
	int prepareGoodRecordsInit(@Param("agentId") String agentId,@Param("goodId") String goodId,@Param("quantity") String quantity);

	

	int updateTerminalBySerialNum1(@Param("status") String status,@Param("serialNum") String serialNum,@Param("goodId") String goodId,
			@Param("agentId") String agentId);

	int dictionaryTradeTypesInit(@Param("tradeType") String tradeType,@Param("tradeValue") String tradeValue);
//	#{tradeType},#{payChannelId},#{tradeTypeId})
	int supportTradeTypesInit(@Param("tradeType") String tradeType,@Param("payChannelId") String payChannelId,
			@Param("tradeTypeId") int tradeTypeId);

	int getMaxIdOfdictionaryTradeTypes();

	int updateCustomerCityId(@Param("cityId") int cityId,@Param("customerId") int customerId);

	int getMaxTerminalId();

	List<Map<String, Object>> getCustomerIdBySerialNum(@Param("serialNum") String serialNum);
	
	List<Map<String, Object>> getAgentIdByCustomerId(@Param("customerId") String customerId);
}
