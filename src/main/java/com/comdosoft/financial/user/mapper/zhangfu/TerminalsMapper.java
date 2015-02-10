package com.comdosoft.financial.user.mapper.zhangfu;

import java.util.List;
import java.util.Map;

import com.comdosoft.financial.user.domain.zhangfu.Merchant;

/**
 * 用户终端管理
 * 
 * @author xianfeihu
 *
 */
public interface TerminalsMapper {

    /**
     * <!-- 查询交易流水用 -->
     * 
     * @param customerId
     * @return
     */
    List<Map<Object, Object>> getTerminals(int customerId);

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
    int isMerchantName(String title);

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
}
