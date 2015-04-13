package com.comdosoft.financial.user.controller.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.comdosoft.financial.user.domain.Response;
import com.comdosoft.financial.user.domain.zhangfu.CustomerAgentRelation;
import com.comdosoft.financial.user.service.UserManagementService;
import com.comdosoft.financial.user.utils.page.PageRequest;

/**
 * 
 * 用户管理<br>
 * <功能描述>
 *
 * @author xfh 2015年2月10日
 *
 */
@RestController
@RequestMapping(value = "api/user")
public class UserManagementController {
	private static final Logger logger = Logger.getLogger(UserManagementController.class);

	@Resource
	private UserManagementService userManagementService;

	/**
	 * 获得该代理商有关联的所有用户
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "query/{customerId}", method = RequestMethod.POST)
	public Response queryCustomer(@PathVariable int customerId) {
		Response sysResponse = new Response();

		Map<Object, Object> result = userManagementService.queryCustomer(customerId);
		if (result != null) {
			sysResponse = Response.getSuccess();
			sysResponse.setResult(result);
		} else {
			sysResponse.setMessage("系统异常");
			// setisDefault
		}
		return sysResponse;
	}

	/**
	 * 获得该代理商有关联的所有用户
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "getUser", method = RequestMethod.POST)
	public Response getUser(@RequestBody Map<String, Object> map) {
		try {//代理商对应用户id
			PageRequest PageRequest = new PageRequest((Integer)map.get("page"),
					(Integer)map.get("rows"));
			int offSetPage = PageRequest.getOffset();
			return Response.getSuccess(userManagementService.getUser((Integer) map.get("customerId"),
					CustomerAgentRelation.STATUS_1,
					CustomerAgentRelation.TYPES_USER_TO_AGENT,
					offSetPage,(Integer)map.get("rows")));
		} catch (Exception e) {
			logger.error("获得该代理商有关联的所有用户异常！", e);
			return Response.getError("请求失败！");
		}
	}
	/**
	 * 获得该代理商有关联的所有用户(Web)
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "getWbeUser", method = RequestMethod.POST)
	public Response getWbeUser(@RequestBody Map<String, Object> map) {
		try {//代理商对应用户id
		    String keys;
            try {
                keys = map.get("keys").toString();
            } catch (Exception e) {
                keys="";
            }
			return Response.getSuccess(userManagementService.getWebUser(
			        (Integer) map.get("agentId"),
					CustomerAgentRelation.STATUS_1,
					CustomerAgentRelation.TYPES_USER_TO_AGENT,keys));
		} catch (Exception e) {
			logger.error("获得该代理商有关联的所有用户异常！", e);
			return Response.getError("请求失败！");
		}
	}

	/**
	 * 根据ID删除与该代理商的关联
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "delectAgentUser")
	public Response delectAgentUser(@RequestBody Map<String, Object> map) {
		try {
			@SuppressWarnings("unchecked")
			List<Object> list = (List<Object>) map.get("customerArrayId");
			for (int i = 0; i < list.size(); i++) {

				userManagementService.delectAgentUser((Integer) map.get("agentId"), (Integer) list.get(i), CustomerAgentRelation.STATUS_2);
			}
			return Response.getSuccess("删除成功！");
		} catch (Exception e) {
			logger.error("根据ID删除与该代理商的关联异常！", e);
			return Response.getError("请求失败！");
		}
	}

	/**
	 * 获取用户列表
	 * 
	 * @param customerId
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "getList/{customerId}/{page}/{rows}", method = RequestMethod.POST)
	public Response getList(@PathVariable int customerId, @PathVariable int page, @PathVariable int rows) {
		Response response = null;
		try {
			response = new Response();
			Map<Object, Object> result = new HashMap<Object, Object>();
			result.put("total", userManagementService.getTerminalListTotalCount(customerId));
			result.put("list", userManagementService.getTerminalList(customerId, page, rows));
			logger.debug(result);
			response.setResult(result);
			response.setCode(Response.SUCCESS_CODE);
		} catch (Exception e) {
			logger.error("获取商户信息列表失败", e);
			response.setMessage("获取商户信息列表失败:系统异常");
		}
		return response;
	}

	/**
	 * 获得该代理商下面某个用户的相关终端列表
	 * 
	 * @param customerId
	 * @return
	 */
	@RequestMapping(value = "getTerminals")
	public Response getTerminals(@RequestBody Map<String, Object> map) {
		try {
			PageRequest PageRequest = new PageRequest((Integer) map.get("page"), (Integer) map.get("rows"));
			int offSetPage = PageRequest.getOffset();

			return Response.getSuccess(userManagementService.getTerminals((Integer) map.get("customerId"), offSetPage, (Integer) map.get("rows")));
		} catch (Exception e) {
			logger.error("获得该代理商下面某个用户的相关终端列表异常！", e);
			return Response.getError("请求失败！");
		}
	}

	/**
	 * 新创建用户
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "addCustomer", method = RequestMethod.POST)
	public Response addCustomer(@RequestBody Map<Object, Object> map) {
		try {
			return userManagementService.addCustomerOrAgents(map);
		} catch (Exception e) {
			logger.error("为用户绑定失败！", e);
			return Response.getError("请求失败！");
		}
	}
	
	/**
	 * 根据商户ID查找商户信息
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "queryMerchantInfo/{customerId}", method = RequestMethod.POST)
	public Response queryMerchantInfo(@PathVariable int customerId) {
		Response response = new Response();
		Map<String, Object> result = userManagementService.queryMerchantInfo(customerId);
		response.setResult(result);
		response.setCode(Response.SUCCESS_CODE);

		return response;
	}

}
