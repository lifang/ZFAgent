package com.comdosoft.financial.user.controller.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.comdosoft.financial.user.domain.Response;
import com.comdosoft.financial.user.domain.zhangfu.CsAgent;
import com.comdosoft.financial.user.domain.zhangfu.Customer;
import com.comdosoft.financial.user.domain.zhangfu.CustomerAgentRelation;
import com.comdosoft.financial.user.domain.zhangfu.Terminal;
import com.comdosoft.financial.user.service.OpeningApplyService;
import com.comdosoft.financial.user.service.TerminalsService;
import com.comdosoft.financial.user.service.UserManagementService;
import com.comdosoft.financial.user.utils.CommonServiceUtil;
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
@RequestMapping(value = "/api/terminal")
public class TerminalsController {
	 private static final Logger logger = LoggerFactory.getLogger(TerminalsController.class);
	
	@Resource
	private TerminalsService terminalsService;
	
	@Resource
	private OpeningApplyService openingApplyService;
	
	@Resource
	private UserManagementService userManagementService;
	
	@Value("${syncStatus}")
	private String syncStatus;
	
	@Value("${timingPath}")
	private String timingPath;
	

	@Value("${passPath}")
	private String passPath;

	/**
	 * 根据代理商ID获得终端列表
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "getApplyList", method = RequestMethod.POST)
	public Response getApplyList(@RequestBody Map<String, Object> map) {
		try {
			Integer status = -1; 
			PageRequest PageRequest = new PageRequest(
					(Integer)map.get("page"),
					(Integer)map.get("rows"));
			int offSetPage = PageRequest.getOffset();
			Map<Object, Object> applyMap = new HashMap<Object, Object>();
			applyMap.put("terminalList", terminalsService.getTerminalList(
					(Integer)map.get("agentId"),
					offSetPage,
					(Integer)map.get("rows"),
					status));
			applyMap.put("total", terminalsService.getTerminalListSize(
					(Integer)map.get("agentId"),
					status,null));
			return Response.getSuccess(applyMap);
		} catch (Exception e) {
			logger.error("根据用户ID获得终端列表异常！", e);
			return Response.getError("请求失败！");
		}
	}
	
	/**
	 * 根据状态/终端号选择查询
	 * @param status
	 * @param customersId
	 * @param page
	 * @param pageNum
	 * @return
	 */
	@RequestMapping(value="getTerminalList",method=RequestMethod.POST)
	public Response getTerminalList(@RequestBody Map<String, Object> map){
		try {
			PageRequest PageRequest = new PageRequest(
					(Integer)map.get("page"),
					(Integer)map.get("rows"));
			int offSetPage = PageRequest.getOffset();
			Map<Object, Object> applyMap = new HashMap<Object, Object>();
			
			applyMap.put("applyList", terminalsService.getNewTerminalList(
					(Integer)map.get("agentId"),
					offSetPage,
					(Integer)map.get("rows"),
					(Integer)map.get("status"),
					(String)map.get("serialNum")));
			applyMap.put("total", terminalsService.getTerminalListSize(
					(Integer)map.get("agentId"),
					(Integer)map.get("status"),
					(String)map.get("serialNum")));
			return Response.getSuccess(applyMap);
		} catch (Exception e) {
			logger.error("根据状态选择查询异常！", e);
			return Response.getError("请求失败！");
		}
	}
	
	/**
	 * 进入终端详情
	 * 
	 * @param id
	 */
	@RequestMapping(value = "getApplyDetails", method = RequestMethod.POST)
	public Response getApplyDetails(@RequestBody Map<String, Object> maps) {
		try {
			Map<Object, Object> map = new HashMap<Object, Object>();
			// 获得终端详情
			map.put("applyDetails",
					terminalsService.getApplyDetails((Integer)maps.get("terminalsId")));
			// 终端交易类型
			map.put("rates", terminalsService.getRate((Integer)maps.get("terminalsId")));
			// 追踪记录
			map.put("trackRecord", terminalsService.getTrackRecord((Integer)maps.get("terminalsId")));
			// 开通详情
			map.put("openingDetails",
					terminalsService.getOpeningDetails((Integer)maps.get("terminalsId")));
			// 获得已有申请开通基本信息
						map.put("openingInfos",
								openingApplyService.getOppinfo((Integer)maps.get("terminalsId")));
			return Response.getSuccess(map);
		} catch (Exception e) {
			logger.error("进入终端详情失败！", e);
			return Response.getError("请求失败！");
		}
	}

