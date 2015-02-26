package com.comdosoft.financial.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.comdosoft.financial.user.domain.Paging;
import com.comdosoft.financial.user.domain.zhangfu.Agent;
import com.comdosoft.financial.user.domain.zhangfu.Customer;
import com.comdosoft.financial.user.mapper.zhangfu.AgentSubMapper;

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

    // public void update(Merchant param) {
    // Date now = new Date();
    // param.setUpdatedAt(now);
    // agentSubMapper.update(param);
    // }
    //
    // public void delete(int id) {
    // agentSubMapper.delete(id);
    // }

}