package com.comdosoft.financial.user.controller.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.comdosoft.financial.user.domain.Response;
import com.comdosoft.financial.user.domain.query.LowerAgentReq;
import com.comdosoft.financial.user.service.CommentService;
import com.comdosoft.financial.user.service.LowerAgentService;
import com.comdosoft.financial.user.service.SystemSetService;
import com.comdosoft.financial.user.utils.CommUtils;
import com.comdosoft.financial.user.utils.HttpFile;
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
	
	@Autowired
	private SystemSetService sys;
	
	@Autowired
	private CommentService commentService;
	
	@Value("${filePath}")
	private String filePath;
	
	@Value("${agent}")
	private String agent;
	/**
	 * 根据传入的agentId,statusId，修改代理商状态
	 * @param req
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "changeStatus", method = RequestMethod.POST)
	public Response changeStatus(@RequestBody LowerAgentReq req) throws Exception{
		Response response=new Response();
		Map<String,Object> result=new HashMap<String, Object>();
		try{
			result=lowerAgentService.changeStatus(req);
			if(Integer.parseInt(result.get("resultCode").toString()) == 1){
				response.setCode(Response.SUCCESS_CODE);
				response.setMessage(result.get("resultInfo").toString());
			}else{
				response.setCode(Response.ERROR_CODE);
				response.setMessage(result.get("resultInfo").toString());
			}
		}catch(Exception ex){
			ex.printStackTrace();
			response.setCode(Response.ERROR_CODE);
			response.setMessage(ex.getMessage());
		}finally{
			return response;
		}
	}
	
	/**
	 * 修改默认分润比例
	 * @param req
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "changeProfit", method = RequestMethod.POST)
	public Response changeProfit(@RequestBody LowerAgentReq req) throws Exception{
		Response response=new Response();
		Map<String,Object> result=new HashMap<String, Object>();
		try{
			result=lowerAgentService.changeProfit(req);
			if(Integer.parseInt(result.get("resultCode").toString()) == 1){
				response.setCode(Response.SUCCESS_CODE);
				response.setMessage(result.get("resultInfo").toString());
			}else{
				response.setCode(Response.ERROR_CODE);
				response.setMessage(result.get("resultInfo").toString());
			}
		}catch(Exception ex){
			ex.printStackTrace();
			response.setCode(Response.ERROR_CODE);
			response.setMessage(ex.getMessage());
		}finally{
			return response;
		}
	}
	
	/**
	 * 获取默认分润比例
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "getDefaultProfit", method = RequestMethod.POST)
	public Response getDefaultProfit(@RequestBody LowerAgentReq req){
		Response response=new Response();
		Map<String,Object> result=lowerAgentService.getDefaultProfit(req);
		if(Integer.parseInt(result.get("resultCode").toString()) == 1){
			response.setCode(Response.SUCCESS_CODE);
			response.setResult(result.get("resultInfo").toString());
		}
		return response;
	}
	
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
    public Response getTradelist(@RequestBody Map<String, Object> map){
        Response response = new Response();
        int id= Integer.parseInt(map.get("id").toString());
        Map<String,Object> result= lowerAgentService.getTradelist(id);
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
	 * @throws Exception 
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "createNew", method = RequestMethod.POST)
	public Response createNew(@RequestBody LowerAgentReq req) throws Exception{
		Response response = new Response();
		Map<String,Object> map=new HashMap<String, Object>();
		try{
			if(req.getLoginId()==null || req.getLoginId().trim().equals("")){
				response.setCode(Response.ERROR_CODE);
				response.setMessage("输入登陆ID不能为空！");
			}else{
				if(req.getPwd()==null || req.getPwd1()==null|| !req.getPwd().equals(req.getPwd1())){
					response.setCode(Response.ERROR_CODE);
					response.setMessage("两次输入的密码不一致");
				}else{
		        	map=lowerAgentService.addNewAgent(req);
					if(map.get("resultCode").toString().equals("-1")){
						response.setCode(Response.ERROR_CODE);
			        	response.setMessage(map.get("resultInfo").toString());
			        }else{
			        	response.setCode(Response.SUCCESS_CODE);
			        	response.setMessage(map.get("resultInfo").toString());
			        }
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
			response.setCode(Response.ERROR_CODE);
			response.setMessage(ex.getMessage());
		}finally{
			return response;
		}
	}
	
	/**
	 * 修改编辑下级代理商保存
	 * @param req
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public Response save(@RequestBody LowerAgentReq req) throws Exception{
		Response response = new Response();
		Map<String,Object> map=new HashMap<String, Object>();
		try{
			map=lowerAgentService.save(req);
		
			if(map.get("resultCode").toString().equals("1")){
	        	response.setCode(Response.SUCCESS_CODE);
	        	response.setMessage(map.get("resultInfo").toString());
	        }else{
	        	response.setCode(Response.ERROR_CODE);
	        	response.setMessage(map.get("resultInfo").toString());
	        }
		}catch(Exception ex){
			ex.printStackTrace();
			response.setCode(Response.ERROR_CODE);
			response.setMessage(ex.getMessage());
		}finally{
			return response;
		}
	}
	
	/**
	 * 根据传入的cityId,获取省市信息
	 * @param cityId
	 * @return
	 */
	@RequestMapping(value = "getProCity", method = RequestMethod.POST)
	public Response getProCity(@RequestBody LowerAgentReq req){
		Response response = new Response();
        Map<String,Object> result= lowerAgentService.getProCity(req);
        response.setCode(Response.SUCCESS_CODE);
        response.setResult(result);
        return response;
	}
	
	
	/**
	 * 代理商 分润新增  或者保存  1为新增，0为保存
	 * @param req
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "saveOrEdit", method = RequestMethod.POST)
	public Response saveOrEdit(@RequestBody LowerAgentReq req) throws Exception{
		Response response = new Response();
		try{
		    Map<String, Object> map=lowerAgentService.saveOrEdit(req);
		    int result= (Integer)map.get("resultCode");
		    if(result==-1){
		    	response.setCode(Response.ERROR_CODE);
		    	response.setMessage(map.get("resultInfo").toString());
		    }else{
		    	response.setCode(Response.SUCCESS_CODE);
		    	response.setMessage(map.get("resultInfo").toString());
		    }
		}catch(Exception ex){
			ex.printStackTrace();
			response.setCode(Response.ERROR_CODE);
			response.setMessage(ex.getMessage());
		}finally{
			return response;
		}
	}
	
	/**
	 * 修改密码
	 * @param req
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "changePwd", method = RequestMethod.POST)
	public Response changePwd(@RequestBody LowerAgentReq req) throws Exception{
		Response response = new Response();
		try{
			if(req.getPwd()==null ||req.getPwd().trim().equals("")){
				response.setCode(Response.ERROR_CODE);
		    	response.setMessage("输入的密码不能为空！");
			}else if(!req.getPwd().trim().equals(req.getPwd1().trim())){
				response.setCode(Response.ERROR_CODE);
		    	response.setMessage("两次输入的密码不一致！");
			}else{
				Map<String, Object> map=lowerAgentService.changePwd(req);
			    int result= (Integer)map.get("errorCode");
			    if(result==-1){
			    	response.setCode(Response.ERROR_CODE);
			    	response.setMessage("保存时出错！");
			    }else{
			    	response.setCode(Response.SUCCESS_CODE);
			    	response.setMessage("保存成功！");
			    }
			}
		}catch(Exception ex){
			ex.printStackTrace();
			response.setCode(Response.ERROR_CODE);
			response.setMessage(ex.getMessage());
		}finally{
			return response;
		}
	}
	
	/**
	 * 
	 * @param req
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "delChannel", method = RequestMethod.POST)
	public Response delChannel(@RequestBody LowerAgentReq req) throws Exception{
		Response response = new Response();
		try{
			Map<String, Object> map=lowerAgentService.delChannel(req);
			
			int result= (Integer)map.get("resultCode");
		    if(result==-1){
		    	response.setCode(Response.ERROR_CODE);
		    	response.setMessage("删除时出错！");
		    }else{
		    	response.setCode(Response.SUCCESS_CODE);
		    	response.setMessage("删除成功！");
		    }
		}catch(Exception ex){
			ex.printStackTrace();
			response.setCode(Response.ERROR_CODE);
			response.setMessage(ex.getMessage());
		}finally{
			return response;
		}
	}
	/**
	 * 修改是否有分润    传入sonagentsId,isprofit
	 * @param req
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value="setDefaultProfit",method=RequestMethod.POST)
	public Response setDefaultProfit(@RequestBody LowerAgentReq req) throws Exception{
		Response response = new Response();
		try{
			Map<String, Object> map=lowerAgentService.openCloseProfit(req);
			
			int result= (Integer)map.get("resultCode");
		    if(result==-1){
		    	response.setCode(Response.ERROR_CODE);
		    	response.setMessage("设置分润时出错！");
		    }else{
		    	response.setCode(Response.SUCCESS_CODE);
		    	response.setMessage("设置分润成功！");
		    }
	    }catch(Exception ex){
			ex.printStackTrace();
			response.setCode(Response.ERROR_CODE);
			response.setMessage(ex.getMessage());
		}finally{
			return response;
		}
	}
	
	/**
	 * 上传图片
	 * @param id
	 * @param img
	 * @param request
	 * @return
	 */
	@RequestMapping(value="uploadImg/{agentsId}",method=RequestMethod.POST)
	public Response uploadImg(@PathVariable("agentsId") int id,@RequestParam(value="img") MultipartFile updatefile, HttpServletRequest request) {
		try {
    		String joinpath="";
    		int temp=updatefile.getOriginalFilename().lastIndexOf(".");
    		String houzuiStr=updatefile.getOriginalFilename().substring(temp+1);
    		
    		if(!commentService.typeIsCommit(houzuiStr)){
    			return Response.getError("您所上传的文件格式不正确");
    		}else{
    			if(!HttpFile.fileSize(updatefile)){
  	      			return Response.getError("您上传的图片大小过大，请上传小于2M的图片!");
  				}
    		}
        	joinpath = HttpFile.upload(updatefile, agent+id+"/opengImg/");
        	if("上传失败".equals(joinpath) || "同步上传失败".equals(joinpath)){
        		return Response.getError(joinpath);
        	}else{
        		return Response.getSuccess(filePath+joinpath);
        	}
	    } catch (Exception e) {
	    	return Response.getError("请求失败！");
	    }
		
		
    }
}
