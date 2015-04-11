package com.comdosoft.financial.user.service;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.comdosoft.financial.user.domain.zhangfu.Merchant;
import com.comdosoft.financial.user.domain.zhangfu.OpeningApplie;
import com.comdosoft.financial.user.domain.zhangfu.Terminal;
import com.comdosoft.financial.user.mapper.zhangfu.OpeningApplyMapper;

@Service
public class OpeningApplyService {
	@Resource
	private OpeningApplyMapper openingApplyMapper;

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
		return openingApplyMapper.getApplyList(map);
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
		return openingApplyMapper.getApplyListSize(map);
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
		return openingApplyMapper.searchApplyList(map);
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
		return openingApplyMapper.searchApplyListSize(map);
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
		 Map<String, Object> map = openingApplyMapper.getOppinfo(openingApplie);
		 if(map!=null){
			 map.put("birthday", sdf.format(map.get("birthday")));
				map.put("created_at", sdf.format(map.get("created_at")));
				map.put("updated_at", sdf.format(map.get("updated_at")));
		 }
		return map;
	}

	/**
	 * 获得终端详情
	 * 
	 * @param id
	 * @return
	 */
	public Map<String, Object> getApplyDetails(Integer id) {
		return openingApplyMapper.getApplyDetails(id);
	}
	
	/**
	 * 修改开通申请资料
	 */
	public void updateApply(OpeningApplie openingApplie) {
		openingApplyMapper.updateApply(openingApplie);

	}
	
	/**
     * 判断该终端是否开通
     * 
     * @param map
     * @return
     */
    public int judgeOpen(int terminalId){
    	return openingApplyMapper.judgeOpen(terminalId);
    }
	
	/**
	 * 申请开通时判断商户是否存在
	 * 
	 * @return
	 */
	public void addMerchan(Merchant merchant) {
		openingApplyMapper.addMerchan(merchant);
	}

	/**
	 * 获得所有商户列表
	 * 
	 * @return
	 */
	public List<Merchant> getMerchants(Integer customerId,Integer offSetPage,Integer pageSize,String title) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("customerId", customerId);
			map.put("offSetPage", offSetPage);
			map.put("pageSize", pageSize);
			map.put("title", title);
		return openingApplyMapper.getMerchants(map);
	}
	
	/**
	 * 获得所有商户列表
	 * 
	 * @return
	 */
	public int getMerchantSize(Integer customerId,Integer offSetPage,Integer pageSize,String title) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("customerId", customerId);
		map.put("offSetPage", offSetPage);
		map.put("pageSize", pageSize);
		map.put("title", title);
		return openingApplyMapper.getMerchantSize(map);
	}
	/**
	 * 获得所有通道
	 * @return
	 */
	public List<Map<Object, Object>> getChannels(){
		return openingApplyMapper.getChannels();
	}
	
	/**
	 * 获得所有通道周期
	 * @return
	 */
	public List<Map<Object, Object>> channelsT(int id){
		return openingApplyMapper.channelsT(id);
	}

	/**
	 * 申请资料数据回显
	 * 
	 * @param id
	 * @return
	 */
	public List<Map<String, String>> ReApplyFor(Integer id) {
		return openingApplyMapper.ReApplyFor(id);
	}

	/**
	 * 根据商户id获得详细信息
	 * 
	 * @param id
	 * @return
	 */
	public Map<Object, Object> getMerchant(Integer merchantId) {
		return openingApplyMapper.getMerchant(merchantId);
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
		return openingApplyMapper.getMerchantsIsNo(map);
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
		openingApplyMapper.addApply(map);
	}

	/**
	 * 添加 开通申请关联
	 */
	public void addOpeningApply(OpeningApplie openingApplie) {
		openingApplyMapper.addOpeningApply(openingApplie);

	}

	/**
	 * 获得材料名字
	 * 
	 * @param id
	 * @return
	 */
	public List<Merchant> getMaterialName(Integer id, Integer status) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("terminalsId", id);
		map.put("status", status);
		return openingApplyMapper.getMaterialName(map);
	}

	/**
	 * 获得申请表的id
	 * 
	 * @param id
	 * @return
	 */
	public int getApplyesId(Integer id) {
		return openingApplyMapper.getApplyesId(id);
	}

	/**
	 * 重新申请开通(先删除旧数据)
	 * 
	 * @param id
	 */
	public void deleteOpeningInfos(Integer id) {
		openingApplyMapper.deleteOpeningInfos(id);
	}
	
	/**
	 * 更该重新申请状态
	 * @param openingApplie
	 */
	public void updateOpeningApplyStatus(int openingAppliesId){
		
		OpeningApplie openingApplie = new OpeningApplie();
		openingApplie.setId(openingAppliesId);
		openingApplie.setStatus(OpeningApplie.STATUS_1);
		openingApplyMapper.updateOpeningApplyStatus(openingApplie);
	}
}
