package com.comdosoft.financial.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.comdosoft.financial.user.domain.zhangfu.CsAgent;
import com.comdosoft.financial.user.mapper.zhangfu.OpeningApplyMapper;
import com.comdosoft.financial.user.mapper.zhangfu.TerminalsMapper;

@Service
public class TerminalsService {
	@Resource
	private OpeningApplyMapper openingApplyMapper;

	@Resource
	private TerminalsMapper terminalsMapper;
	/**
	 * 获得终端列表
	 * 
	 * @param id
	 * @param offSetPage
	 * @param pageSize
	 * @return
	 */
	public List<Map<Object, Object>> getTerminalList(Integer id,
			Integer offSetPage, Integer pageSize,Integer status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("offSetPage", offSetPage);
		map.put("pageSize", pageSize);
		map.put("status", status);
		return terminalsMapper.getTerminalList(map);
	}

	/**
	 * 获得终端详情
	 * @param id
	 * @return
	 */
	public Map<String, String> getApplyDetails(Integer id){
		return terminalsMapper.getApplyDetails(id);
	}
	
	/**
	 * 获得该终端交易类型详情
	 * @param id
	 * @return
	 */
	public List<Map<String, String>> getRate(Integer id){
		return terminalsMapper.getRate(id);
	}

	/**
	 * 获得追踪记录
	 * @param id
	 * @return
	 */
	public List<Map<String, String>> getTrackRecord(Integer id){
		return terminalsMapper.getTrackRecord(id);
	}
	
	/**
	 * 开通详情
	 * @param id
	 * @return
	 */
	public List<Map<String, String>> getOpeningDetails(Integer id){
		return terminalsMapper.getOpeningDetails(id);
	}
	
	/**
	 * 支付通道列表
	 * @return
	 */
	public List<Map<String, String>> getChannels(){
		return terminalsMapper.channels();
	}
	

	/**
	 * 获得代理商下面的商户
	 * @param customerId
	 * @return
	 */
	public  List<Map<String, Object>> getMerchants(int customerId){
		return terminalsMapper.getMerchants(customerId);
	}
	
	/**
	 * 查看该终端号是否存在
	 * @param terminalsNum
	 * @return
	 */
	public Object getTerminalsNum(String terminalsNum){
		return terminalsMapper.getTerminalsNum(terminalsNum);
	}
	
	/**
	 * 查看该终端号是否已经绑定
	 * @param terminalsNum
	 * @return
	 */
	public int numIsBinding(String terminalsNum){
		return terminalsMapper.numIsBinding(terminalsNum);
	}
	
	/**
	 * 查看该用户是否已有绑定终端
	 * @param erchantsId
	 * @return
	 */
	public Object merchantsIsBinding(int erchantsId){
		return terminalsMapper.merchantsIsBinding(erchantsId);
	}
	
	/**
	 * 给用户绑定终端号
	 * @param map
	 */
	public void Binding(Map<Object, Object> map){
		terminalsMapper.Binding(map);
	}
	
	/**
	 * 该用户所有终端
	 * @param customerId
	 * @return
	 */
	public List<Map<String, Object>> getTerminal(int customerId){
		return terminalsMapper.getTerminal(customerId);
	}
	
	/**
	 * 收件人信息
	 * @param customerId
	 * @return
	 */
	public  List<Map<String, Object>> getAddressee(int customerId){
		return terminalsMapper.getAddressee(customerId);
	}
	
	/**
	 * 添加申请售后记录
	 * @param csAgent
	 */
	public  void submitAgent(CsAgent csAgent){
		terminalsMapper.submitAgent(csAgent);
	}
	
	/**
	 * 筛选终端
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> screeningTerminalNum(Map<Object, Object> map){
		return terminalsMapper.screeningTerminalNum(map);
	}
	
	/**
	 * POS机选择
	 * @param customerId
	 * @return
	 */
	public  List<Map<String, Object>> screeningPosName(int customerId){
		return terminalsMapper.screeningPosName(customerId);
	}
}
