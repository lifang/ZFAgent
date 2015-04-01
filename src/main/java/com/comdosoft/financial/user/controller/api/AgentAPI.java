package com.comdosoft.financial.user.controller.api;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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

	@RequestMapping(value = "getOne", method = RequestMethod.POST)
	public Response getOne(@RequestBody Customer param) {
		Response sysResponse = null;
		try {
			sysResponse = Response.getSuccess(agentService.getOne(param));
		} catch (Exception e) {
			logger.error("获取代理商信息失败", e);
			sysResponse = Response.getError("获取代理商信息失败:系统异常");
		}
		return sysResponse;
	}

	@RequestMapping(value = "getUpdatePhoneDentcode", method = RequestMethod.POST)
	public Response getUpdatePhoneDentcode(@RequestBody Customer param) {
		Response sysResponse = null;
		try {
			if (null == param.getPhone()) {
				return Response.getError("请传入手机号");
			}
			if (0 == param.getCustomerId()) {
				return Response.getError("请传入用户id");
			}
			sysResponse = Response.getSuccess(agentService.getUpdatePhoneDentcode(param.getCustomerId(), param.getPhone()));
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
            Customer customer = agentService.getOneCustomer(param);
            if (customer != null) {
                if (customer.getDentcode().equals(param.getDentcode())) {// 判断验证码
                    agentService.update(param,3);
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

    @RequestMapping(value = "getUpdateEmailDentcode", method = RequestMethod.POST)
    public Response getUpdateEmailDentcode(@RequestBody Customer param) {
        Response sysResponse = null;
        try {
            sysResponse = Response.getSuccess(agentService.getUpdateEmailDentcode(param.getCustomerId(),param.getEmail()));
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
            Customer customer = agentService.getOneCustomer(param);
            if (customer != null) {
                if (customer.getDentcode().equals(param.getDentcode())) {// 判断验证码
                    agentService.update(param,2);
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
            Customer customer = agentService.getOneCustomer(param);
            if (customer != null) {
                if (param.getPasswordOld().equals(customer.getPassword())) {// 判断原密码
                    agentService.update(param,1);//更新密码
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

	@RequestMapping(value = "getAddressList", method = RequestMethod.POST)
	public Response getAddressList(@RequestBody Customer param) {
		Response sysResponse = null;
		try {
			sysResponse = Response.getSuccess(agentService.getAddressList(param));
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

	/**
	 * 商户列表显示
	 * 
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "queryCommercials", method = RequestMethod.POST)
	public Response queryCommercials(@RequestBody Map<Object, Object> param) {
		Response response = null;
		try {
			Map<String, Object> result = agentService.getCommercialTenantCount(param);
			result.put("list", agentService.getCommercialTenantList(param));
			response = Response.getSuccess();
			response.setResult(result);
		} catch (Exception e) {
			response = Response.getError("获取商户列表失败:系统异常");
		}

		return response;
	}

	/**
	 * 删除单个商户
	 * 
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "deleteCommercialOne", method = RequestMethod.POST)
	public Response deleteCommercialOne(@RequestBody Map<Object, Object> param) {
		Response response = null;
		try {
			agentService.deleteCommercial(param);
			response = Response.getSuccess();
		} catch (Exception e) {
			response = Response.getError("删除商户列表失败:系统异常");
		}

		return response;
	}

	/**
	 * 获取单个商户的终端状态
	 * 
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "getTerminalState", method = RequestMethod.POST)
	public Response getOneCommercialTerminalList(@RequestBody Map<Object, Object> param) {
		Response response = null;
		List<Map<String, Object>> result = agentService.getOneCommercialTerminalList(param);
		if (result != null && !result.isEmpty()) {
			response = Response.getError("您不能删除用户，该用户还有终端在使用中，请先注销终端，再删除用户");
		} else {
			response = Response.getSuccess();
		}

		return response;
	}


    @RequestMapping(value = "deleteAddress", method = RequestMethod.POST)
    public Response deleteAddress(@RequestBody Customer param) {
        Response sysResponse = null;
        try {
            agentService.deleteAddress(param);
            sysResponse = Response.getSuccess();
        } catch (Exception e) {
            logger.error("删除代理商地址失败", e);
            sysResponse = Response.getError("删除代理商地址失败:系统异常");
        }
        return sysResponse;
    }
    
    @RequestMapping(value = "query/{customer_id}", method = RequestMethod.POST)
    public Response queryAddress(@PathVariable int customer_id) {
        Response sysResponse = new Response();
      
        Map<Object, Object> result= agentService.queryAgent(customer_id);
        	  if(result!=null)
        	  {
        		   sysResponse = Response.getSuccess();
                   sysResponse.setResult(result);
        	  }
        	  else{
        		  sysResponse.setMessage("系统异常");
        		  //setisDefault
        	  }
        return sysResponse;
    }
    
    @RequestMapping(value = "updatePhoneNumber", method = RequestMethod.POST)
    public Response updatePhoneNumber(@RequestBody Customer param) {
        Response sysResponse = null;
        try {
            agentService.updatePhoneNumber(param);
            sysResponse = Response.getSuccess();
        } catch (Exception e) {
            logger.error("更新代理商电话失败", e);
            sysResponse = Response.getError("更新代理商电话失败:系统异常");
        }
        return sysResponse;
    }
    
    @RequestMapping(value = "updateEmailAddr", method = RequestMethod.POST)
    public Response updateEmailAddr(@RequestBody Customer param) {
        Response sysResponse = null;
        try {
            agentService.updateEmailAddr(param);
            sysResponse = Response.getSuccess();
        } catch (Exception e) {
            logger.error("更新代理商邮件失败", e);
            sysResponse = Response.getError("更新代理商邮件失败:系统异常");
        }
        return sysResponse;
    }

}
