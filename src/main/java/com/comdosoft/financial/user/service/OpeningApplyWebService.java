package com.comdosoft.financial.user.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.comdosoft.financial.user.domain.zhangfu.Merchant;
import com.comdosoft.financial.user.domain.zhangfu.OpeningApplie;
import com.comdosoft.financial.user.domain.zhangfu.OpeningRequirement;
import com.comdosoft.financial.user.domain.zhangfu.Terminal;
import com.comdosoft.financial.user.mapper.zhangfu.OpeningApplyWebMapper;


@Service
public class OpeningApplyWebService {
	@Resource
	private OpeningApplyWebMapper openingApplyWebMapper;
	
	@Value("${filePath}")
	private String filePath;

	/**
	 * 获得申请开通列表
	 * 
	 * @param id
	 * @param offSetPage
	 * @param pageSize
	 * @return
	 */
	public List<Map<Object, Object>> getApplyList(Integer id,
			Integer offSetPage, Integer pageSize) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("offSetPage", offSetPage);
		map.put("pageSize", pageSize);
		map.put("twoStatus", Terminal.TerminalTYPEID_2);
		map.put("threeStatus", Terminal.TerminalTYPEID_3);
		return openingApplyWebMapper.getApplyList(map);
	}
	
	/**
	 * 获得申请开通列表总记录数
	 * 
	 * @param id
	 * @param offSetPage
	 * @param pageSize
	 * @return
	 */
	public int getApplyListSize(Integer id,
			Integer offSetPage, Integer pageSize) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("offSetPage", offSetPage);
		map.put("pageSize", pageSize);
		map.put("twoStatus", Terminal.TerminalTYPEID_2);
		map.put("threeStatus", Terminal.TerminalTYPEID_3);
		return openingApplyWebMapper.getApplyListSize(map);
	}
	/**
	 * 根据终端号模糊查询相关终端
	 * 
	 * @param id
	 * @param offSetPage
	 * @param pageSize
	 * @return
	 */
	public List<Map<Object, Object>> searchApplyList(Integer id,
			Integer offSetPage, Integer pageSize,String serialNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("offSetPage", offSetPage);
		map.put("pageSize", pageSize);
		map.put("serialNum", serialNum);
		map.put("twoStatus", Terminal.TerminalTYPEID_2);
		map.put("threeStatus", Terminal.TerminalTYPEID_3);
		return openingApplyWebMapper.searchApplyList(map);
	}
	
	/**
	 * 根据终端号模糊查询相关终端
	 * 
	 * @param id
	 * @param offSetPage
	 * @param pageSize
	 * @return
	 */
	public int searchApplyListSize(Integer id,
			Integer offSetPage, Integer pageSize,String serialNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("offSetPage", offSetPage);
		map.put("pageSize", pageSize);
		map.put("serialNum", serialNum);
		map.put("twoStatus", Terminal.TerminalTYPEID_2);
		map.put("threeStatus", Terminal.TerminalTYPEID_3);
		return openingApplyWebMapper.searchApplyListSize(map);
	}
	
	
	/**
	 * 获得申请开通已有基本信息
	 * 
	 * @param id
	 * @return
	 */
	public Map<String, Object> getOppinfo(Integer terminalsId) {
		OpeningApplie openingApplie =new OpeningApplie();
		openingApplie.setTerminalId(terminalsId);
		
		 SimpleDateFormat sdf =  new SimpleDateFormat( "yyyy-MM-dd" );
		 Map<String, Object> map = openingApplyWebMapper.getOppinfo(openingApplie);
		 if(map!=null){
			 map.put("birthday", sdf.format(map.get("birthday")));
				map.put("created_at", sdf.format(map.get("created_at")));
				map.put("updated_at", sdf.format(map.get("updated_at")));
		 }
		return map;
	}
	
	/**
	 * 城市级联
	 * 
	 * @param id
	 * @param offSetPage
	 * @param pageSize
	 * @return
	 */
	public List<Map<Object, Object>> getCities() {
		return openingApplyWebMapper.getCities();
	}

	/**
	 * 获得终端详情
	 * 
	 * @param id
	 * @return
	 */
	public Map<String, Object> getApplyDetails(Integer id) {
		return openingApplyWebMapper.getApplyDetails(id);
	}
	
	/**
	 * 获得终端材料等级个数
	 * 
	 * @param id
	 * @return
	 */
	public List<OpeningRequirement> getMaterialLevel( int terminalsId) {
		return openingApplyWebMapper.getMaterialLevel(terminalsId);
	}
	
	/**
	 * 修改开通申请资料
	 */
	public void updateApply(OpeningApplie openingApplie) {
		openingApplyWebMapper.updateApply(openingApplie);

	}
	
	/**
     * 判断该终端是否开通
     * 
     * @param map
     * @return
     */
    public int judgeOpen(int terminalId){
    	return openingApplyWebMapper.judgeOpen(terminalId);
    }
    
    /**
     * 判断该终端是否绑定
     * 
     * @return
     */
    public int isopen(int terminalId){
    	return openingApplyWebMapper.isopen(terminalId);
    }
	
	/**
	 * 申请开通时判断商户是否存在
	 * 
	 * @return
	 */
	public void addMerchan(Merchant merchant) {
		openingApplyWebMapper.addMerchan(merchant);
	}
	
	/**
     * 获得该终端信息
     * 
     * @param terminalId
     * @return
     */
    public int isopenMessage(int terminalId){
    	return openingApplyWebMapper.isopenMessage(terminalId);
    }

	/**
	 * 获得所有商户列表
	 * 
	 * @return
	 */
	public List<Merchant> getMerchants(Integer terminalId) {
		return openingApplyWebMapper.getMerchants(terminalId);
	}
	
	/**
	 * 获得所有商户列表
	 * 
	 * @return
	 */
	public int getMerchantSize(Integer customerId,Integer offSetPage,Integer pageSize) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("customerId", customerId);
		map.put("offSetPage", offSetPage);
		map.put("pageSize", pageSize);
		return openingApplyWebMapper.getMerchantSize(map);
	}
	/**
	 * 获得所有通道
	 * @return
	 */
	public List<Map<Object, Object>> getChannels(){
		return openingApplyWebMapper.getChannels();
	}
	
	/**
	 * 获得所有通道周期
	 * @return
	 */
	public List<Map<Object, Object>> channelsT(int id){
		return openingApplyWebMapper.channelsT(id);
	}

	/**
	 * 申请资料数据回显
	 * 
	 * @param id
	 * @return
	 */
	public List<Map<Object, Object>> ReApplyFor(Integer id) {
		List<Map<Object, Object>> list = new ArrayList<Map<Object,Object>>();
        list = openingApplyWebMapper.ReApplyFor(id);
        for(int i=0;i<list.size();i++){
       	 if((Integer)list.get(i).get("types") == 2){
       		 list.get(i).put("value",filePath+list.get(i).get("value").toString());
       	 }else {
       		list.get(i).put("value",list.get(i).get("value").toString());
       	 }
        }
		return list;
	}

	/**
	 * 根据商户id获得详细信息
	 * 
	 * @param id
	 * @return
	 */
	public Map<Object, Object> getMerchant(Integer merchantId) {
		return openingApplyWebMapper.getMerchant(merchantId);
	}

	/**
	 * 申请开通时判断商户是否存在
	 * 
	 * @return
	 */
	public Map<Object, Object> getMerchantsIsNo(String merchantName,String phone) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("merchantName", merchantName);
		map.put("phone", phone);
		return openingApplyWebMapper.getMerchantsIsNo(map);
	}
	
	/**
	 * 添加开通申请资料
	 * 
	 * @param map
	 */
	public void addApply(String key, Object value,Integer types, String openingAppliesId,Integer openingRequirementId,Integer targetId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("key", key);
		map.put("value", value);
		map.put("types", types);
		map.put("openingAppliesId", openingAppliesId);
		map.put("openingRequirementId", openingRequirementId);
		map.put("targetId", targetId);
		openingApplyWebMapper.addApply(map);
	}

	/**
	 * 添加 开通申请关联
	 */
	public void addOpeningApply(OpeningApplie openingApplie) {
		openingApplyWebMapper.addOpeningApply(openingApplie);

	}

	/**
	 * 获得材料名字
	 * 
	 * @param id
	 * @return
	 */
	public List<Map<Object, Object>> getMaterialName(Integer id, Integer status) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("terminalsId", id);
		map.put("status", status);
		return openingApplyWebMapper.getMaterialName(map);
	}

	/**
	 * 获得申请表的id
	 * 
	 * @param id
	 * @return
	 */
	public int getApplyesId(Integer id) {
		return openingApplyWebMapper.getApplyesId(id);
	}

	/**
	 * 重新申请开通(先删除旧数据)
	 * 
	 * @param id
	 */
	public void deleteOpeningInfos(Integer id) {
		openingApplyWebMapper.deleteOpeningInfos(id);
	}
	
	/**
	 * 终端表关联商户id和通道周期
	 * 
	 */
	public void updateterminal(int merchantId,int terminalId,int billingCyclesId) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("merchantId", merchantId);
		map.put("terminalId", terminalId);
		map.put("billingCyclesId", billingCyclesId);
		openingApplyWebMapper.updateterminal(map);
	}
	
	/**
	 * 更该重新申请状态
	 * @param openingApplie
	 */
	public void updateOpeningApplyStatus(int openingAppliesId){
		
		OpeningApplie openingApplie = new OpeningApplie();
		openingApplie.setId(openingAppliesId);
		openingApplie.setStatus(OpeningApplie.STATUS_1);
		openingApplyWebMapper.updateOpeningApplyStatus(openingApplie);
	}
}
