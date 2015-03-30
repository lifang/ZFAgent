package com.comdosoft.financial.user.mapper.zhangfu;

import java.util.List;
import java.util.Map;

import com.comdosoft.financial.user.domain.zhangfu.CsAgent;

/**
 * 用户终端管理
 * 
 * @author xianfeihu
 *
 */
public interface TerminalsMapper {

   

    /**
     * 根据用户id获得终端列表
     * 
     * @param map
     * @return
     */
    List<Map<Object, Object>> getTerminalList(Map<String, Object> map);

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
    List<Map<String, String>> channels();
    
    /**
     * 获得代理商下面的用户
     * @param customerId
     * @return
     */
    List<Map<String, Object>> getMerchants(int customerId);
    
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
    Object merchantsIsBinding(int terchantsId);

    /**
     * 给用户绑定终端号
     * @param map
     */
    void Binding(Map<Object, Object> map);
    
    /**
     * 该用户所有终端
     * @param customerId
     * @return
     */
    List<Map<String, Object>> getTerminal(int customerId);
    /**
     * 收件人信息
     * @param customerId
     * @return
     */
    List<Map<String, Object>> getAddressee(int customerId);
    
    /**
     * 添加申请售后记录
     * @param csAgent
     */
    void submitAgent(CsAgent csAgent);
    
    /**
     * 筛选终端
     * @param map
     * @return
     */
    List<Map<String, Object>> screeningTerminalNum(Map<Object, Object> map);
    
    /**
     * 批量终端号筛选终端
     * @param map
     * @return
     */
    List<Map<String, Object>> batchTerminalNum(Map<Object, Object> map);
    
    /**
     * POS机选择
     * @param customerId
     * @return
     */
    List<Map<String, Object>> screeningPosName(int customerId);
}
