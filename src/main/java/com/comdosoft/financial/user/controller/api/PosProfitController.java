package com.comdosoft.financial.user.controller.api;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.comdosoft.financial.user.domain.Response;
import com.comdosoft.financial.user.domain.query.PosProfitReq;
import com.comdosoft.financial.user.service.PosProfitService;

/**
 * 代理商POS销售分润
 * 
 * @author DELL
 *
 */
@RestController
@RequestMapping(value = "api/posprofit")
public class PosProfitController {

	@Resource
	private PosProfitService posProfitService;

	/**
	 * POS销售分润列表
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "getPosProfitList", method = RequestMethod.POST)
	public Response getPosProfitList(@RequestBody PosProfitReq req) {
		Response response = new Response();
		try {
			Map<String, Object> result = posProfitService.getProfitResult(req);
			response.setResult(result);
			response.setCode(Response.SUCCESS_CODE);
		} catch (Exception e) {
			response.setMessage("系统错误,获取代理商POS销售分润失败");
			response.setCode(Response.ERROR_CODE);
		}
		return response;
	}

}
