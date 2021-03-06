package com.comdosoft.financial.user.controller.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.comdosoft.financial.user.domain.Response;
import com.comdosoft.financial.user.domain.zhangfu.CsAgent;
import com.comdosoft.financial.user.domain.zhangfu.CsCancel;
import com.comdosoft.financial.user.domain.zhangfu.CsUpdateInfo;
import com.comdosoft.financial.user.domain.zhangfu.Customer;
import com.comdosoft.financial.user.domain.zhangfu.CustomerAddress;
import com.comdosoft.financial.user.domain.zhangfu.CustomerAgentRelation;
import com.comdosoft.financial.user.domain.zhangfu.Terminal;
import com.comdosoft.financial.user.domain.zhangfu.TerminalOpeningInfo;
import com.comdosoft.financial.user.service.CommentService;
import com.comdosoft.financial.user.service.OpeningApplyService;
import com.comdosoft.financial.user.service.TerminalsWebService;
import com.comdosoft.financial.user.service.UserManagementService;
import com.comdosoft.financial.user.utils.CommonServiceUtil;
import com.comdosoft.financial.user.utils.HttpFile;
import com.comdosoft.financial.user.utils.SysUtils;
import com.comdosoft.financial.user.utils.page.PageRequest;

/**
 * 
 * 终端管理<br>
 * <功能描述>
 *
 * @author xfh 2015年2月10日
 *
 */
@RestController
@RequestMapping(value = "/api/webTerminal")
public class TerminalsWebController {
	 private static final Logger logger = LoggerFactory.getLogger(TerminalsWebController.class);
	
	@Resource
	private TerminalsWebService terminalsWebService;
	
	@Resource
	private OpeningApplyService openingApplyService;
	
	@Resource
	private UserManagementService userManagementService;
	
	@Autowired
	private CommentService commentService ;
	
	@Value("${uploadPictureTempsPath}")
    private String uploadPictureTempsPath;

	@Value("${passPath}")
	private String passPath;
	
	@Value("${userTerminal}")
	private String userTerminal;
	
	@Value("${filePath}")
	private String filePath;
	
	@Value("${sysFileTerminal}")
	private String sysFileTerminal;
	
	@Value("${syncStatus}")
	private String syncStatus;
	
	@Value("${timingPath}")
	private String timingPath;
	
	/**
	 * 根据用户ID获得终端列表
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "getAgentApplyList", method = RequestMethod.POST)
	public Response getApplyList(@RequestBody Map<String, Object> map) {
		try {
			Map<Object, Object> maps = new HashMap<Object, Object>();
			PageRequest PageRequest = new PageRequest((Integer)map.get("page"),
					(Integer)map.get("rows"));
			int offSetPage = PageRequest.getOffset();
			
			maps.put("total", terminalsWebService.getTerminalListNums(
					((Integer)map.get("customersId")),
					(Integer)map.get("frontStatus"),
					(String)map.get("serialNum")));
			//终端付款状态（2 已付   1未付  3已付定金）
			maps.put("frontPayStatus", terminalsWebService.getFrontPayStatus());
			List<Map<Object, Object>> li = terminalsWebService.getTerminalList(
					((Integer)map.get("customersId")),
					offSetPage,
					(Integer)map.get("rows"),
					(Integer)map.get("frontStatus"),
					(String)map.get("serialNum"));
			maps.put("list", li == null?new ArrayList<Object>():li);
			return Response.getSuccess(maps);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getError("请求失败！");
		}
	}
	
	/**
	 * 进入终端详情(Web)
	 * 
	 * @param id
	 */
	@RequestMapping(value = "getWebApplyDetail", method = RequestMethod.POST)
	public Response getWebApplyDetail(@RequestBody Map<Object, Object> maps) {
		try {
			Map<Object, Object> map = new HashMap<Object, Object>();
			// 获得终端详情
			map.put("applyDetails",
					terminalsWebService.getApplyDetails((Integer)maps.get("terminalsId")));
			// 终端交易类型
			map.put("rates", terminalsWebService.getRate((Integer)maps.get("terminalsId")));
			//获得租赁信息
			map.put("tenancy", terminalsWebService.getTenancy((Integer)maps.get("terminalsId")));
			// 追踪记录
			map.put("trackRecord", terminalsWebService.getTrackRecord((Integer)maps.get("terminalsId")));
			// 开通详情
			map.put("openingDetails",
					terminalsWebService.getOpeningDetails((Integer)maps.get("terminalsId")));
			// 获得已有申请开通基本信息
						map.put("openingInfos",
								terminalsWebService.getOppinfo((Integer)maps.get("terminalsId")));
			return Response.getSuccess(map);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getError("请求失败！");
		}
	}
	
