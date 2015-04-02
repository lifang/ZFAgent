package com.comdosoft.financial.user.controller.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.comdosoft.financial.user.domain.Response;
import com.comdosoft.financial.user.service.CustomeraddressService;

@RestController
@RequestMapping("api/address")
public class CustomerAddressContorller {
	@Autowired
	private CustomeraddressService customer_addresser_server;

	/**
	 * 查询代理商收获地址
	 * 
	 * @param customer_id
	 * @return
	 */
	@RequestMapping(value = "query/{customer_id}", method = RequestMethod.POST)
	public Response query(@PathVariable int customer_id) {
		Response response = new Response();
		List<Map<String, Object>> result = customer_addresser_server.queryAddress(customer_id);
		if (result != null) {
			response.setResult(result);
			response.setCode(Response.SUCCESS_CODE);
			return response;
		}
		return response;
	}

	/**
	 * 插入收获地址
	 * 
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "insert", method = RequestMethod.POST)
	public Response add(@RequestBody Map<Object, Object> param) {
		Response sysResponse = null;
		try {
			customer_addresser_server.insertAddress(param);
			sysResponse = Response.getSuccess();
		} catch (Exception e) {
			sysResponse = Response.getError("新增地址失败:系统异常");
		}
		return sysResponse;
	}

	/**
	 * 删除收获地址
	 * 
	 * @param param
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public Response delete(@RequestBody Map<Object, Object> param) {
		Response sysResponse = null;
		try {
			List<Integer> ids = (ArrayList<Integer>) param.get("ids");
			for (int id : ids) {
				customer_addresser_server.deleteAddress(id);
			}
			sysResponse = Response.getSuccess();
		} catch (Exception e) {
			sysResponse = Response.getError("删除地址失败:系统异常");
		}
		return sysResponse;
	}

	/**
	 * 修改收获地址
	 * 
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "updateAddress", method = RequestMethod.POST)
	public Response update(@RequestBody Map<Object, Object> param) {
		Response sysResponse = null;
		try {
			customer_addresser_server.updateAddress(param);
			sysResponse = Response.getSuccess();
		} catch (Exception e) {
			sysResponse = Response.getError("修改地址失败:系统异常");
		}
		return sysResponse;
	}

	/**
	 * 设置默认地址
	 * 
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "setDefaultAddress", method = RequestMethod.POST)
	public Response setisDefault(@RequestBody Map<Object, Object> param) {
		Response sysResponse = null;
		try {
			customer_addresser_server.setDefaultAddress(param);
			sysResponse = Response.getSuccess();
		} catch (Exception e) {
			sysResponse = Response.getError("设置为默认地址失败:系统异常");
		}
		return sysResponse;
	}
}
