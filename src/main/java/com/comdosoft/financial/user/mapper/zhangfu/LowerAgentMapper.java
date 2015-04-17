package com.comdosoft.financial.user.mapper.zhangfu;

import java.util.List;
import java.util.Map;

import com.comdosoft.financial.user.domain.query.LowerAgentReq;

/**
 * 下级代理商
 * @author yyb
 *
 */
public interface LowerAgentMapper {
	
	int getLowerAgentTotal(LowerAgentReq req);
	
	List<Map<String, Object>> getProfitList(LowerAgentReq req);
	
	List<Map<String, Object>> getLowerAgentList(LowerAgentReq req);
	
	Map<String, Object> getInfo(LowerAgentReq req);
	
	List<Map<String,Object>> getTradelist(int id);
	
	List<Map<String,Object>> getChannellist();
	
	List<Map<String,Object>> getProvinceList();
	
	List<Map<String,Object>> getCityList(int proId);
	
	Map<String,Object> getProCity(int cityId);
	
	int changeStatus(LowerAgentReq req);
	
	int addNewAgent(LowerAgentReq req);
	
	int addNewCustomer(LowerAgentReq req);
	
	int saveAgents(LowerAgentReq req);
	
	int saveCustomers(LowerAgentReq req);
	
	int delChannel(LowerAgentReq req);
	
	Map<String, Object> checkLoginId(LowerAgentReq req);
	
	int getCustomerId(LowerAgentReq req);
	
	int savePrecent(LowerAgentReq req);
	int editPrecent(LowerAgentReq req);
	
	int changePwd(LowerAgentReq req);
	
	int changeProfit(LowerAgentReq req);
	
	int checkChannelById(LowerAgentReq req);
	
	String getParentAgentCode(LowerAgentReq req);
	
	List<Map<String,Object>> getChildAgentCode(LowerAgentReq req);
	
}
