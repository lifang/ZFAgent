package com.comdosoft.financial.user.mapper.zhangfu;

import java.util.List;
import java.util.Map;

import com.comdosoft.financial.user.domain.zhangfu.CsAgent;
import com.comdosoft.financial.user.domain.zhangfu.CsCancel;
import com.comdosoft.financial.user.domain.zhangfu.Customer;
import com.comdosoft.financial.user.domain.zhangfu.CustomerAddress;
import com.comdosoft.financial.user.domain.zhangfu.OpeningApplie;

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
     * 查看该终端号是否存在
     * @param terminalsNum
     * @return
     */
    Object getTerminalsNum(String terminalsNum);
    
    /**
     * 查看该终端号是否已经绑定
     * @param terminalsNum
     * @return
     */
    int numIsBinding(String terminalsNum);
    
    /**
     * 查看该用户是否已有绑定终端
     * @param erchantsId
     * @return
     */
    int merchantsIsBinding(int merchantsId);
    
    /**
     * 给用户绑定终端号
     * @param map
     */
    void Binding(Map<Object, Object> map);
    
    /**
     * 判断用户是否存在
     * @param map
     */
    int findUname(Map<Object, Object> map);
    
    /**
     * 添加新用户
     * @param map
     */
    void addUser(Customer customer);
    
    /**
     * 检查终端号是否存在
     * @param map
     */
    int checkTerminalCode(String str);
    
    /**
     * 添加申请售后记录
     * @param csAgent
     */
    void submitAgent(CsAgent csAgent);
    
    /**
     * 收件人信息
     * @param customerId
     * @return
     */
    List<Map<String, Object>> getAddressee(int customerId);
    
    /**
     * <!-添加联系地址 -->
     * 
     * @param 
     * @return
     */
    void addCostometAddress(CustomerAddress customerAddress);
    
    /**
     * 物流信息
     * 
     * @param 
     * @return
     */
    void addCsAgentMark(Map<Object, Object> map);
    
    /**
     * 获得终端详情
     * 
     * @param id
     * @return
     */
    Map<String, String> getApplyDetails(Integer id);
    
    /**
     * 获得该终端交易类型
     * 
     * @param id
     * @return
     */
    List<Map<String, String>> getRate(Integer id);
    
    /**
     * 获得租赁信息
     * 
     * @param id
     * @return
     */
    Map<String, String> getTenancy(Integer id);
    
    /**
	 * 获得已有开通申请基本申请信息
	 * @param id
	 * @return
	 */
	Map<String, Object> getOppinfo(OpeningApplie openingApplie);
	
	 /**
     * 获得模板
     * 
     * @param id
     * @return
     */
    List<Map<Object, Object>> getModule(Map<String, Object> map);
    
    /**
     * 提交退还申请
     * 
     * @param map
     * @return
     */
    void subRentalReturn(CsCancel csCancel);
    
    /**
     * 查看注销申请是否有未处理中
     * 
     * @param map
     * @return
     */
    int JudgeRentalReturnStatus(Map<String, Object> map);
    
    /**
     * 申请更新添加
     * 
     * @param map
     * @return
     */
    void subToUpdate(Map<Object, Object> map);
    
    /**
     * 查看跟新申请是否有未处理中
     * 
     * @param map
     * @return
     */
    int JudgeUpdateStatus(Map<String, Object> map);

    
    /**
     * <!--用户收货地址 -->
     * 
     * @param customerId
     * @return
     */
    List<Map<Object, Object>> getCustomerAddress(int customerId);
    
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
    List<Map<Object, Object>> getOpeningDetails(Integer id);

    /**
     * 支付通道列表
     * 
     * @return
     */
    List<Map<Object, Object>> channels();

    /**
     * POS找回密码
     * 
     * @param id
     * @return
     */
    String findPassword(Integer id);

}
