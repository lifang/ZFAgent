package com.comdosoft.financial.user.controller.api;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.comdosoft.financial.user.domain.Response;
import com.comdosoft.financial.user.domain.zhangfu.CustomerAgentRelation;
import com.comdosoft.financial.user.service.UserManagementService;

/**
 * 
 * 终端管理<br>
 * <功能描述>
 *
 * @author xfh 2015年2月10日
 *
 */
@RestController
@RequestMapping(value = "api/terminal")
public class UserManagementController {
	//private static final Logger logger = LoggerFactory.getLogger(UserManagementController.class);

	@Resource
	private UserManagementService userManagementService;

	/**
	 * 获得该代理商有关联的所有用户
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "getUser/{customerId}", method = RequestMethod.GET)
	public Response getUser(@PathVariable int customerId) {
		try {
			return Response.getSuccess(userManagementService
					.getUser(customerId));
		} catch (Exception e) {
			return Response.getError("请求失败！");
		}
	}

	/**
	 * 根据ID删除与该代理商的关联
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "delectAgentUser/{agentId}/{customerId}")
	public Response delectAgentUser(@PathVariable("agentId") Integer agentId,@PathVariable("customerId") Integer customerId) {
		try {
			userManagementService.delectAgentUser(agentId,customerId,CustomerAgentRelation.STATUS_2);
			return Response.getSuccess("删除成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getError("请求失败！");
		}
	}
	
	/**
	 * 获得该代理商下面某个用户的相关终端列表
	 * @param customerId
	 * @return
	 */
	@RequestMapping(value="getTerminals/{customerId}")
	public Response getTerminals(@PathVariable Integer customerId){
		try {
			return Response.getSuccess(userManagementService.getTerminals(customerId));
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getError("请求失败！");
		}
	}
}
