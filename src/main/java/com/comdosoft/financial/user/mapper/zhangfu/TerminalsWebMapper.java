package com.comdosoft.financial.user.mapper.zhangfu;

import java.util.List;
import java.util.Map;

import com.comdosoft.financial.user.domain.zhangfu.CsCancel;
import com.comdosoft.financial.user.domain.zhangfu.CsReceiverAddress;
import com.comdosoft.financial.user.domain.zhangfu.CustomerAddress;
import com.comdosoft.financial.user.domain.zhangfu.Merchant;

/**
 * 用户终端管理
 * 
 * @author xianfeihu
 *
 */
public interface TerminalsWebMapper {
	
	/**
     * 根据用户id获得终端列表
     * 
     * @param map
     * @return
     */
    List<Map<Object, Object>> getTerminalList(Map<String, Object> map);
	
	/**
     * 根据用户id获得终端列总记录数
     * 
     * @param map
     * @return
     */
    int getTerminalListPage(Map<String, Object> map);
	
    /**
     * 搜索所有用户
     * 
     * @param map
     * @return
     */
    List<Map<Object, Object>> searchUser(Map<Object, Object> map);
	
	
	
	
	
	
	
    
	
	
	
	
	
	

   

    /**
     * <!-- 查询交易流水用 -->
     * 
     * @param customerId
     * @return
     */
    List<Map<Object, Object>> getTerminals(int customerId);
    
    /**
     * <!--用户收货地址 -->
     * 
     * @param customerId
     * @return
     */
    List<Map<Object, Object>> getCustomerAddress(int customerId);
    
    /**
     * <!-城市级联(省) -->
     * 
     * @param 
     * @return
     */
    List<Map<Object, Object>> getCities();
    
    /**
     * <!-城市级联(市) -->
     * 
     * @param 
     * @return
     */
    List<Map<Object, Object>> getShiCities(int parentId);
    
    /**
     * <!-添加联系地址 -->
     * 
     * @param 
     * @return
     */
    void addCostometAddress(CustomerAddress customerAddress);
    
    /**
     * 根据通道id获得周期
     * 
     * @param map
     * @return
     */
    List<Map<Object, Object>> channelsT(int id);
    
    /**
     * 判断该终端是否开通
     * 
     * @param map
     * @return
     */
    int judgeOpen(int terminalId);
    
    
    
    /**
     * 获得终端状态
     * 
     * @param map
     * @return
     */
    List<Map<Object, Object>> getTerminStatus();
    
    
    
    /**
     * 查看换货申请是否有未处理中
     * 
     * @param map
     * @return
     */
    int JudgeChangStatus(Map<String, Object> map);
    
    /**
     * 查看跟新申请是否有未处理中
     * 
     * @param map
     * @return
     */
    int JudgeUpdateStatus(Map<String, Object> map);
    
    /**
     * 查看注销申请是否有未处理中
     * 
     * @param map
     * @return
     */
    int JudgeRentalReturnStatus(Map<String, Object> map);
    
    /**
     * 查看维修申请是否有未处理中
     * 
     * @param map
     * @return
     */
    int JudgeRepair(Map<String, Object> map);
    
    /**
     * 查看退货申请是否有未处理中
     * 
     * @param map
     * @return
     */
    int JudgeReturn(Map<String, Object> map);
    
    /**
     * 查看租赁退还申请是否有未处理中
     * 
     * @param map
     * @return
     */
    int JudgeLeaseReturn(Map<String, Object> map);
    
    /**
     * 申请维修添加地址
     * 
     * @param map
     * @return
     */
    void subRepairAddress(CsReceiverAddress csReceiverAddress);
    
    /**
     * 申请维修添加
     * 
     * @param map
     * @return
     */
    void subRepair(Map<Object, Object> map);
    
    /**
     * 申请更新添加
     * 
     * @param map
     * @return
     */
    void subToUpdate(Map<Object, Object> map);
    
    /**
     * 申请退货添加
     * 
     * @param map
     * @return
     */
    void subReturn(Map<Object, Object> map);
    
    /**
     * 申请换货添加
     * 
     * @param map
     * @return
     */
    void subChange(Map<Object, Object> map);
    
    
    /**
     * 提交退还申请
     * 
     * @param map
     * @return
     */
    void subRentalReturn(CsCancel csCancel);
    
    
    /**
     * 提交注销
     * 
     * @param map
     * @return
     */
    void subLeaseReturn(Map<Object, Object> map);
    
    /**
     * 获得终端详情
     * 
     * @param id
     * @return
     */
    Map<String, String> getApplyDetails(Integer id);
    
    /**
     * 获得租赁信息
     * 
     * @param id
     * @return
     */
    Map<String, String> getTenancy(Integer id);
    
    /**
     * 获得该终端交易类型
     * 
     * @param id
     * @return
     */
    List<Map<String, String>> getRate(Integer id);

    /**
     * 获得追踪记录
     * 
     * @param id
     * @return
     */
    List<Map<String, String>> getTrackRecord(Integer id);

    /**
     * 开通详情
     * 
     * @param id
     * @return
     */
    List<Map<String, String>> getOpeningDetails(Integer id);

    /**
     * 支付通道列表
     * 
     * @return
     */
    List<Map<Object, Object>> channels();

    /**
     * 判断该终端号是否存在
     * 
     * @param id
     * @return
     */
    int isExistence(String serialNum);

    /**
     * 判断商户名是否存在
     * 
     * @param id
     * @return
     */
    Map<Object, Object> isMerchantName(String title);

    /**
     * 添加商户
     * 
     * @param merchant
     */
    void addMerchants(Merchant merchant);

    /**
     * 添加终端
     * 
     * @param map
     */
    void addTerminal(Map<String, String> map);

    /**
     * POS找回密码
     * 
     * @param id
     * @return
     */
    String findPassword(Integer id);

    /**
     * author jwb
     * 根据手机获取终端
     * @param paramMap
     * @return
     */
    List<Map<String, Object>> getTerminalListByPhone(Map<String, Object> paramMap);

    /**
     * author jwb
     * 根据终端获取终端业务开通信息
     * @param id
     * @return
     */
    List<Map<String, Object>> getTerminalOpenStatus(int id);
    
    /**
     * 获得模板
     * 
     * @param id
     * @return
     */
    List<Map<String, Object>> getModule(Map<String, Object> map);
    
}
