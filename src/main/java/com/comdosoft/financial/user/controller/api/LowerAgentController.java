package com.comdosoft.financial.user.controller.api;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.comdosoft.financial.user.domain.Response;
import com.comdosoft.financial.user.domain.query.ExchangeGoodReq;
import com.comdosoft.financial.user.domain.query.LowerAgentReq;
import com.comdosoft.financial.user.service.LowerAgentService;
/**
 * 下级代销商业务处理
 * @author yyb
 *
 */
@RestController
@RequestMapping("api/lowerAgent")
public class LowerAgentController {
	@Autowired
	private LowerAgentService lowerAgentService;
	
	/**
	 * 获取下级代理商分润设置比例列表
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "getProfitlist", method = RequestMethod.POST)
    public Response getProfitSetList(@RequestBody  LowerAgentReq req){
        Response response = new Response();
        Map<String,Object> result= lowerAgentService.getProfitlist(req);
        response.setCode(Response.SUCCESS_CODE);
        response.setResult(result);
        return response;
    }
	
	/**
	 * 根据支付通道ID获取所关联的分润类型
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "getTradelist", method = RequestMethod.POST)
    public Response getTradelist(@RequestBody  String id){
        Response response = new Response();
        Map<String,Object> result= lowerAgentService.getTradelist(Integer.parseInt(id));
        response.setCode(Response.SUCCESS_CODE);
        response.setResult(result);
        return response;
    }
	/**
	 * 获取支付渠道list
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "getChannellist", method = RequestMethod.POST)
    public Response getChannellist(){
        Response response = new Response();
        Map<String,Object> result= lowerAgentService.getChannellist();
        response.setCode(Response.SUCCESS_CODE);
        response.setResult(result);
        return response;
    }
	
	/**
	 * 展示下级代理商列表
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST)
    public Response getList(@RequestBody  LowerAgentReq req){
        Response response = new Response();
        Map<String,Object> result= lowerAgentService.getList(req);
        response.setCode(Response.SUCCESS_CODE);
        response.setResult(result);
        return response;
    }
	
	/**
	 * 根据Id展示下级代理商具体信息
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "info", method = RequestMethod.POST)
    public Response getInfo(@RequestBody  LowerAgentReq req){
        Response response = new Response();
        Map<String,Object> result= lowerAgentService.getInfo(req);
        response.setCode(Response.SUCCESS_CODE);
        response.setResult(result);
        return response;
    }
	/**
	 * 获取省填充下拉列表
	 * @return
	 */
	@RequestMapping(value = "getProvince", method = RequestMethod.POST)
	public Response getProvinceList(){
		Response response = new Response();
        Map<String,Object> result= lowerAgentService.getProvinceList();
        response.setCode(Response.SUCCESS_CODE);
        response.setResult(result);
        return response;
	}
	
	/**
	 * 根据省级联市填充下拉列表
	 * @param proId
	 * @return
	 */
	@RequestMapping(value = "getCity", method = RequestMethod.POST)
	public Response getCityList(@RequestBody String proId){
		Response response = new Response();
        Map<String,Object> result= lowerAgentService.getCityList(Integer.parseInt(proId));
        response.setCode(Response.SUCCESS_CODE);
        response.setResult(result);
        return response;
	}
	
	/**
	 * 创建新增新的下级代理商
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "createNew", method = RequestMethod.POST)
	public Response createNew(@RequestBody LowerAgentReq req){
		Response response = new Response();
        int temp=lowerAgentService.addNewAgent(req);
		if(temp==1){
        	response.setCode(Response.SUCCESS_CODE);
        }else{
        	response.setCode(Response.ERROR_CODE);
        }
        return response;
	}
	
	/**
	 * 修改编辑下级代理商保存
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public Response save(@RequestBody LowerAgentReq req){
		Response response = new Response();
        int temp=lowerAgentService.save(req);
		if(temp==1){
        	response.setCode(Response.SUCCESS_CODE);
        }else{
        	response.setCode(Response.ERROR_CODE);
        }
        return response;
	}
	
	/**
	 * 检查新的登陆名是否已经存在
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "check", method = RequestMethod.POST)
	public Response checkLoginId(@RequestBody LowerAgentReq req){
		Response response = new Response();
		if(req.getLoginId().trim().equals("")){
			response.setCode(Response.MISSING_CODE);
			response.setMessage("输入登陆ID不能为空！");
		}else{
			int temp=lowerAgentService.checkLoginId(req);
			if(temp>=1){
	        	response.setCode(Response.SUCCESS_CODE);
	        }else{
	        	response.setCode(Response.ERROR_CODE);
	        }
		}
		req.setLoginId(req.getLoginId().trim());
        
        return response;
	}
	
	/**
	 * 代理商 分润新增  或者保存  1为新增，0为保存
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "saveOrEdit", method = RequestMethod.POST)
	public Response saveOrEdit(@RequestBody LowerAgentReq req){
		Response response = new Response();
	    Map<String, Object> map=lowerAgentService.saveOrEdit(req);
	    int result= (Integer)map.get("errorCode");
	    if(result==-1){
	    	response.setCode(Response.ERROR_CODE);
	    	response.setMessage("保存时出错！");
	    }else{
	    	response.setCode(Response.SUCCESS_CODE);
	    	response.setMessage("保存时出错！");
	    }
        return response;
	}
	
	
}