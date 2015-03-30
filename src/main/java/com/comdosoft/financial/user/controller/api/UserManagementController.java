package com.comdosoft.financial.user.controller.api;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.comdosoft.financial.user.domain.Response;
import com.comdosoft.financial.user.domain.zhangfu.CustomerAgentRelation;
import com.comdosoft.financial.user.service.UserManagementService;

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
	@RequestMapping(value = "getUser", method = RequestMethod.POST)
	public Response getUser(@RequestBody Map<String, Object> map) {
		try {
			return Response.getSuccess(userManagementService
					.getUser((Integer)map.get("customerId")));
		} catch (Exception e) {
			logger.error("获得该代理商有关联的所有用户异常！",e);
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
			userManagementService.delectAgentUser(
					(Integer)map.get("agentId"),
					(Integer)map.get("customerId"),
					CustomerAgentRelation.STATUS_2);
			return Response.getSuccess("删除成功！");
		} catch (Exception e) {
			logger.error("根据ID删除与该代理商的关联异常！",e);
			return Response.getError("请求失败！");
		}
	}
	
	/**
	 * 获得该代理商下面某个用户的相关终端列表
	 * @param customerId
	 * @return
	 */
	@RequestMapping(value="getTerminals")
	public Response getTerminals(@RequestBody Map<String, Object> map){
		try {
			return Response.getSuccess(userManagementService.getTerminals((Integer)map.get("customerId")));
		} catch (Exception e) {
			logger.error("获得该代理商下面某个用户的相关终端列表异常！",e);
			return Response.getError("请求失败！");
		}
	}
}
