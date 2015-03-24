package com.comdosoft.financial.user.controller.api;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.comdosoft.financial.user.domain.Response;
import com.comdosoft.financial.user.domain.query.EmpReq;
import com.comdosoft.financial.user.domain.query.MyAccountReq;
import com.comdosoft.financial.user.service.SystemSetService;
import com.comdosoft.financial.user.utils.SysUtils;
import com.comdosoft.financial.user.utils.page.Page;

/**
 * 系统设定controller
 * 
 * @author maomao
 * @since 2015-3-21
 *
 */
@RestController
@RequestMapping(value = "/api/empAccount")
public class SystemSetController {
	private static final Logger logger = Logger
			.getLogger(SystemSetController.class);
	@Resource
	private SystemSetService systemSetService;

	@RequestMapping(value = "getAllAccountlist", method = RequestMethod.POST)
	public Response getAllAccountlist(@RequestBody MyAccountReq myAccountReq) {
		Response response = null;

		response = new Response();
		Page<Object> centers = systemSetService.getAllAccountlist(myAccountReq);
		response.setResult(centers);
		response.setCode(Response.SUCCESS_CODE);
		return response;

	}

	/**
	 * 插入用户
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "addCustomer", method = RequestMethod.POST)
	public Response addCustomer(@RequestBody EmpReq req) {
		Response response = new Response();

		Map<String, Object> map = systemSetService.checkAccount(req);
		if (map != null) {
			response.setMessage("用户名重复,请选择其他用户名");
			response.setCode(Response.ERROR_CODE);
		} else {
			String password = req.getPassword();
			String md5Password = SysUtils.md5(password);
			logger.debug("原始密码: " + password + " ,加密密码: " + md5Password);
			req.setPassword(md5Password);

			// 向customers表插入数据
			systemSetService.addCustomer(req);

			Map<String, Object> result = systemSetService
					.getEmpInfoByUsername(req.getUsername());
			if (result != null) {
				int customer_id = Integer.parseInt(result.get("customer_id").toString());
				// customer_agent_relations表插入数据
				req.setCustomer_id(customer_id);
				systemSetService.insertCustomerAgentRelations(req);
			}
			
			response.setMessage("创建用户成功");
			response.setCode(Response.SUCCESS_CODE);
		}

		return response;
	}

	/**
	 * 根据员工ID获取员工信息
	 * 
	 * @param myAccountReq
	 * @return
	 */
	@RequestMapping(value = "info", method = RequestMethod.POST)
	public Response getEmpInfoFromAgent(@RequestBody EmpReq req) {
		Response response = new Response();
		int id = req.getId();
		Map<String, Object> result = systemSetService.getEmpInfoById(id);
		response.setCode(Response.SUCCESS_CODE);
		response.setResult(result);
		return response;
	}

	/**
	 * 获取代理商所有权限
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "getWholeRights", method = RequestMethod.POST)
	public Response getWholeRightsByAgentId(@RequestBody MyAccountReq req) {
		Response response = new Response();
		List<Object> results = systemSetService.getWholeRightsByAgentId(req);
		response.setResult(results);
		return response;
	}

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public Response deleteEmpInfoFromAgent(@RequestBody EmpReq req) {
		Response response = new Response();
		Map<String, Object> result = systemSetService
				.deleteEmpInfoFromAgent(req);
		response.setCode(Response.SUCCESS_CODE);
		response.setResult(result);
		return response;
	}
}