	/**
	 * 判断申请注销
	 * 
	 * @param maps
	 */
	@RequestMapping(value = "judgeRentalReturn", method = RequestMethod.POST)
	public Response judgeRentalReturn(@RequestBody Map<Object, Object> maps) {
		try {
			int count = terminalsWebService.JudgeRentalReturnStatus((Integer)maps.get("terminalid"),CsCancel.STATUS_1,CsCancel.STATUS_2);
			if(count == 0){
				return Response.getSuccess("可以申请！");
			}else{
				return Response.getError("已有相关申请！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getError("请求失败！");
		}
	}
	
	/**
	 * 判断申请更新资料
	 * @param maps
	 */
	@RequestMapping(value = "judgeUpdate", method = RequestMethod.POST)
	public Response JudgeUpdate(@RequestBody Map<Object, Object> maps) {
		try {
			int count = terminalsWebService.judgeUpdateStatus((Integer)maps.get("terminalid"),CsUpdateInfo.STATUS_1,CsUpdateInfo.STATUS_2);
			if(count == 0){
				return Response.getSuccess("可以申请！");
			}else{
				return Response.getError("已有相关申请！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getError("请求失败！");
		}
	}
	
	/**
	 * 找回POS机密码
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "Encryption", method = RequestMethod.POST)
	public Response Encryption(@RequestBody Map<String, Object> map) {
		try {
			//String pass = SysUtils.Decrypt(
					//terminalsWebService.findPassword((Integer)map.get("terminalid")),passPath);
			
			String pass = 
					terminalsWebService.findPassword((Integer)map.get("terminalid"));
			
			if("".equals(pass) || pass == null){
				return Response.getSuccess("未设置密码！");
			}
			return Response.getSuccess(pass);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getError("请求失败!");
		}
	}
	
	/**
	 * 收件人信息
	 * @param customerId
	 * @return
	 */
	@RequestMapping(value="getAddressee",method=RequestMethod.POST)
	public Response getAddressee(@RequestBody Map<String, Object> map){
		try{
			return Response.getSuccess(terminalsWebService.getAddressee((Integer)map.get("customerId")));
		}catch(Exception e){
			logger.error("收件人信息异常！", e);
			e.printStackTrace();
			return Response.getError("请求失败！");
		}
	}
	
	/**
	 *添加联系地址
	 * 
	 * @param id
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "addCostometAddress", method = RequestMethod.POST)
	public Response addCostometAddress(@RequestBody CustomerAddress customerAddress) {
		try {
			customerAddress.setIsDefault(CustomerAddress.ISDEFAULT_2);
			customerAddress.setStatus((byte) CustomerAddress.STATUS_1);
			terminalsWebService.addCostometAddress(customerAddress);
			return Response.getSuccess("添加成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getError("请求失败！");
		}
	}
	
	/**
	 * 提交申请售后
	 * @param customerId
	 * @return
	 */
	@RequestMapping(value="submitAgent",method=RequestMethod.POST)
	public Response submitAgent(@RequestBody Map<Object, Object> map){
		try{
			List<String> errorlist = new ArrayList<String>();//错误终端号数据
			List<String> successlist = new ArrayList<String>();//正确终端号数据
			
			CsAgent csAgent = new CsAgent();
			csAgent.setCustomerId((Integer)map.get("customerId"));
			csAgent.setAddress((String)map.get("address"));
			csAgent.setReason((String)map.get("reason"));
			csAgent.setTerminalsList((String)map.get("terminalsList"));
			csAgent.setReciver((String)map.get("receiver"));
			csAgent.setPhone((String)map.get("phone"));
			
			String[] arr = csAgent.getTerminalsList().split(",");
			
			for(int i=0;i<arr.length;i++){
				int count = terminalsWebService.checkTerminalCode(arr[i],(Integer)map.get("agentId"),Terminal.TerminalTYPEID_4,Terminal.TerminalTYPEID_5);
				if(count == 0){
					errorlist.add(arr[i]);
				}else{
					successlist.add(arr[i]);
				}
			}
			if(errorlist.size() == 0){
			    //判断终端是否有在申请售后中
			    //获得该用户所有售后申请列表
			    List<Map<Object, Object>> list =  terminalsWebService.getCsAgentsList((Integer)map.get("customerId"), CsAgent.STSTUS_1,CsAgent.STSTUS_2);
				if(list.size()> 0){
				    for(int i=0;i<arr.length;i++){
				        for(Map<Object, Object> ma:list){
				            String[] arras = ma.get("terminals_list").toString().split(",");
				            List l = Arrays.asList(arras);
				            if(l.contains(arr[i])){
				                errorlist.add(arr[i]);
				            }
				        }
		            }
				    if(errorlist.size()>0){
				      //返回错误终端号数组
		                return Response.getErrorContext(errorlist,"终端已经提交售后申请");
				    }
				}
			    //提交数据操作
				csAgent.setStatus(CsAgent.STSTUS_1);
				csAgent.setApplyNum(String.valueOf(System.currentTimeMillis())+csAgent.getCustomerId());
				csAgent.setTerminalsQuantity(arr.length);
				terminalsWebService.submitAgent(csAgent);//添加售后信息
				map.put("agentId", csAgent.getId());
				map.put("customerId",(Integer)map.get("agentUserId"));
				terminalsWebService.addCsAgentMark(map);//物流信息
				return Response.getSuccess("提交申请成功！");
			}else{
				//返回错误终端号数组
				return Response.getErrorContext(errorlist,"终端号错误");
			}
		}catch(Exception e){
			logger.error("提交申请售后失败！", e);
			return Response.getError("请求失败！");
		}
	}
	
	/**
	 * 搜索代理商相关所有用户
	 * 
	 * @param namemap
	 * @return
	 */
	@RequestMapping(value="searchUser",method=RequestMethod.POST)
	public Response searchUser(@RequestBody Map<Object, Object> namemap){
		try{
			namemap.put("type", Customer.TYPE_CUSTOMER);
			return Response.getSuccess(terminalsWebService.searchUser(namemap));
		}catch(Exception e){
			e.printStackTrace();
			return Response.getError("系统异常");
		}
	}
	
	/**
	 * 为用户绑定
	 * @param map
	 * @return
	 */
	@RequestMapping(value="BindingTerminals",method=RequestMethod.POST)
	public Response BindingTerminals(@RequestBody Map<Object, Object> map){
		try {
			if(terminalsWebService.getTerminalsNum((String)map.get("terminalsNum"),(Integer)map.get("agentId"))==null){
				return Response.getError("终端号不存在！");
			}else{
				if(terminalsWebService.numIsBinding((String)map.get("terminalsNum"))==0){
					return Response.getError("该终端已绑定！");
				}else{
						map.put("keys", Terminal.SELFTYPES);
						terminalsWebService.Binding(map);
						return Response.getSuccess("绑定成功！");
				}
			}
		} catch (Exception e) {
			logger.error("为用户绑定失败！", e);
			return Response.getError("请求失败！");
		}
	}
	
	/**
	 * 新创建用户
	 * @param map
	 * @return
	 */
	@RequestMapping(value="addCustomer",method=RequestMethod.POST)
	public Response addCustomer(@RequestBody Map<Object, Object> map){
		try {
			CustomerAgentRelation customerAgentRelation = new CustomerAgentRelation();
			if(terminalsWebService.findUname(map)>0){
				return Response.getError("用户已存在！");
			}else{
				//添加新用户
				Customer customer = new Customer();
				customer.setUsername((String)map.get("username"));
				customer.setName((String)map.get("name"));
				customer.setPassword(SysUtils.string2MD5((String)map.get("pass1")));
				customer.setCityId((Integer)map.get("cityid"));
				customer.setTypes(Customer.TYPE_CUSTOMER);
				customer.setStatus(Customer.STATUS_NORMAL);
				customer.setIntegral(0);
				terminalsWebService.addUser(customer);
				//对该代理商已改用户绑定关系
				customerAgentRelation.setCustomerId(customer.getId());
				customerAgentRelation.setAgentId((Integer)map.get("agentId"));
				customerAgentRelation.setTypes(CustomerAgentRelation.TYPES_USER_TO_AGENT);
				customerAgentRelation.setStatus(CustomerAgentRelation.STATUS_1);
				userManagementService.addCustomerOrAgent(customerAgentRelation);
				return Response.getSuccess(customer);
			}
		} catch (Exception e) {
			logger.error("为用户绑定失败！", e);
			return Response.getError("请求失败！");
		}
	}
	
	/**
	 * 注销/更新申请获取信息(Web)
	 * 
	 * @param id
	 */
	@RequestMapping(value = "getWebApplyCancellation", method = RequestMethod.POST)
	public Response getWebApplyCancellation(@RequestBody Map<Object, Object> maps) {
		try {
			Map<Object, Object> map = new HashMap<Object, Object>();
			//获得终端详情
			map.put("applyDetails",
					terminalsWebService.getApplyDetails((Integer)maps.get("terminalsId")));
			//获得模板路径
			map.put("ReModel", terminalsWebService.getModule((Integer)maps.get("terminalsId"),(Integer)maps.get("types")));
			return Response.getSuccess(map);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getError("请求失败！");
		}
	}
	
	/**
	 * 提交注销
	 * 
	 * @param maps
	 */
	@RequestMapping(value = "subRentalReturn", method = RequestMethod.POST)
	public Response subRentalReturn(@RequestBody Map<Object, Object> maps) {
		try {
			CsCancel csCancel =new CsCancel();
			csCancel.setTerminalId((Integer)maps.get("terminalId"));
			csCancel.setStatus(CsCancel.STATUS_1);
			csCancel.setTempleteInfoXml(maps.get("templeteInfoXml").toString());
			csCancel.setTypes((Integer)maps.get("type"));
			csCancel.setCustomerId((Integer)maps.get("customerId"));
			//注销
			terminalsWebService.subRentalReturn(csCancel);
			return Response.getSuccess("操作成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getError("请求失败！");
		}
	}
	
	/**
	 * 申请更新资料
	 * 
	 * @param maps
	 */
	@RequestMapping(value = "getApplyToUpdate", method = RequestMethod.POST)
	public Response getApplyToUpdate(@RequestBody Map<Object, Object> maps) {
		try {
			maps.put("templeteInfoXml", maps.get("templeteInfoXml").toString());
			maps.put("status", CsUpdateInfo.STATUS_1);
			terminalsWebService.subToUpdate(maps);
			return Response.getSuccess("更新成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getError("请求失败！");
		}
	}
	
	
	/**
	 * 所有通道列表
	 * @return
	 */
	@RequestMapping(value="getChannels",method=RequestMethod.POST)
	public Response getChannels(){
		try{
			return Response.getSuccess(terminalsWebService.getChannels());
		}catch(Exception e){
			logger.error("获取通道列表异常！", e);
			return Response.getError("请求失败！");
		}
	}
	
	@RequestMapping(value = "noticeMaterial/{id}", method = RequestMethod.POST)
    public Response downloadZip(@PathVariable(value="id") String id,HttpServletRequest request, HttpServletResponse response) throws IOException {
		//根据终端id查的该终端开通申请资料
		List<Map<Object, Object>> list = terminalsWebService.getTerminalOpen(Integer.valueOf(id),TerminalOpeningInfo.TYPE_2);
		if(list == null){
			return Response.getError("下载失败！");
		}
		String[] str = new String[list.size()];
		for(int i=0;i<list.size();i++){
			if((String) list.get(i).get("value") !=null){
				str[i] = (String) list.get(i).get("value");
			}
		}
		int count  = HttpFile.postWar(str,id);
		if(count == 0){
			return Response.getSuccess(filePath+"zip/terminal/"+id+".zip");
		}
			return Response.getError("下载失败！");
    }
	
	/**
     * 上传开通图片文件
     * 
     * @param request
     * @param response
     * @param id
     */
    @RequestMapping(value = "upload/tempOpenImg/{id}", method = RequestMethod.POST)
    public ResponseEntity<String> tempOpenImg(@PathVariable("id") int id,@RequestParam(value="img") MultipartFile updatefile, HttpServletRequest request) {
        try {
        		String json;
        	
        		HttpHeaders responseHeaders = new HttpHeaders();
        		responseHeaders.setContentType(MediaType.TEXT_HTML);
        	
              int temp=updatefile.getOriginalFilename().lastIndexOf(".");
      			String houzuiStr=updatefile.getOriginalFilename().substring(temp+1);
              if(!commentService.typeIsCommit(houzuiStr)){
      			//return Response.getError("您所上传的文件格式不正确");
      			json="{\"message\":\"您所上传的文件格式不正确\",\"code\":\"-1\"}";
      			return new ResponseEntity<String>(json, responseHeaders, HttpStatus.OK);
      			}else{
      				if(!HttpFile.fileSize(updatefile)){
      					json="{\"message\":\"您上传的图片大小过大，请上传小于2M的图片\",\"code\":\"-1\"}";
      	      			return new ResponseEntity<String>(json, responseHeaders, HttpStatus.OK);
      				}
      			}
        		String joinpath="";
            	joinpath = HttpFile.upload(updatefile, userTerminal+id+"/opengImg/");
            	if("上传失败".equals(joinpath) || "同步上传失败".equals(joinpath)){
            		json="{\"message\":\""+joinpath+"\",\"code\":\"-1\"}";
                	return new ResponseEntity<String>(json, responseHeaders, HttpStatus.OK);
            	}else{
            		joinpath = filePath+joinpath;
            		json="{\"result\":\""+joinpath+"\",\"code\":\"1\"}";
            		//return Response.getSuccess(joinpath);
            		return new ResponseEntity<String>(json, responseHeaders, HttpStatus.OK);
            	}
            		//return Response.getError(joinpath);
        } catch (Exception e) {
        	e.printStackTrace();
        	//return Response.getError("请求失败！");
        	return new ResponseEntity<String>("{\"nessage\":\"请求失败！\",\"code\":\"-1\"}", null, HttpStatus.OK);
        }
    }
	
	 /**
     * 上传注销文件
     * 
     * @param request
     * @param response
     * @param id
     */
    @RequestMapping(value = "upload/tempCancellationFile/{id}", method = RequestMethod.POST)
    public Response tempCancellationFile(@PathVariable("id") int id,@RequestParam(value="updatefile") MultipartFile updatefile, HttpServletRequest request) {
        try {
        	//return Response.getSuccess(commentService.saveTmpImage(uploadPictureTempsPath+id+"/cancellation/",updatefile, request));
        	String joinpath="";
        	joinpath = HttpFile.upload(updatefile, sysFileTerminal+id+"/cancellation/");
        	if("上传失败".equals(joinpath) || "同步上传失败".equals(joinpath))
        		return Response.getError(joinpath);
        		return Response.getSuccess(joinpath);
        } catch (Exception e) {
        	return Response.getError("请求失败！");
        }
    }
    /**
     * 上传更新资料文件
     * 
     * @param request
     * @param response
     * @param id
     */
    @RequestMapping(value = "upload/tempUpdateFile/{id}", method = RequestMethod.POST)
    public Response tempUpdateFile(@PathVariable("id") int id,@RequestParam(value="updatefile") MultipartFile updatefile, HttpServletRequest request) {
    	try {
    		String joinpath="";
        	joinpath = HttpFile.upload(updatefile, sysFileTerminal+id+"/update/");
        	if("上传失败".equals(joinpath) || "同步上传失败".equals(joinpath))
        		return Response.getError(joinpath);
        		return Response.getSuccess(joinpath);
    	} catch (Exception e) {
    		return Response.getError("请求失败！");
    	}
    }
    
    /**
	 * 同步状态
	 */
	@RequestMapping(value = "synchronous", method = RequestMethod.POST)
	@ResponseBody
	public String syncStatus(@RequestBody Map<String, Object> map) {
		String url = timingPath + syncStatus;
		String response = null;
		try {
			response = CommonServiceUtil.synchronizeStatus(url, (Integer)map.get("terminalId"));
		} catch (IOException e) {
			logger.error("IOException...");
			return "{\"code\":-1,\"message\":\"同步失败\",\"result\":null}";
		}
		return response;
	}
}
