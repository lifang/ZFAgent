package com.comdosoft.financial.user.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.comdosoft.financial.user.domain.Response;
import com.comdosoft.financial.user.service.DataBaseCopyService;

@RestController
@RequestMapping("api/dataBase")
public class DataBaseCopyController {
	
	@Autowired
	private DataBaseCopyService dbs;
	
	@RequestMapping(value = "init", method = RequestMethod.POST)
	public Response changeStatus(){
		Response response=new Response();
		dbs.init();
		return response;
	}
}
