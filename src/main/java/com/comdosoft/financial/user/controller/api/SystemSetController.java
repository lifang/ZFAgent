package com.comdosoft.financial.user.controller.api;

import java.util.Date;
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
	private static final Logger logger = Logger.getLogger(SystemSetController.class);
	@Resource
	private SystemSetService systemSetService;

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
		Map<String, Object> map = systemSetService.getEmpInfoByUsername(username);
		if (map != null && !map.isEmpty()) {
			response.setCode(Response.ERROR_CODE);
			response.setMessage("登陆ID不能重复");
		} else {
			Date d = new Date();
			Customer c = new Customer();
			c.setName(req.getName());
			c.setUsername(username);
			c.setPassword(SysUtils.string2MD5(req.getPassword()));
			c.setStatus(Customer.STATUS_NORMAL);// 正常
			c.setTypes(Customer.TYPE_AGENT_STAFF);
			c.setCreatedAt(d);
			systemSetService.insertCustomer(c);

			map = systemSetService.getEmpInfoByUsername(username);
			if (map != null) {
				CustomerAgentRelation ca = new CustomerAgentRelation();
				ca.setAgentId(req.getAgent_id());
				int customerId = Integer.parseInt(map.get("id").toString());
				ca.setCustomerId(customerId);
				ca.setStatus(CustomerAgentRelation.STATUS_2);
				ca.setTypes(6);
				ca.setCreatedAt(d);
				systemSetService.insertCustomerAgentRelations(ca);

				CustomerRoleRelation cr = null;

				String[] rights = req.getRightIds();
				if (rights != null) {
					for (int i = 0, j = rights.length; i < j; i++) {
						cr = new CustomerRoleRelation();
						cr.setCreatedAt(d);
						cr.setCustomerId(customerId);
						cr.setRoleId(Integer.parseInt(rights[i].trim()));
						systemSetService.insertCustomerRights(cr);
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
	@RequestMapping(value = "info/{customerId}", method = RequestMethod.POST)
	public Response getEmpInfoFromAgent(@PathVariable int customerId) {
		Response response = new Response();
		Map<String, Object> result = systemSetService.getEmpInfoFromAgent(customerId);
		if (result != null) {
			logger.debug(result);
			response.setCode(Response.SUCCESS_CODE);
			response.setResult(result);
		} else {
			response.setCode(Response.ERROR_CODE);
		}

		return response;
	}

	/**
	 * 删除用户
	 * 
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public Response deleteEmpInfoFromAgent(@RequestBody Map<Object, Object> param) {
		Response response = new Response();

		if (param.get("ids") != null) {
			int id = Integer.parseInt(param.get("ids").toString());
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
		} else {
			response.setCode(Response.ERROR_CODE);
			response.setMessage("删除失败!!!!");
		}

		return response;
	}

	/**
	 * 重置用户密码
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "resetPassword", method = RequestMethod.POST)
	public Response insert(@RequestBody Map<String, Object> map) {
		Response response = new Response();
		if (map.get("customer_id") != null) {
			map.put("password", SysUtils.string2MD5((String) map.get("password")));

			if (systemSetService.resetPassword(map) > 0) {
				response.setCode(Response.SUCCESS_CODE);
				response.setMessage("重置密码成功");
			} else {
				response.setCode(Response.ERROR_CODE);
				response.setMessage("重置密码失败");
			}
		} else {
			response.setCode(Response.ERROR_CODE);
			response.setMessage("重置密码失败");
		}

		return response;
	}

	/**
	 * 编辑用户信息
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "editCustomer", method = RequestMethod.POST)
	public Response editCustomer(@RequestBody EmpReq req) {
		Response response = new Response();
		String[] roleIds = req.getRightIds();

		if (systemSetService.editCustomerInfo(req) > 0) {
			systemSetService.deleteCustomerRights(req);
			if (roleIds != null) {
				for (int i = 0, j = roleIds.length; i < j; i++) {
					req.setRole_id(Integer.parseInt(roleIds[i]));
					systemSetService.insertRights(req);
				}
				response.setCode(Response.SUCCESS_CODE);
				response.setMessage("更新用户信息成功");
			} else {
				response.setCode(Response.ERROR_CODE);
				response.setMessage("更新用户信息失败");
			}

		} else {
			response.setCode(Response.ERROR_CODE);
			response.setMessage("更新用户信息失败");
		}
		return response;
	}

	/**
	 * 账户列表
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "getAccountList", method = RequestMethod.POST)
	public Response getAccountList(@RequestBody MyAccountReq req) {
		Response response = new Response();
		Map<String, Object> result = systemSetService.getAccountList(req);
		response.setCode(Response.SUCCESS_CODE);
		response.setResult(result);
		return response;
	}
}
