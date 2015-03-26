package com.comdosoft.financial.user.controller.api;

import java.util.Date;
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
import com.comdosoft.financial.user.domain.query.EmpReq;
import com.comdosoft.financial.user.domain.query.MyAccountReq;
import com.comdosoft.financial.user.domain.zhangfu.Customer;
import com.comdosoft.financial.user.domain.zhangfu.CustomerAgentRelation;
import com.comdosoft.financial.user.domain.zhangfu.CustomerRoleRelation;
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
@RequestMapping(value = "api/account")
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

		String username = req.getUsername().trim();
		Map<String, Object> map = systemSetService
				.getEmpInfoByUsername(username);
		if (map != null) {
			response.setCode(Response.ERROR_CODE);
			response.setMessage("用户名重复");
		} else {
			Date d = new Date();
			Customer c = new Customer();
			c.setName(req.getName());
			c.setUsername(username);
			c.setPassword(SysUtils.string2MD5(req.getPassword()));
			c.setStatus(Customer.STATUS_NORMAL);// 正常
			c.setAccountType(Customer.TYPE_CUSTOMER);// 1 普通用户/商户
			c.setCreatedAt(d);
			systemSetService.insertCustomer(c);

			map = systemSetService.getEmpInfoByUsername(username);
			if (map != null) {
				CustomerAgentRelation ca = new CustomerAgentRelation();
				logger.debug(req.getAgent_id());
				ca.setAgentId(req.getAgent_id());
				int customerId = Integer.parseInt(map.get("id").toString());
				ca.setCustomerId(customerId);
				ca.setStatus(CustomerAgentRelation.STATUS_2);
				ca.setTypes(1);
				ca.setCreatedAt(d);
				systemSetService.insertCustomerAgentRelations(ca);

				CustomerRoleRelation cr = new CustomerRoleRelation();
				cr.setCreatedAt(d);
				cr.setCustomerId(customerId);
				String rights = req.getRights();
				if (rights != null && "".equals(rights)) {
					String[] arr = rights.split(",");
					if (arr != null && arr.length > 0) {
						for (int i = 0, j = arr.length; i < j; i++) {
							cr.setRoleId(Integer.parseInt(arr[i].trim()));
							systemSetService.insertCustomerRights(cr);
						}
					}

				}

				response.setCode(Response.SUCCESS_CODE);
				response.setMessage("添加账号成功");
			}

		}
		return response;
	}

	/**
	 * 根据员工ID获取员工信息
	 * 
	 * @param myAccountReq
	 * @return
	 */
	@RequestMapping(value = "info/{merchantId}", method = RequestMethod.POST)
	public Response getEmpInfoFromAgent(@PathVariable int merchantId) {
		Response response = new Response();
		Map<String, Object> result = systemSetService
				.getEmpInfoById(merchantId);
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
	public Response deleteEmpInfoFromAgent(
			@RequestBody Map<Object, Object> param) {
		Response response = new Response();

		Integer id = (Integer) param.get("ids");
		if (systemSetService.updateCustomerStatus(id) > 0) {
			if (systemSetService.deleteEmpInfoFromAgent(id) > 0) {
				response.setCode(Response.SUCCESS_CODE);
				response.setMessage("删除成功!!!!");
			} else {
				response.setCode(Response.ERROR_CODE);
				response.setMessage("删除失败!!!!");
			}
		} else {
			response.setCode(Response.ERROR_CODE);
			response.setMessage("删除失败!!!!");
		}

		return response;
	}

	@RequestMapping(value = "getList/{customerId}/{page}/{rows}", method = RequestMethod.POST)
	public Response getList(@PathVariable int customerId,
			@PathVariable int page, @PathVariable int rows) {
		Response response = null;
		try {
			response = new Response();
			Map<Object, Object> result = new HashMap<Object, Object>();
			result.put("total", systemSetService.getListCount(customerId));
			result.put("list", systemSetService.getList(customerId, page, rows));
			logger.debug(result);
			response.setResult(result);
			response.setCode(Response.SUCCESS_CODE);
		} catch (Exception e) {
			logger.error("获取商户信息列表失败", e);
			response.setMessage("获取商户信息列表失败:系统异常");
		}
		return response;
	}

	@RequestMapping(value = "userRegistration", method = RequestMethod.POST)
	public Response insert(@RequestBody Map<String, Object> map) {
		Response response = null;

		return response;
	}
}
