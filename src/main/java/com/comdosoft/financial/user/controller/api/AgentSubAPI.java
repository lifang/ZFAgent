package com.comdosoft.financial.user.controller.api;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.comdosoft.financial.user.domain.Response;
import com.comdosoft.financial.user.domain.zhangfu.Merchant;
import com.comdosoft.financial.user.service.AgentSubService;

/**
 * 
 * 下级代理商管理<br>
 * <功能描述>
 *
 * @author zengguang 2015年2月7日
 *
 */
@RestController
@RequestMapping(value = "api/merchant")
public class AgentSubAPI {

    @Resource
    private AgentSubService agentSubService;

    /**
     * 日志记录器
     */
    private static final Logger logger = Logger.getLogger(AgentSubAPI.class);

    /**
     * 获取商户信息列表
     * 
     * @param customerId
     */
    @RequestMapping(value = "getList/{customerId}/{page}/{rows}", method = RequestMethod.GET)
    public Response getList(@PathVariable int customerId, @PathVariable int page, @PathVariable int rows) {
        Response sysResponse = null;
        try {
            sysResponse = Response.getSuccess(agentSubService.getList(customerId, page, rows));
        } catch (Exception e) {
            logger.error("获取商户信息列表失败", e);
            sysResponse = Response.getError("获取商户信息列表失败:系统异常");
        }
        return sysResponse;
    }

    /**
     * 获取商户信息
     * 
     * @param id
     */
    @RequestMapping(value = "getOne/{id}", method = RequestMethod.GET)
    public Response getOne(@PathVariable int id) {
        Response sysResponse = null;
        try {
            sysResponse = Response.getSuccess(agentSubService.getOne(id));
        } catch (Exception e) {
            logger.error("获取商户信息失败", e);
            sysResponse = Response.getError("获取商户信息失败:系统异常");
        }
        return sysResponse;
    }

    /**
     * 新增商户信息
     * 
     * @param customer
     * @return
     */
    @RequestMapping(value = "insert", method = RequestMethod.POST)
    public Response insert(@RequestBody Merchant param) {
        Response sysResponse = null;
        try {
            // agentSubService.insert(param);
            sysResponse = Response.getSuccess();
        } catch (Exception e) {
            logger.error("新增商户信息失败", e);
            sysResponse = Response.getError("新增商户信息失败:系统异常");
        }
        return sysResponse;
    }

    /**
     * 修改商户信息
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
            logger.error("修改修改商户信息失败", e);
            sysResponse = Response.getError("修改修改商户信息失败:系统异常");
        }
        return sysResponse;
    }

    /**
     * 删除商户信息
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
            logger.error("删除商户信息失败", e);
            sysResponse = Response.getError("删除商户信息失败:系统异常");
        }
        return sysResponse;
    }

}