package com.comdosoft.financial.user.service;

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
	 * 获得终端详情
	 * 
	 * @param id
	 * @return
	 */
	public Map<String, Object> getApplyDetails(Integer id) {
		return openingApplyMapper.getApplyDetails(id);
	}

	/**
	 * 获得所有商户列表
	 * 
	 * @return
	 */
	public List<Merchant> getMerchants() {
		return openingApplyMapper.getMerchants();
	}
	
	/**
	 * 获得所有通道
	 * @return
	 */
	public List<Map<String, String>> getChannels(){
		return openingApplyMapper.getChannels();
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
	public Merchant getMerchant(Integer merchantId) {
		return openingApplyMapper.getMerchant(merchantId);
	}

	/**
	 * 添加开通申请资料
	 * 
	 * @param map
	 */
	public void addApply(String key, String value, String openingAppliesId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("key", key);
		map.put("value", value);
		map.put("openingAppliesId", openingAppliesId);
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
}
