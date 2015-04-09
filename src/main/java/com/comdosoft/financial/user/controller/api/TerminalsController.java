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
import org.springframework.web.bind.annotation.RestController;

import com.comdosoft.financial.user.domain.Response;
import com.comdosoft.financial.user.domain.zhangfu.CsAgent;
import com.comdosoft.financial.user.domain.zhangfu.Customer;
import com.comdosoft.financial.user.service.OpeningApplyService;
import com.comdosoft.financial.user.service.TerminalsService;
import com.comdosoft.financial.user.service.UserManagementService;
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
					offSetPage,
					(Integer)map.get("rows"),
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
					offSetPage,
					(Integer)map.get("rows"),
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
					(Integer)map.get("customerId"),
					offSetPage,
					(Integer)map.get("rows")));
			applyMap.put("total", terminalsService.getMerchantSize(
					(Integer)map.get("customerId"),
					offSetPage,
					(Integer)map.get("rows")));
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
                if (terminalsService.findUnameAndStatus(customer) == 0) {
                    return Response.getError("该用户已注册！");
                } else {
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
			 Customer customer = new Customer();
			 customer.setUsername((String)map.get("codeNumber"));
			if (terminalsService.findUnameAndStatus(customer) > 0) {
				return Response.getError("用户已存在！");
			} else {
				// 添加新用户
				customer.setName((String) map.get("name"));
				customer.setPassword((String) map.get("password"));
				customer.setCityId((Integer) map.get("cityId"));
				customer.setTypes(Customer.TYPE_CUSTOMER);
				customer.setStatus(Customer.STATUS_NORMAL);
				customer.setIntegral(0);
				userManagementService.addUser(customer);
				return Response.getSuccess(customer);
			}
		} catch (Exception e) {
			logger.error("为用户绑定失败！", e);
			return Response.getError("请求失败！");
		}
	}


	
	/**
	 * 选择终端号
	 * @param customerId
	 * @return
	 */
	//@RequestMapping(value="getTerminal/{customerId}",method=RequestMethod.GET)
	/*public Response getTerminal(@PathVariable("customerId") Integer customerId){
		try{
			return Response.getSuccess(terminalsService.getTerminal(customerId));
		}catch(Exception e){
			return Response.getError("请求失败！");
		}
	}*/
	
	/**
	 * 筛选终端（pos机，通道，价格）
	 * @param map
	 * @return
	 */
	@RequestMapping(value="screeningTerminalNum",method=RequestMethod.POST)
	public Response screeningTerminalNum(@RequestBody Map<Object, Object> map){
		try{
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
					int count = terminalsService.checkTerminalCode(arr.get(i));//该终端号是否存在
					int num = terminalsService.checkTerminalCodeOpen(arr.get(i));//该终端号是否存在
					if(count == 0){
						errorlist.add(arr.get(i));
					}else if(num != 0){
						errorlist.add(arr.get(i));
					}else{
						successlist.add(arr.get(i));
					}
				}
				if(errorlist.size() == 0){
					List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
					for(int i=0;i<arr.size();i++){
						list.add(terminalsService.getTerminalArray(arr.get(i).toString()));
					}
					return Response.getSuccess(list);
				}else{
					//返回错误终端号数组
					Map<Object, Object> listMap = new HashMap<Object, Object>();
					listMap.put("errorlist", errorlist);
					listMap.put("successlist", successlist);
					return Response.getErrorContext(listMap);
				}
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
			return Response.getSuccess(terminalsService.getAddressee((Integer)map.get("customerId")));
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
		try{
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
	 * 同步
	 */
	@RequestMapping(value = "synchronous", method = RequestMethod.POST)
	public Response Synchronous() {
		try {
			return Response.getSuccess("同步成功！");
		} catch (Exception e) {
			logger.error("同步异常！", e);
			return Response.getError("同步失败！");
		}
	}

}
