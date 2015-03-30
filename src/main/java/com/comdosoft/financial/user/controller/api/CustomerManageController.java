package com.comdosoft.financial.user.controller.api;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.comdosoft.financial.user.domain.Response;
import com.comdosoft.financial.user.domain.query.CustomerManageReq;
import com.comdosoft.financial.user.service.CustomerManageService;
import com.comdosoft.financial.user.service.SystemSetService;
/**
 * 用于App用户管理
 * @author yangyibin
 *
 */
@RestController
@RequestMapping("api/customerManage")
public class CustomerManageController {
	@Autowired
	private CustomerManageService customerManageService;
	
	@Autowired
	private SystemSetService sys;
	
	
	@RequestMapping(value = "getList", method = RequestMethod.POST)
	public Response getList(@RequestBody CustomerManageReq req){
		Response response = new Response();
        Map<String,Object> result= customerManageService.getList(req);
        response.setCode(Response.SUCCESS_CODE);
        response.setResult(result);
        return response;
	}
}
