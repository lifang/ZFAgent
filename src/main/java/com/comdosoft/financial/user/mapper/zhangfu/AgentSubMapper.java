package com.comdosoft.financial.user.mapper.zhangfu;

import java.util.List;
import java.util.Map;

import com.comdosoft.financial.user.domain.zhangfu.Agent;
import com.comdosoft.financial.user.domain.zhangfu.AgentProfitSetting;
import com.comdosoft.financial.user.domain.zhangfu.Customer;

/**
 * 下级代理商 - 数据层
 * 
 * @author
 *
 */
public interface AgentSubMapper {

    List<Map<Object, Object>> getList(Map<Object, Object> query);

    Map<Object, Object> getOne(int agentId);

    String getCurrentAgentMaxCode(Integer parentId);

    void insertCustomer(Customer param);

    void insertAgent(Agent param);

    void setDefaultProfit(Map<Object, Object> param);

    void openDefaultProfit(Map<Object, Object> param);

    List<Map<Object, Object>> getProfits(int agentId);

    void updateProfit(Map<Object, Object> param);

    void deleteProfits(AgentProfitSetting param);

    List<Map<Object, Object>> getPayChannels();

    List<Map<Object, Object>> getPayChannelSupportTradeTypes(int payChannelId);

    void insertProfitsBatch(List<AgentProfitSetting> param);

}