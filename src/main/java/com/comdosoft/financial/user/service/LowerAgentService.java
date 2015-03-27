package com.comdosoft.financial.user.service;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionStatus;

import com.comdosoft.financial.user.domain.query.ExchangeGoodReq;
import com.comdosoft.financial.user.domain.query.LowerAgentReq;
import com.comdosoft.financial.user.domain.query.PrepareGoodReq;
import com.comdosoft.financial.user.mapper.zhangfu.LowerAgentMapper;
import com.comdosoft.financial.user.utils.Param;
import com.comdosoft.financial.user.utils.SysUtils;
/**
 * 下级代理商
 * @author yyb
 *
 */
@Service
public class LowerAgentService {
	
	@Autowired
	private LowerAgentMapper lowerAgentMapper;
	
	
	public Map<String,Object> changeStatus(LowerAgentReq req){
		Map<String,Object> map =new HashMap<String, Object>();
		if(req.getStatus()==5){
			req.setStatus(6);
		}else if(req.getStatus()==6){
			req.setStatus(5);
		}
		int result=lowerAgentMapper.changeStatus(req);
		if(result>=1){
			//成功
			map.put("resultCode", 1);
			map.put("resultInfo", "修改状态成功");
		}else{
			//失败
			map.put("resultCode", -1);
			map.put("resultInfo", "修改状态出错");
		}
		return map;
	}
	
	public Map<String, Object> getProfitlist(LowerAgentReq req) {
        req.setStartTime(Param.setDay(req.getStartTime()));
        req.setEndTime(Param.setDay(req.getEndTime()));
        Map<String, Object> map=new HashMap<String, Object>();
        //int total=lowerAgentMapper.getLowerAgentTotal(req);
        //map.put("total", total);
        List<Map<String, Object>> list=lowerAgentMapper.getProfitList(req);
        
        //去掉重复的channelName的list
        List<String> channelNameList=new ArrayList<String>();
        for(int i=0;i<list.size();i++){
        	String channelName=list.get(i).get("channelName").toString();
        	if(channelNameList.size()==0){
        		channelNameList.add(channelName);
        	}else{
        		for(int j=0;j<channelNameList.size();j++){
        			if(channelNameList.contains(channelName)){}else{
        				channelNameList.add(channelName);
        			}
        		}
        	}
        }
        
        List<Map<String, Object>> newList=new ArrayList<Map<String,Object>>();
        for(int i=0;i<channelNameList.size();i++){
        	String channelName=channelNameList.get(i).toString();
        	
        	Map<String,Object> newMap=new HashMap<String, Object>();
        	newMap.put("channelName", channelName);
        	List<Map<String, Object>> childList=new ArrayList<Map<String,Object>>();
        	for(int j=0;j<list.size();j++){
        		String channelName1=list.get(j).get("channelName").toString();
        		String tradeTypeName=list.get(j).get("tradeTypeName").toString();
    			String percent=list.get(j).get("percent").toString();
    			String id=list.get(j).get("pay_channel_id").toString();
    			
        		if(channelName.equals(channelName1)){
        			newMap.put("id", id);
        			Map<String,Object> newMap1=new HashMap<String, Object>();
        			
        			newMap1.put("tradeTypeName", tradeTypeName);
        			newMap1.put("percent", percent);
        			childList.add(newMap1);
        		}
        		newMap.put("detail", childList);
        	}
        	newList.add(newMap);
        }
        map.put("list", newList);
        return map;
    }
	
	
	public Map<String, Object> getChannellist() {
        Map<String, Object> map=new HashMap<String, Object>();
        List<Map<String, Object>> list=lowerAgentMapper.getChannellist();
        map.put("list", list);
        return map;
    }
	
	
	public Map<String, Object> getTradelist(int id) {
        Map<String, Object> map=new HashMap<String, Object>();
        List<Map<String, Object>> list=lowerAgentMapper.getTradelist(id);
        map.put("list", list);
        return map;
    }
	
	public Map<String, Object> getList(LowerAgentReq req) {
        req.setStartTime(Param.setDay(req.getStartTime()));
        req.setEndTime(Param.setDay(req.getEndTime()));
        Map<String, Object> map=new HashMap<String, Object>();
        int total=lowerAgentMapper.getLowerAgentTotal(req);
        map.put("total", total);
        List<Map<String, Object>> list=lowerAgentMapper.getLowerAgentList(req);
        map.put("list", list);
        return map;
    }
	