	/**
	 * 获得代理商下面的用户
	 * @param customerId
	 * @return
	 */
	@RequestMapping(value="getMerchants",method=RequestMethod.POST)
	public Response getMerchants(@RequestBody Map<String, Object> map){
		try {
			PageRequest PageRequest = new PageRequest((Integer)map.get("page"),
					(Integer)map.get("rows"));
			int offSetPage = PageRequest.getOffset();
			Map<Object, Object> applyMap = new HashMap<Object, Object>();
			applyMap.put("terminalList", terminalsService.getMerchants(
					(Integer)map.get("terminalId"),
					(String)map.get("title"),
					offSetPage,
					(Integer)map.get("rows")));
			applyMap.put("total", terminalsService.getMerchantSize(
					(Integer)map.get("terminalId"),
					(String)map.get("title")));
			return Response.getSuccess(applyMap);
		} catch (Exception e) {
			logger.error("获得代理商下面的用户失败！", e);
			return Response.getError("请求失败！");
		}
	}
	
	/**
	 * 为用户绑定
	 * @param map
	 * @return
	 */
	@RequestMapping(value="bindingTerminals",method=RequestMethod.POST)
	public Response BindingTerminals(@RequestBody Map<Object, Object> map){
		try {
			if(terminalsService.getTerminalsNum((String)map.get("terminalsNum"))==null){
				return Response.getError("终端号不存在！");
			}else{
				if(terminalsService.numIsBinding((String)map.get("terminalsNum"))==0){
					return Response.getError("该终端已绑定！");
				}else{
						map.put("keys", Terminal.SELFTYPES);
						terminalsService.Binding(map);
						return Response.getSuccess("绑定成功！");
				}
			}
		} catch (Exception e) {
			logger.error("为用户绑定失败！", e);
			return Response.getError("请求失败！");
		}
	}
	
