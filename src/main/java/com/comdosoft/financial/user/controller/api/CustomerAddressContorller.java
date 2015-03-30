package com.comdosoft.financial.user.controller.api;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.comdosoft.financial.user.domain.Response;
import com.comdosoft.financial.user.domain.zhangfu.CustomerAddress;
import com.comdosoft.financial.user.service.CustomeraddressService;

@RestController
@RequestMapping("api/address")
public class CustomerAddressContorller {
	@Autowired
	private CustomeraddressService customer_addresser_server;

	@RequestMapping(value = "insert", method = RequestMethod.POST)
	public Response add(@RequestBody CustomerAddress param) {
		Response response = new Response();
		response.setCode(Response.ERROR_CODE);
		System.out.println(param.getIsDefault());
		if (customer_addresser_server.insertaddress(param) > 0) {
			response.setCode(Response.SUCCESS_CODE);
		} else {
			response.setCode(Response.ERROR_CODE);
		}
		return response;
	}

	@RequestMapping(value = "delete/{id}", method = RequestMethod.POST)
	public Response delete(@PathVariable int id) {
		Response response = new Response();
		response.setCode(Response.ERROR_CODE);
		int result = customer_addresser_server.deletetaddress(id);
		if (result > 0) {
			response.setCode(Response.SUCCESS_CODE);
		}

		return response;
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public Response update(@RequestBody CustomerAddress req, @PathVariable int id) {
		Response response = new Response();
		response.setCode(Response.ERROR_CODE);
		customer_addresser_server.updateadderss(req, id);
		return response;
	}

	@RequestMapping(value = "query/{customer_id}", method = RequestMethod.POST)
	public Response query(@PathVariable int customer_id) {
		Response response = new Response();

		List<Map<String, Object>> result = customer_addresser_server.queryadderss(customer_id);
		if (result != null) {
			response.setResult(result);
			response.setCode(Response.SUCCESS_CODE);
			return response;
		}
		return response;
	}

	@RequestMapping(value = "setisDefault", method = RequestMethod.POST)
	public Response setisDefault(@RequestBody CustomerAddress param) {
		Response sysResponse = new Response();
		try {
			int oidDefault = 1;
			int Default = 2;
			customer_addresser_server.updeteDefault(oidDefault, Default);
			if (customer_addresser_server.setisDefault(param) == 1) {
				sysResponse.setCode(Response.SUCCESS_CODE);
				sysResponse = Response.getSuccess();
				return sysResponse;
			}
		} catch (Exception e) {
			sysResponse = Response.getError("设置默认地址失败:系统异常");
		}
		return sysResponse;
	}
}
