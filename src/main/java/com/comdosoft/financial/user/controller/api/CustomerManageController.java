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
	
	/**
	 * 获取用户列表
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "getList", method = RequestMethod.POST)
	public Response getList(@RequestBody CustomerManageReq req){
		Response response = new Response();
        Map<String,Object> result= customerManageService.getList(req);
        response.setCode(Response.SUCCESS_CODE);
        response.setResult(result);
        return response;
	}
	
	/**
	 * 新增用户
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "insert", method = RequestMethod.POST)
	public Response insert(@RequestBody CustomerManageReq req){
		Response response=new Response();
		Map<String,Object> result= customerManageService.insert(req);
		response.setCode(Integer.parseInt(result.get("resultCode").toString()));
		response.setMessage(result.get("resultInfo").toString());
		return response;
	}
	
	/**
	 * 删除单个用户
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "deleteOne", method = RequestMethod.POST)
	public Response deleteOne(@RequestBody CustomerManageReq req){
		Response response=new Response();
		Map<String,Object> result= customerManageService.deleteOne(req);
		response.setCode(Integer.parseInt(result.get("resultCode").toString()));
		response.setMessage(result.get("resultInfo").toString());
		return response;
	}
	
	/**
	 * 批量删除用户
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "deleteAll", method = RequestMethod.POST)
	public Response deleteAll(@RequestBody CustomerManageReq req){
		Response response=new Response();
		Map<String,Object> result= customerManageService.deleteAll(req);
		response.setCode(Integer.parseInt(result.get("resultCode").toString()));
		response.setMessage(result.get("resultInfo").toString());
		return response;
	}
	
	/**
	 * 查看用户详细信息
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "getInfo", method = RequestMethod.POST)
	public Response getInfo(@RequestBody CustomerManageReq req){
		Response response=new Response();
		Map<String,Object> result= customerManageService.getInfo(req);
		response.setCode(Integer.parseInt(result.get("resultCode").toString()));
		response.setMessage(result.get("resultInfo").toString());
		response.setResult(result.get("result"));
		return response;
	}
}
