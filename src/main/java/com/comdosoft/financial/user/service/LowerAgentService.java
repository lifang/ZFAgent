package com.comdosoft.financial.user.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.comdosoft.financial.user.domain.query.LowerAgentReq;
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
	
	@Autowired
	private SystemSetService sys;
	
	@Value("${filePath}")
	private String filePath;
	/**
	 * 修改代理商状态
	 * @param req
	 * @return
	 * @throws Exception 
	 */
	@Transactional(value="transactionManager-zhangfu",propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String,Object> changeStatus(LowerAgentReq req) throws Exception{
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
			throw new Exception("修改状态出错");
		}
		String resultInfo="执行修改下级代理商状态操作,结果为："+map.get("resultInfo");
		int temp=sys.operateRecord(resultInfo,req.getAgentsId());
		if(temp<1){
			throw new Exception("保存操作记录出错");
		}
		return map;
	}
	
	/**
	 * 修改默认分润比例
	 * @param req
	 * @return
	 */
	public Map<String,Object> getDefaultProfit(LowerAgentReq req){
		Map<String,Object> map =new HashMap<String, Object>();
		String defaultProfit=lowerAgentMapper.getDefaultProfit(req);
		if(defaultProfit==null){
			defaultProfit="0";
		}
		map.put("resultCode", 1);
		map.put("resultInfo", defaultProfit);
		return map;
	}
	
	/**
	 * 修改默认分润比例
	 * @param req
	 * @return
	 * @throws Exception 
	 */
	@Transactional(value="transactionManager-zhangfu",propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String,Object> changeProfit(LowerAgentReq req) throws Exception{
		Map<String,Object> map =new HashMap<String, Object>();
		float profit=req.getDefaultProfit()*10;
		
		if(profit>1000 || profit<0){
			map.put("resultCode", -1);
			map.put("resultInfo", "默认分润比例必须介于0-100之间");
		}else{
			int result=lowerAgentMapper.changeProfit(req);
			if(result>=1){
				//成功
				map.put("resultCode", 1);
				map.put("resultInfo", "修改默认分润比例成功");
			}else{
				//失败
				map.put("resultCode", -1);
				map.put("resultInfo", "修改默认分润比例出错");
				throw new Exception("修改默认分润比例出错");
			}
		}
		
		String resultInfo="执行修改下级代理商默认分润比例操作,结果为："+map.get("resultInfo");
		int temp=sys.operateRecord(resultInfo,req.getAgentsId());
		if(temp<1){
			throw new Exception("保存操作记录出错");
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
    			float percent=Float.parseFloat(list.get(j).get("percent").toString())/10;
    			String id=list.get(j).get("pay_channel_id").toString();
    			String tradeTypeId=list.get(j).get("tradeTypeId").toString();
        		if(channelName.equals(channelName1)){
        			newMap.put("id", id);
        			Map<String,Object> newMap1=new HashMap<String, Object>();
        			
        			newMap1.put("tradeTypeName", tradeTypeName);
        			newMap1.put("percent", percent);
        			newMap1.put("tradeTypeId", tradeTypeId);
        			childList.add(newMap1);
        		}
        		newMap.put("detail", childList);
        	}
        	newList.add(newMap);
        }
        map.put("list", newList);
        return map;
    }
	
	/**
	 * 获取channelList表
	 * @return
	 */
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
		Map<String, Object> map=lowerAgentMapper.getInfo(req);
		String temp=filePath+" "+map.get("cardpath").toString();
		map.put("cardpath", temp);
		String temp1=filePath+" "+map.get("licensepath").toString();
		map.put("licensepath", temp1);
		String temp2=filePath+" "+map.get("taxpath").toString();
		map.put("taxpath", temp2);
        return map;
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
	
	/**
	 * 新增下级代理商
	 * @param req
	 * @return
	 * @throws Exception 
	 */
	@Transactional(value="transactionManager-zhangfu",propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String,Object> addNewAgent(LowerAgentReq req) throws Exception {
		
		Map<String, Object> map=new HashMap<String, Object>();
    	//向customers表中插入记录
    	//调用加密方法
		if(null==req.getIsEncrypt() || !req.getIsEncrypt()){
			req.setPwd(SysUtils.string2MD5(req.getPwd()));
		}
    	//判断该登陆名是否已经存在
    	Map<String, Object> mapTemp=lowerAgentMapper.checkLoginId(req);
    	if(Integer.parseInt(mapTemp.get("num").toString())>=1){
    		//已经存在
    		map.put("resultCode", -1);
    		map.put("resultInfo", "当前登录名已经存在！");
    	}else{
    		req.setTypes(2);
        	int affect_series1=lowerAgentMapper.addNewCustomer(req);
        	if(affect_series1==1){
        		//成功
                //获取新的agents表ID
        		int customer_id=lowerAgentMapper.getCustomerId(req);
            	req.setCustomerId(customer_id);
            	
            	int temp1=0;
            	//插入权限
            	req.setRoleId(3);
            	temp1=lowerAgentMapper.createRoleRelation(req);
            	if(temp1<=0){
            		throw new Exception("新增用户权限失败");
            	}
            	req.setRoleId(4);
            	temp1=lowerAgentMapper.createRoleRelation(req);
            	if(temp1<=0){
            		throw new Exception("新增用户权限失败");
            	}
            	req.setRoleId(5);
            	temp1=lowerAgentMapper.createRoleRelation(req);
            	if(temp1<=0){
            		throw new Exception("新增用户权限失败");
            	}
            	req.setRoleId(6);
            	temp1=lowerAgentMapper.createRoleRelation(req);
            	if(temp1<=0){
            		throw new Exception("新增用户权限失败");
            	}
            	req.setRoleId(7);
            	temp1=lowerAgentMapper.createRoleRelation(req);
            	if(temp1<=0){
            		throw new Exception("新增用户权限失败");
            	}
            	req.setRoleId(8);
            	temp1=lowerAgentMapper.createRoleRelation(req);
            	if(temp1<=0){
            		throw new Exception("新增用户权限失败");
            	}
            	req.setRoleId(9);
            	temp1=lowerAgentMapper.createRoleRelation(req);
            	if(temp1<=0){
            		throw new Exception("新增用户权限失败");
            	}
            	//agengCode
            	String parentAgentCode=lowerAgentMapper.getParentAgentCode(req);
            	int lengthTemp=parentAgentCode.length();
            	List<Map<String, Object>> list=lowerAgentMapper.getChildAgentCode(req);
            	
            	int temp=0;
            	
            	if(list.size()>0){
	            	for(int i=0;i<list.size();i++){
	            		String tempStr1=list.get(i).get("code").toString();
	            		if(tempStr1.length()>3 && tempStr1.length()<=6){
	            			String tempStr=list.get(i).get("code").toString().substring(lengthTemp, lengthTemp+3);
		            		if(tempStr.trim().equals("")){
		            			
		            		}else{
			            		int tempCode=Integer.parseInt(tempStr.trim());
			            		if(tempCode>=temp){
			            			temp=tempCode;
			            		}
		            		}
	            		}
	            	}
            	}
            	temp=temp+1;
            	StringBuilder tempCode=new StringBuilder();
            	tempCode.append(parentAgentCode);
            	if(temp<=9 && temp>=0){
            		tempCode.append("00"+temp);
            	}else if(temp>=10 && temp<=99){
            		tempCode.append("0"+temp);
            	}else{
            		tempCode.append(temp);
            	}
            	req.setCode(tempCode.toString());
            	//向agents表中插入记录
            	int affect_series=lowerAgentMapper.addNewAgent(req);
            	if(affect_series >=1){
            		map.put("resultCode", 1);
	        		map.put("resultInfo", "新增成功");
            	}else{
            		map.put("resultCode", -1);
	        		map.put("resultInfo", "新增agents表出错");
	        		throw new Exception("新增agents表出错");
            	}
        	}else{
        		//失败
        		map.put("resultCode", -1);
        		map.put("resultInfo", "新增customers表出错");
        		throw new Exception("新增customers表出错");
        	}
    	}
    	String resultInfo="执行新增下级代理商操作,结果为："+map.get("resultInfo");
		int temp=sys.operateRecord(resultInfo,req.getAgentsId());
		if(temp<1){
			throw new Exception("保存操作记录出错");
		}
		return map;
    }
	
	/**
	 * 修改下级代理商密码
	 * @param req
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("finally")
	@Transactional(value="transactionManager-zhangfu",propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String,Object> changePwd(LowerAgentReq req) throws Exception{
		Map<String,Object> map=new HashMap<String, Object>();
		req.setPwd(SysUtils.string2MD5(req.getPwd()));
		int result=lowerAgentMapper.changePwd(req);
		
		if(result>=1){
			map.put("errorCode", 1);
			map.put("errorInfo", "保存成功！");
		}else{
			map.put("errorCode", -1);
			map.put("errorInfo", "保存出错！");
			throw new Exception("保存出错！");
		}
		String resultInfo="执行修改下级代理商密码操作,结果为："+map.get("errorInfo");
		
		int temp=sys.operateRecord(resultInfo,req.getAgentsId());
		if(temp<1){
			throw new Exception("保存操作记录出错");
		}
		return map;
	}
	
	/**
	 * 修改下级代理商，保存
	 * @param req
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("finally")
	@Transactional(value="transactionManager-zhangfu",propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String,Object> save(LowerAgentReq req) throws Exception {
		Map<String,Object> map=new HashMap<String, Object>();
    	int affect_series1=lowerAgentMapper.saveAgents(req);
    	//调用加密方法
    	req.setPwd(SysUtils.string2MD5(req.getPwd()));
    	int affect_series2=lowerAgentMapper.saveCustomers(req);
    	
    	//都更新成功
    	if(affect_series1>=1 && affect_series2>=1){
    		map.put("resultCode", 1);
			map.put("resultInfo", "保存修改成功！");
    	}else{
    		map.put("resultCode", -1);
			map.put("resultInfo", "保存修改失败！");
			throw new Exception("保存修改失败");
    	}
    	String resultInfo="执行修改下级代理商信息操作,结果为："+map.get("resultInfo");
		int temp=sys.operateRecord(resultInfo,req.getAgentsId());
		if(temp<1){
			throw new Exception("保存操作记录出错");
		}
		return map;
    }
	
	
	@SuppressWarnings("finally")
	@Transactional(value="transactionManager-zhangfu",propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String,Object> delChannel(LowerAgentReq req) throws Exception {
		Map<String,Object> map=new HashMap<String, Object>();
    	int affect_series1=lowerAgentMapper.delChannel(req);
    	//都更新成功
    	if(affect_series1>=1){
    		map.put("resultCode", 1);
			map.put("resultInfo", "删除成功！");
    	}else{
    		map.put("resultCode", -1);
			map.put("resultInfo", "删除失败！");
    	}
    	String resultInfo="执行删除下级代理商分润渠道操作,结果为："+map.get("resultInfo");
    	int temp=sys.operateRecord(resultInfo,req.getAgentsId());
		if(temp<1){
			throw new Exception("保存操作记录出错");
		}
		return map;
    }
	
	
	
	public int checkLoginId(LowerAgentReq req) {
		Map<String, Object> map=lowerAgentMapper.checkLoginId(req);
        return Integer.parseInt(map.get("num").toString());
    }
	
	
	@SuppressWarnings("finally")
	@Transactional(value="transactionManager-zhangfu",propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, Object> openCloseProfit(LowerAgentReq req) throws Exception{
		Map<String,Object> map=new HashMap<String, Object>();
		int resultCode=-1;
		StringBuffer resultInfo=new StringBuffer("");
		
		int temp=lowerAgentMapper.openCloseProfit(req);
		if(temp<1){
			resultInfo.setLength(0);
			resultInfo.append("更新是否有分润失败");
			throw new Exception("更新是否有分润失败");
		}else{
			resultCode=1;
			resultInfo.setLength(0);
			resultInfo.append("执行更新下级代理商是否分润操作,结果为：更新是否有分润失败");
	    	int temp1=sys.operateRecord(resultInfo.toString(),req.getAgentsId());
			if(temp1<1){
				throw new Exception("更新是否有分润失败");
			}
		}
		map.put("resultCode", resultCode);
		map.put("resultInfo", resultInfo.toString());
		return map;
	}
	
	/**
	 * 代理商 分润新增  或者保存  1为新增，0为保存
	 * @param req
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("finally")
	@Transactional(value="transactionManager-zhangfu",propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String,Object> saveOrEdit(LowerAgentReq req) throws Exception{
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
				float precent=Float.parseFloat(temp2[0])*10;
				req.setTradeTypeId(tradeTypeId);
				req.setPrecent(precent);
				
				//看该代理商是否已经存在该渠道的分润设置
				int temp=lowerAgentMapper.checkChannelById(req);
				if(temp>=1){
					map.put("resultCode", -1);
					map.put("resultInfo", "该代理商支付通道分润比例设置已经存在！");
				}else{
					int result=lowerAgentMapper.savePrecent(req);
					if(result<1){
						map.put("resultCode", -1);
						map.put("resultInfo", "保存出错！");
						throw new Exception("保存分润比例出错");
					}
				}
			}
			if(map==null || map.get("resultCode")==null){
				map.put("resultCode", 1);
				map.put("resultInfo", "保存成功！");
			}else if(!map.get("resultCode").toString().equals("-1")){
				map.put("resultCode", 1);
				map.put("resultInfo", "保存成功！");
			}
			String resultInfo="执行新增下级代理商支付通道分润比例操作,结果为："+map.get("resultInfo");
			sys.operateRecord(resultInfo,req.getAgentsId());
		}else if(req.getSign() ==0){
			//修该
			//遍历分解
			String profitPercent=req.getProfitPercent();
			//tradeTypeId_channelId
			String[] temp1=profitPercent.split("\\|");
			for(int i=0;i<temp1.length;i++){
				String[] temp2=temp1[i].split("\\_");
				int tradeTypeId=Integer.parseInt(temp2[1]);
				float precent=Float.parseFloat(temp2[0])*10;
				req.setTradeTypeId(tradeTypeId);
				req.setPrecent(precent);
				int result=lowerAgentMapper.editPrecent(req);
				if(result<1){
					map.put("resultCode", -1);
					map.put("resultInfo", "保存出错！");
					throw new Exception("修改分润比例出错");
				}
			}
			if(map==null || map.get("resultCode")==null){
				map.put("resultCode", 1);
				map.put("resultInfo", "保存成功！");
			}else if(!map.get("resultCode").toString().equals("-1")){
				map.put("resultCode", 1);
				map.put("resultInfo", "保存成功！");
			}
			
			String resultInfo="执行修改下级代理商分润操作,结果为："+map.get("resultInfo");
			int temp=sys.operateRecord(resultInfo,req.getAgentsId());
			if(temp<1){
				throw new Exception("保存操作记录出错");
			}
		}
		return map;
	}
	
	
	
}
