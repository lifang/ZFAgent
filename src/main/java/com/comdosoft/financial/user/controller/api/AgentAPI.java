package com.comdosoft.financial.user.controller.api;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.comdosoft.financial.user.domain.Response;
import com.comdosoft.financial.user.domain.zhangfu.Customer;
import com.comdosoft.financial.user.service.AgentService;

/**
 * 我的信息 - 代理商<br>
 * <功能描述>
 *
 * @author zengguang 2015年2月7日
 *
 */
@RestController
@RequestMapping(value = "api/agents")
public class AgentAPI {

    @Resource
    private AgentService agentService;

    /**
     * 日志记录器
     */
    private static final Logger logger = Logger.getLogger(AgentAPI.class);

    @RequestMapping(value = "getOne/{customerId}", method = RequestMethod.GET)
    public Response getOne(@PathVariable int customerId) {
        Response sysResponse = null;
        try {
            sysResponse = Response.getSuccess(agentService.getOne(customerId));
        } catch (Exception e) {
            logger.error("获取代理商信息失败", e);
            sysResponse = Response.getError("获取代理商信息失败:系统异常");
        }
        return sysResponse;
    }

    @RequestMapping(value = "getUpdatePhoneDentcode/{phone}/{customerId}", method = RequestMethod.GET)
    public Response getUpdatePhoneDentcode(@PathVariable String phone, @PathVariable int customerId) {
        Response sysResponse = null;
        try {
            sysResponse = Response.getSuccess(agentService.getUpdatePhoneDentcode(customerId, phone));
        } catch (Exception e) {
            logger.error("获取代理商修改手机验证码失败", e);
            sysResponse = Response.getError("获取代理商修改手机验证码失败:系统异常");
        }
        return sysResponse;
    }

    @RequestMapping(value = "updatePhone", method = RequestMethod.POST)
    public Response updatePhone(@RequestBody Customer param) {
        Response sysResponse = null;
        try {
            Customer customer = agentService.getOneCustomer(param.getCustomerId());
            if (customer != null) {
                if (customer.getDentcode().equals(param.getDentcode())) {// 判断验证码
                    agentService.update(param);
                    sysResponse = Response.getSuccess();
                } else {
                    sysResponse = Response.getError("修改代理商手机失败:验证码不正确");
                }
            } else {
                sysResponse = Response.getError("修改代理商手机失败:代理商不存在");
            }
        } catch (Exception e) {
            logger.error("修改代理商手机失败", e);
            sysResponse = Response.getError("修改代理商手机失败:系统异常");
        }
        return sysResponse;
    }

    @RequestMapping(value = "getUpdateEmailDentcode/{email}/{customerId}", method = RequestMethod.GET)
    public Response getUpdateEmailDentcode(@PathVariable String email, @PathVariable int customerId) {
        Response sysResponse = null;
        try {
            sysResponse = Response.getSuccess(agentService.getUpdateEmailDentcode(customerId, email));
        } catch (Exception e) {
            logger.error("获取代理商修改邮箱验证码失败", e);
            sysResponse = Response.getError("获取代理商修改邮箱验证码失败:系统异常");
        }
        return sysResponse;
    }

    @RequestMapping(value = "updateEmail", method = RequestMethod.POST)
    public Response updateEmail(@RequestBody Customer param) {
        Response sysResponse = null;
        try {
            Customer customer = agentService.getOneCustomer(param.getCustomerId());
            if (customer != null) {
                if (customer.getDentcode().equals(param.getDentcode())) {// 判断验证码
                    agentService.update(param);
                    sysResponse = Response.getSuccess();
                } else {
                    sysResponse = Response.getError("修改代理商邮箱失败:验证码不正确");
                }
            } else {
                sysResponse = Response.getError("修改代理商邮箱失败:代理商不存在");
            }
        } catch (Exception e) {
            logger.error("修改代理商邮箱失败", e);
            sysResponse = Response.getError("修改代理商邮箱失败:系统异常");
        }
        return sysResponse;
    }

    @RequestMapping(value = "updatePassword", method = RequestMethod.POST)
    public Response updatePassword(@RequestBody Customer param) {
        Response sysResponse = null;
        try {
            Customer customer = agentService.getOneCustomer(param.getCustomerId());
            if (customer != null) {
                if (param.getPasswordOld().equals(customer.getPassword())) {// 判断原密码
                    agentService.update(param);
                    sysResponse = Response.getSuccess();
                } else {
                    sysResponse = Response.getError("修改代理商密码失败:原密码不正确");
                }
            } else {
                sysResponse = Response.getError("修改代理商密码失败:代理商不存在");
            }
        } catch (Exception e) {
            logger.error("修改代理商密码失败", e);
            sysResponse = Response.getError("修改代理商密码失败:系统异常");
        }
        return sysResponse;
    }

    @RequestMapping(value = "getAddressList/{customerId}", method = RequestMethod.GET)
    public Response getAddressList(@PathVariable int customerId) {
        Response sysResponse = null;
        try {
            sysResponse = Response.getSuccess(agentService.getAddressList(customerId));
        } catch (Exception e) {
            logger.error("获取代理商地址列表失败", e);
            sysResponse = Response.getError("获取代理商地址列表失败:系统异常");
        }
        return sysResponse;
    }

    @RequestMapping(value = "insertAddress", method = RequestMethod.POST)
    public Response insertAddress(@RequestBody Map<Object, Object> param) {
        Response sysResponse = null;
        try {
            agentService.insertAddress(param);
            sysResponse = Response.getSuccess();
        } catch (Exception e) {
            logger.error("新增代理商地址失败", e);
            sysResponse = Response.getError("新增代理商地址失败:系统异常");
        }
        return sysResponse;
    }

    @RequestMapping(value = "deleteAddress/{id}", method = RequestMethod.GET)
    public Response deleteAddress(@PathVariable int id) {
        Response sysResponse = null;
        try {
            agentService.deleteAddress(id);
            sysResponse = Response.getSuccess();
        } catch (Exception e) {
            logger.error("删除代理商地址失败", e);
            sysResponse = Response.getError("删除代理商地址失败:系统异常");
        }
        return sysResponse;
    }

}