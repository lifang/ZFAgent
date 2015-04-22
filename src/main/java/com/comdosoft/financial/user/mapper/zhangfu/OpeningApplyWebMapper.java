package com.comdosoft.financial.user.mapper.zhangfu;



import java.util.List;
import java.util.Map;

import com.comdosoft.financial.user.domain.zhangfu.Merchant;
import com.comdosoft.financial.user.domain.zhangfu.OpeningApplie;
import com.comdosoft.financial.user.domain.zhangfu.OpeningRequirement;



/**
 * 用户申请开通
 * @author xianfeihu
 *
 */
public interface OpeningApplyWebMapper {
	
	/**
	 * 根据用户id得到所有申请列表
	 * @param map
	 * @return
	 */
	List<Map<Object, Object>> getApplyList(Map<String, Object> map);
	
	/**
	 * 根据用户id得到所有申请总记录数
	 * @param map
	 * @return
	 */
	int getApplyListSize(Map<String, Object> map);
	
	
	/**
	 * 根据终端号模糊查询相关终端
	 * @param map
	 * @return
	 */
	List<Map<Object, Object>> searchApplyList(Map<String, Object> map);
	
	/**
	 * 根据终端号模糊查询相关终端总记录数
	 * @param map
	 * @return
	 */
	int searchApplyListSize(Map<String, Object> map);
	
	/**
	 * 获得已有开通申请基本申请信息
	 * @param id
	 * @return
	 */
	Map<String, Object> getOppinfo(OpeningApplie openingApplie);
	
	 /**
     * <!-城市级联(省) -->
     * 
     * @param 
     * @return
     */
    List<Map<Object, Object>> getCities();
	
	/**
	 * 申请开通时判断商户是否存在
	 * @return
	 */
	Map<Object, Object> getMerchantsIsNo(Map<Object, Object> map);
	
	/**
	 * 进入申请开通时判断该终端是否绑定
	 * @return
	 */
	int isopen(int id);
	
	/**
	 * 根据终端id获得该终端详情
	 * @param id
	 * @return
	 */
	Map<String, Object> getApplyDetails(Integer id);
	
	/**
	 * 查看该终端材料等级个数
	 * @param id
	 * @return
	 */
	List<OpeningRequirement> getMaterialLevel(int terminalsId);
	
	
	/**
	 * 修改开通关联基本信息
	 * @param openingApplie
	 */
	void updateApply(OpeningApplie openingApplie);
	
	/**
	 * 获得所有商户
	 * @return
	 */
	List<Merchant> getMerchants(int terminalId);
	
	/**
	 * 获得所有商户
	 * @return
	 */
	int getMerchantSize(Map<String, Object> map);
	/**
	 * 添加商户
	 * @return
	 */
	void addMerchan(Merchant merchant);
	
	 /**
     * 获得该终端信息
     * 
     * @param terminalId
     * @return
     */
    int isopenMessage(int terminalId);
	
	/**
	 * 获得所有通道周期
	 * @return
	 */
	List<Map<Object, Object>> channelsT(int id);
	
	/**
	 * 获得所有通道
	 * @return
	 */
	List<Map<Object, Object>> getChannels();
	
	/**
	 * 申请资料数据回显
	 * @param id
	 * @return
	 */
	List<Map<Object, Object>> ReApplyFor(Integer id);
	
	/**
	 * 根据商户id获得商户详情信息
	 * @param id
	 * @return
	 */
	Map<Object, Object> getMerchant(int id);
	
	/**
	 * 添加开通信息
	 * @param map
	 */
	void addApply(Map<String, Object> map);
	
	/**
     * 判断该终端是否开通
     * 
     * @param map
     * @return
     */
    int judgeOpen(int terminalId);
	
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
	List<Map<Object, Object>> getMaterialName(Map<Object, Object> map);
	
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
	
	/**
	 * 终端关联商户和通道周期时间
	 * @param id
	 */
	void updateterminal(Map<Object, Object> map);
	
	/**
	 * 更该重新申请状态
	 * @param openingApplie
	 */
	void updateOpeningApplyStatus(OpeningApplie openingApplie);
}
