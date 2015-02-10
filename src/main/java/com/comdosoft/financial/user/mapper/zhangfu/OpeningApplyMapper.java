package com.comdosoft.financial.user.mapper.zhangfu;



import java.util.List;
import java.util.Map;

import com.comdosoft.financial.user.domain.zhangfu.Merchant;
import com.comdosoft.financial.user.domain.zhangfu.OpeningApplie;



/**
 * 用户申请开通
 * @author xianfeihu
 *
 */
public interface OpeningApplyMapper {
	
	/**
	 * 根据用户id得到所有申请列表
	 * @param map
	 * @return
	 */
	List<Map<Object, Object>> getApplyList(Map<String, Object> map);
	
	/**
	 * 根据终端id获得该终端详情
	 * @param id
	 * @return
	 */
	Map<String, Object> getApplyDetails(Integer id);
	
	/**
	 * 获得所有商户
	 * @return
	 */
	List<Merchant> getMerchants();
	
	/**
	 * 获得所有通道
	 * @return
	 */
	List<Map<String, String>> getChannels();
	
	/**
	 * 申请资料数据回显
	 * @param id
	 * @return
	 */
	List<Map<String, String>> ReApplyFor(Integer id);
	
	/**
	 * 根据商户id获得商户详情信息
	 * @param id
	 * @return
	 */
	Merchant getMerchant(Integer id);
	
	/**
	 * 添加开通信息
	 * @param map
	 */
	void addApply(Map<String, String> map);
	
	/**
	 * 添加开通关联信息
	 * @param map
	 */
	void addOpeningApply(OpeningApplie openingApplie);
	
	/**
	 * 获得材料名字
	 * @param id
	 * @return
	 */
	List<Merchant> getMaterialName(Map<Object, Object> map);
	
	/**
	 * 获得申请表的id
	 * @param id
	 * @return
	 */
	int getApplyesId(Integer id);
	
	/**
	 * 重新申请开通(先删除旧数据)
	 * @param id
	 */
	void deleteOpeningInfos(Integer id);
}
