package com.comdosoft.financial.user.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.comdosoft.financial.user.domain.Paging;
import com.comdosoft.financial.user.domain.zhangfu.Agent;
import com.comdosoft.financial.user.domain.zhangfu.AgentProfitSetting;
import com.comdosoft.financial.user.domain.zhangfu.Customer;
import com.comdosoft.financial.user.domain.zhangfu.SysConfig;
import com.comdosoft.financial.user.mapper.zhangfu.AgentSubMapper;
import com.comdosoft.financial.user.mapper.zhangfu.SysconfigMapper;

/**
 * 代理商 - 业务层<br>
 *
 * @author Java-007 2015年2月7日
 *
 */
@Service
public class AgentSubService {

    @Resource
    private AgentSubMapper agentSubMapper;

    @Resource
    private SysconfigMapper sysconfigMapper;

    public List<Map<Object, Object>> getList(int parentAgentId, int page, int rows) {
        Map<Object, Object> query = new HashMap<Object, Object>();
        query.put("parentAgentId", parentAgentId);
        Paging paging = new Paging(page, rows);
        query.put("offset", paging.getOffset());
        query.put("rows", paging.getRows());
        return agentSubMapper.getList(query);
    }

    public Map<Object, Object> getOne(int agentId) {
        return agentSubMapper.getOne(agentId);
    }

    @Transactional(value = "transactionManager-zhangfu")
    public void insert(Customer customer, Agent agent) {
        agentSubMapper.insertCustomer(customer);
        agent.setCustomerId(customer.getId());
        agentSubMapper.insertAgent(agent);
    }

    public String getCurrentAgentMaxCode(Integer parentId) {
        return agentSubMapper.getCurrentAgentMaxCode(parentId);
    }

    public void setDefaultProfit(Map<Object, Object> param) {
        agentSubMapper.setDefaultProfit(param);
    }

    public void openDefaultProfit(Map<Object, Object> param) {
        agentSubMapper.openDefaultProfit(param);
    }

    public List<Map<Object, Object>> getProfits(int agentId) {
        return agentSubMapper.getProfits(agentId);
    }

    public void updateProfit(Map<Object, Object> param) {
        agentSubMapper.updateProfit(param);
    }

    public void deleteProfits(AgentProfitSetting param) {
        agentSubMapper.deleteProfits(param);
    }

    public List<Map<Object, Object>> getPayChannels() {
        return agentSubMapper.getPayChannels();
    }

    public void insertProfits(Map<Object, Object> param) {
        int agentId = (int) param.get("agentId");
        int payChannelId = (int) param.get("payChannelId");
        List<Map<Object, Object>> list = agentSubMapper.getPayChannelSupportTradeTypes(payChannelId);
        Map<String, Object> config = sysconfigMapper.getSysConfig(SysConfig.PARAMNAME_TRADERECORDDEFAULTPROFIT);
        int sysDefaultProfit = Integer.parseInt((String) config.get("param_value"));
        AgentProfitSetting agentProfitSetting = null;
        List<AgentProfitSetting> agentProfitSettings = new ArrayList<AgentProfitSetting>();
        for (Map<Object, Object> map : list) {
            agentProfitSetting = new AgentProfitSetting();
            agentProfitSetting.setAgentId(agentId);
            agentProfitSetting.setPayChannelId(payChannelId);
            agentProfitSetting.setTradeType((int) map.get("trade_type"));
            agentProfitSetting.setPercent(sysDefaultProfit);
            agentProfitSettings.add(agentProfitSetting);
        }
        if (!CollectionUtils.isEmpty(agentProfitSettings)) {
            agentSubMapper.insertProfitsBatch(agentProfitSettings);
        }
    }
}