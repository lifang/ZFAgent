package com.comdosoft.financial.user.controller.api;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.comdosoft.financial.user.domain.Response;
import com.comdosoft.financial.user.domain.zhangfu.Agent;
import com.comdosoft.financial.user.domain.zhangfu.Customer;
import com.comdosoft.financial.user.domain.zhangfu.Merchant;
import com.comdosoft.financial.user.service.AgentLoginService;
import com.comdosoft.financial.user.service.AgentSubService;

/**
 * 下级代理商管理<br>
 * <功能描述>
 *
 * @author zengguang 2015年2月7日
 *
 */
@RestController
@RequestMapping(value = "api/agentSub")
public class AgentSubAPI {

    @Resource
    private AgentLoginService agentLoginService;

    @Resource
    private AgentSubService agentSubService;

    /**
     * 日志记录器
     */
    private static final Logger logger = Logger.getLogger(AgentSubAPI.class);

    /**
     * 获取下级代理商列表
     * 
     * @param agentId
     */
    @RequestMapping(value = "getList/{agentId}/{page}/{rows}", method = RequestMethod.GET)
    public Response getList(@PathVariable int agentId, @PathVariable int page, @PathVariable int rows) {
        Response sysResponse = null;
        try {
            sysResponse = Response.getSuccess(agentSubService.getList(agentId, page, rows));
        } catch (Exception e) {
            logger.error("获取下级代理商列表失败", e);
            sysResponse = Response.getError("获取下级代理商列表失败:系统异常");
        }
        return sysResponse;
    }

    /**
     * 获取下级代理商信息
     * 
     * @param agentId
     */
    @RequestMapping(value = "getOne/{agentId}", method = RequestMethod.GET)
    public Response getOne(@PathVariable int agentId) {
        Response sysResponse = null;
        try {
            sysResponse = Response.getSuccess(agentSubService.getOne(agentId));
        } catch (Exception e) {
            logger.error("获取下级代理商信息失败", e);
            sysResponse = Response.getError("获取下级代理商信息失败:系统异常");
        }
        return sysResponse;
    }

    /**
     * 创建下级代理商信息
     * 
     * @param agent
     * @return
     */
    @RequestMapping(value = "insert", method = RequestMethod.POST)
    public Response insert(@RequestBody Agent agent) {
        Response sysResponse = null;
        try {
            Customer customer = new Customer();
            customer.setUsername(agent.getUsername());
            if (agentLoginService.findUname(customer) > 0) {
                return Response.getError("创建下级代理商信息失败:已注册");
            } else {
                // 查找该城市中是否有状态为正常的代理商
                customer.setStatus(Customer.STATUS_NORMAL);
                customer.setCityId(agent.getCityId());
                if (agentLoginService.judgeCityId(customer) > 0) {
                    return Response.getError("创建下级代理商信息失败:该城市已有代理商");
                } else {

                    // 设置代理商账号信息
                    customer.setPassword(agent.getPassword());
                    customer.setTypes(Customer.TYPE_AGENT);
                    customer.setStatus(Customer.STATUS_NORMAL);
                    customer.setPhone(agent.getPhone());
                    customer.setEmail(agent.getEmail());
                    customer.setName(agent.getName());

                    // 生成代理商编码
                    String maxCodeInDB = agentSubService.getCurrentAgentMaxCode(agent.getParentId());
                    if (StringUtils.isEmpty(maxCodeInDB)) {
                        agent.setCode(agent.getAgentCode() + "001");
                    } else {
                        String serialNumber = String.valueOf(Integer.parseInt(maxCodeInDB) + 1);
                        if (serialNumber.length() % 3 == 1) {
                            serialNumber = "00" + serialNumber;
                        } else if (serialNumber.length() % 3 == 2) {
                            serialNumber = "0" + serialNumber;
                        }
                        agent.setCode(serialNumber);
                    }
                    agent.setFormTypes(Agent.FROM_TYPE_2);
                    agent.setStatus(Agent.STATUS_2);
                    agent.setIsHaveProfit(Agent.IS_HAVE_PROFIT_N);
                }
            }
            agentSubService.insert(customer, agent);
            sysResponse = Response.getSuccess();
        } catch (Exception e) {
            logger.error("创建下级代理商信息失败", e);
            sysResponse = Response.getError("创建下级代理商信息失败:系统异常");
        }
        return sysResponse;
    }

    /**
     * 修改下级代理商信息
     * 
     * @param customer
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public Response update(@RequestBody Merchant param) {
        Response sysResponse = null;
        try {
            // agentSubService.update(param);
            sysResponse = Response.getSuccess();
        } catch (Exception e) {
            logger.error("修改修改下级代理商信息失败", e);
            sysResponse = Response.getError("修改修改下级代理商信息失败:系统异常");
        }
        return sysResponse;
    }

    /**
     * 删除下级代理商信息
     * 
     * @param id
     * @return
     */
    @RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
    public Response deleteAddress(@PathVariable int id) {
        Response sysResponse = null;
        try {
            // agentSubService.delete(id);
            sysResponse = Response.getSuccess();
        } catch (Exception e) {
            logger.error("删除下级代理商信息失败", e);
            sysResponse = Response.getError("删除下级代理商信息失败:系统异常");
        }
        return sysResponse;
    }

}