	 /**
     * 发送手机验证码(添加用户)
     * 
     * @param number
     */
    @RequestMapping(value = "sendPhoneVerificationCodeReg", method = RequestMethod.POST)
    public Response sendPhoneVerificationCodeReg(@RequestBody Map<String, Object> map) {
        try {
            Customer customer = new Customer();
            customer.setUsername((String)map.get("codeNumber") );
            String str = SysUtils.getCode();
            customer.setPassword("000000");
            customer.setCityId(0);
            customer.setDentcode(str);
            customer.setStatus(Customer.STATUS_NON_END);
            String phone = (String)map.get("codeNumber");//手机号
            if (terminalsService.findUname(customer) == 0) {
            try {
                Boolean is_sucess = SysUtils.sendPhoneCode("感谢您注册华尔街金融，您的验证码为："+str, phone);
                if(!is_sucess){
                	return Response.getError("获取验证码失败！");
                }else{
                	// 添加假状态
                	terminalsService.addUser(customer);
                    return Response.getSuccess(str);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return Response.getError("系统异常！");
            }
            } else {
            	Map<Object, Object> m = terminalsService.findUnameAndStatus(customer);
                if (Integer.valueOf((String) m.get("id")) == -1) {
                    return Response.getError("该用户已注册！");
                } else {
                	 Boolean is_sucess = SysUtils.sendPhoneCode("感谢您注册华尔街金融，您的验证码为："+str, phone);
                     if(!is_sucess){
                     	return Response.getError("获取验证码失败！");
                     }
                	terminalsService.updateCode(customer);
                    return Response.getSuccess(str);
                }
            }
        } catch (Exception e) {
        	e.printStackTrace();
            return Response.getError("系统异常！");
        }
    }
    
    /**
	 * 新创建用户
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "addCustomer", method = RequestMethod.POST)
	public Response addCustomer(@RequestBody Map<Object, Object> map) {
		try {
			CustomerAgentRelation customerAgentRelation = new CustomerAgentRelation();
			 Customer customer = new Customer();
			 customer.setUsername((String)map.get("codeNumber"));
			 customer.setStatus(Customer.STATUS_NON_END);
			 customer.setDentcode((String)map.get("code"));
			 Map<Object, Object> m = terminalsService.findUnameAndStatusCode(customer);
			 if(m != null){
			if (Integer.valueOf((String) m.get("id")) > 0) {
				// 修改添加新用户
				customer.setName((String) map.get("name"));
				customer.setId(Integer.valueOf(m.get("id").toString()));
				customer.setPassword((String) map.get("password"));
				customer.setCityId((Integer) map.get("cityId"));
				customer.setTypes(Customer.TYPE_CUSTOMER);
				customer.setStatus(Customer.STATUS_NORMAL);
				customer.setIntegral(0);
				userManagementService.updateCustomer(customer);
				//对该代理商已改用户绑定关系
				customerAgentRelation.setCustomerId(Integer.valueOf((String) m.get("id")));
				customerAgentRelation.setAgentId((Integer)map.get("agentId"));
				customerAgentRelation.setTypes(CustomerAgentRelation.TYPES_USER_TO_AGENT);
				customerAgentRelation.setStatus(CustomerAgentRelation.STATUS_1);
				userManagementService.addCustomerOrAgent(customerAgentRelation);
				return Response.getSuccess(customer);
			} 
			 }
				return Response.getError("验证码错误！");
		} catch (Exception e) {
			logger.error("为用户绑定失败！", e);
			return Response.getError("请求失败！");
		}
	}

	
	/**
	 * 筛选终端（pos机，通道，价格）
	 * @param map
	 * @return
	 */
	@RequestMapping(value="screeningTerminalNum",method=RequestMethod.POST)
	public Response screeningTerminalNum(@RequestBody Map<Object, Object> map){
		try{
			PageRequest PageRequest = new PageRequest(
					(Integer)map.get("page"),
					(Integer)map.get("rows"));
			int offSetPage = PageRequest.getOffset();
			map.put("offSetPage", offSetPage);
			map.put("pageSize", (Integer)map.get("rows"));
			return Response.getSuccess(terminalsService.screeningTerminalNum(map));
		}catch(Exception e){
			logger.error("筛选终端失败！", e);
			return Response.getError("请求失败！");
		}
	}
	
	/**
	 * 批量终端号筛选终端
	 * @param map
	 * @return
	 */
	@RequestMapping(value="batchTerminalNum",method=RequestMethod.POST)
	public Response batchTerminalNum(@RequestBody Map<Object, Object> map){
		try{
			
			List<String> errorlist = new ArrayList<String>();//错误终端号数据
			List<String> successlist = new ArrayList<String>();//正确终端号数据
			try{
				
				@SuppressWarnings("unchecked")
				List<String> arr= (List<String>) map.get("serialNum");
				
				for(int i=0;i<arr.size();i++){
					int count = terminalsService.checkTerminalCode(arr.get(i),(Integer)map.get("agentId"),Terminal.TerminalTYPEID_4,Terminal.TerminalTYPEID_5);//该终端号是否存在
					int num = terminalsService.checkTerminalCodeOpen(arr.get(i));//该终端号是否售后
					if(count == 0){
						errorlist.add(arr.get(i));
					}else if(num != 0){
						errorlist.add(arr.get(i));
					}else{
						successlist.add(arr.get(i));
					}
				}
					List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
					for(int i=0;i<successlist.size();i++){
						list.add(terminalsService.getTerminalArray(successlist.get(i).toString()));
					}
					return Response.getSuccess(list);
			}catch(Exception e){
				logger.error("提交申请售后失败！", e);
				return Response.getError("请求失败！");
			}
		}catch(Exception e){
			logger.error("筛选终端失败！", e);
			return Response.getError("请求失败！");
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
			return Response.getSuccess(terminalsService.getAddressee((Integer)map.get("customerId")));//代理商对应用户id
		}catch(Exception e){
			logger.error("收件人信息异常！", e);
			return Response.getError("请求失败！");
		}
	}
	
	/**
	 * 提交申请售后
	 * @param customerId
	 * @return
	 */
	@RequestMapping(value="submitAgent",method=RequestMethod.POST)
	public Response submitAgent(@RequestBody CsAgent csAgent){
		try{
			csAgent.setStatus(CsAgent.STSTUS_1);
			csAgent.setApplyNum(String.valueOf(System.currentTimeMillis())+csAgent.getCustomerId());
			terminalsService.submitAgent(csAgent);
			return Response.getSuccess("提交申请成功！");
		}catch(Exception e){
			logger.error("提交申请售后失败！", e);
			return Response.getError("请求失败！");
		}
	}
	
	/**
	 * POS机选择
	 * @param customerId
	 * @return
	 */
	@RequestMapping(value="screeningPosName",method=RequestMethod.POST)
	public Response screeningPosName(@RequestBody Map<String, Object> map){
		try{//代理商对应用户id
			return Response.getSuccess(terminalsService.screeningPosName((Integer)map.get("customerId")));
		}catch(Exception e){
			logger.error("POS机选择失败！", e);
			return Response.getError("请求失败！");
		}
	}
	
	/**
	 * 获得所有通道
	 */
	@RequestMapping(value = "getChannels", method = RequestMethod.POST)
	public Response getChannels() {
		try {
			//支付通道和周期列表
			List<Map<Object, Object>> list = openingApplyService.getChannels();
			 for(Map<Object, Object> m:list){
				 List<Map<Object, Object>> listT = openingApplyService.channelsT(Integer.parseInt(m.get("id").toString()));
				 if(listT == null){
					 m.put("billings","");
				 }else if(listT != null){
					 m.put("billings",listT);
				 }
			 }
			return Response.getSuccess(list);
		} catch (Exception e) {
			logger.error("获得所有通道异常！",e);
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
		
		/**
		 * 找回POS机密码
		 * 
		 * @param id
		 * @return
		 */
		@RequestMapping(value = "encryption", method = RequestMethod.POST)
		public Response Encryption(@RequestBody Map<String, Object> map) {
			try {
				String  password= terminalsService.findPassword((Integer)map.get("terminalid")) == null?null:
					terminalsService.findPassword((Integer)map.get("terminalid"));
				String pass = "该终端未设置密码！";
				if(password != null){
					/*pass = SysUtils.Decrypt(
							password,passPath);*/
					pass = password;
				}
				return Response.getSuccess(pass);
			} catch (Exception e) {
				e.printStackTrace();
				return Response.getError("请求失败!");
			}
		}

}