	public Map<String, Object> getInfo(LowerAgentReq req) {
        return lowerAgentMapper.getInfo(req);
    }
	
	public Map<String, Object> getProCity(LowerAgentReq req) {
        return lowerAgentMapper.getProCity(req.getCityId());
    }
	
	
	public Map<String,Object> getProvinceList(){
		Map<String, Object> map=new HashMap<String, Object>();
		List<Map<String, Object>> list= lowerAgentMapper.getProvinceList();
		map.put("list", list);
        return map;
	}
	
	public Map<String,Object> getCityList(int proId){
		Map<String, Object> map=new HashMap<String, Object>();
		List<Map<String, Object>> list= lowerAgentMapper.getCityList(proId);
		map.put("list", list);
        return map;
	}
	
	@Transactional(value="transactionManager-zhangfu",propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String,Object> addNewAgent(LowerAgentReq req) {
		
		Map<String, Object> map=new HashMap<String, Object>();
        	//向customers表中插入记录
        	//调用加密方法
        	req.setPwd(SysUtils.string2MD5(req.getPwd()));;
        	//判断该登陆名是否已经存在
        	if(lowerAgentMapper.checkLoginId(req)>=1){
        		//已经存在
        		map.put("resultCode", -1);
        		map.put("resultInfo", "当前登录名已经存在！");
        	}else{
	        	int affect_series1=lowerAgentMapper.addNewCustomer(req);
	        	if(affect_series1==1){
	        		//成功
	                //获取新的agents表ID
	        		int customer_id=lowerAgentMapper.getCustomerId(req);
	            	req.setCustomer_id(customer_id);
	            	//向agents表中插入记录
	            	int affect_series=lowerAgentMapper.addNewAgent(req);
	            	if(affect_series >=1){
	            		map.put("resultCode", 1);
		        		map.put("resultInfo", "新增成功");
	            	}else{
	            		map.put("resultCode", -1);
		        		map.put("resultInfo", "新增agents表出错");
	            	}
	        	}else{
	        		//失败
	        		map.put("resultCode", -1);
	        		map.put("resultInfo", "新增customers表出错");
	        	}
        	}
        return map;
    }
	
	
	public int save(LowerAgentReq req) {
    	int affect_series1=lowerAgentMapper.saveAgents(req);
    	
    	//调用加密方法
    	//req.setPwd(req.getPwd());
    	int affect_series2=lowerAgentMapper.saveCustomers(req);
    	
    	//都更新成功
    	if(affect_series1>=1 && affect_series2>=1){
    		return 1;
    	}else{
    		return 0;
    	}
    }
	
	public int checkLoginId(LowerAgentReq req) {
        return lowerAgentMapper.checkLoginId(req);
    }
	
	@Transactional(value="transactionManager-zhangfu",propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String,Object> saveOrEdit(LowerAgentReq req){
		
		Map<String,Object> map=new HashMap<String, Object>();
		
		//precent_tradeId|precent_tradeId
		if(req.getSign() == 1){
			//新增 
			//遍历分解
			String profitPercent=req.getProfitPercent();
			//tradeTypeId_channelId
			String[] temp1=profitPercent.split("\\|");
			for(int i=0;i<temp1.length;i++){
				String[] temp2=temp1[i].split("\\_");
				int tradeTypeId=Integer.parseInt(temp2[1]);
				int precent=Integer.parseInt(temp2[0]);
				req.setTradeTypeId(tradeTypeId);
				req.setPrecent(precent);
				
				int result=lowerAgentMapper.savePrecent(req);
				if(result<1){
					map.put("errorCode", -1);
					map.put("errorInfo", "保存出错！");
				}
			}
		}else if(req.getSign() ==0){
			//修该
			//遍历分解
			String profitPercent=req.getProfitPercent();
			//tradeTypeId_channelId
			String[] temp1=profitPercent.split("\\|");
			for(int i=0;i<temp1.length;i++){
				String[] temp2=temp1[i].split("\\_");
				int tradeTypeId=Integer.parseInt(temp2[1]);
				int precent=Integer.parseInt(temp2[0]);
				req.setTradeTypeId(tradeTypeId);
				req.setPrecent(precent);
				int result=lowerAgentMapper.editPrecent(req);
				if(result<1){
					map.put("errorCode", -1);
					map.put("errorInfo", "保存出错！");
				}
			}
		}
		return map;
	}
}