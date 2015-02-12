package com.comdosoft.financial.user.controller.api;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.comdosoft.financial.user.domain.Response;
import com.comdosoft.financial.user.domain.zhangfu.CsAgent;
import com.comdosoft.financial.user.domain.zhangfu.Merchant;
import com.comdosoft.financial.user.domain.zhangfu.Terminal;
import com.comdosoft.financial.user.service.TerminalsService;
import com.comdosoft.financial.user.service.UserManagementService;
import com.comdosoft.financial.user.utils.SysUtils;
import com.comdosoft.financial.user.utils.page.PageRequest;

/**
 * 
 * 终端管理<br>
 * <功能描述>
 *
 * @author xfh 2015年2月10日
 *
 */
@RestController
@RequestMapping(value = "/api/terminal")
public class UserManagementController {
	 private static final Logger logger = LoggerFactory.getLogger(UserManagementController.class);
	
	@Resource
	private UserManagementService userManagementService;
	
	@Resource
	private TerminalsService terminalsService;
	
	@Value("${passPath}")
	private String passPath;

	
	/**
	 * 获得该代理商有关联的所有用户
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "getUser/{customerId}", method = RequestMethod.GET)
	public Response getUser(
			@PathVariable("customersId") Integer customersId
			) {
		try {
			return Response.getSuccess(userManagementService.getUser(customersId));
		} catch (Exception e) {
			return Response.getError("请求失败！");
		}
	}
	
	/**
	 * 根据ID删除与该代理商的关联
	 * @param id
	 * @return
	 */
	@RequestMapping(value="delectAgentUser/{id}")
	public Response delectAgentUser(@PathVariable("id") Integer id){
		try{
			userManagementService.delectAgentUser(id);
			return Response.getSuccess("删除成功！");
		}catch(Exception e){
			return Response.getError("请求失败！");
		}
	}
	
	
}
