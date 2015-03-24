package com.comdosoft.financial.user.controller.api;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.comdosoft.financial.user.domain.Response;
import com.comdosoft.financial.user.domain.query.MailReq;
import com.comdosoft.financial.user.domain.zhangfu.Agent;
import com.comdosoft.financial.user.domain.zhangfu.Customer;
import com.comdosoft.financial.user.service.AgentLoginService;
import com.comdosoft.financial.user.service.MailService;
import com.comdosoft.financial.user.utils.SysUtils;


/**
 * 
 * 代理商登陆
 * <功能描述>
 *
 * @author xfh 2015年2月10日
 *
 */
@RestController
@RequestMapping(value = "/api/agent")
public class AgentLoginController {
	
	private static final Logger logger = Logger.getLogger(UserManagementController.class);
	
	@Resource
	private AgentLoginService agentLoginService;

	@Value("${passPath}")
	private String passPath;
	
	@Value("${sendEmailFindServicsePath}")
    private String sendEmailFindServicsePath;
	
	@Resource
    private MailService mailService;
	
	/**
	 * 代理商登陆
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "agentLogin", method = RequestMethod.POST)
	public Response agentLogin(@RequestBody Customer customer) {
		try {
			customer.setTypes(Customer.TYPE_AGENT);
			customer.setStatus(Customer.STATUS_NORMAL);
			Customer tomer = agentLoginService.doLogin(customer);
			if(tomer!=null){
				agentLoginService.updateLastLoginedAt(customer);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("customer", tomer);
				//登陆成功并且获得权限
				map.put("Machtigingen", agentLoginService.Toestemming(customer));
				return Response.getSuccess(map);
			} else {
				return Response.getError("用户名或密码错误！");
			}
		} catch (Exception e) {
			logger.error("代理商登陆异常！",e);
			return Response.getError("系统异常！");
		}
	}

	/**
	 * 发送手机验证码(找回密码)
	 * @param number
	 */
	@RequestMapping(value = "sendPhoneVerificationCode", method = RequestMethod.POST)
	public Response sendPhoneVerificationCode(@RequestBody Map<String, Object> map){
		try{
			Customer customer = new Customer();
			customer.setUsername((String)map.get("codeNumber"));
			
			String str = SysUtils.getCode();
			customer.setDentcode(str);
			if(agentLoginService.findUname(customer)>0){
				agentLoginService.updateDentcode(customer);
				Boolean is_sucess = SysUtils.sendPhoneCode("感谢您注册华尔街金融，您的验证码为："+str, (String)map.get("codeNumber"));
                if(!is_sucess){
                	return Response.getError("获取验证码失败！");
                }else{
                	return Response.getSuccess(str);
                }
			}else{
				return Response.getError("该用户不存在！");
			}
		}catch(Exception e){
			logger.error("发送手机验证码(找回密码)异常！",e);
			return Response.getError("获取验证码失败！");
		}
	}
	
	/**
	 * 发送邮箱验证(找回密码)
	 * @param number
	 */
	@RequestMapping(value = "sendEmailVerificationCode", method = RequestMethod.POST)
	public Response sendEmailVerificationCode(@RequestBody Map<String, Object> map){
		try{
			Customer customer = new Customer();
			customer.setUsername((String)map.get("codeNumber"));
   		 	MailReq req = new MailReq();
   		 	req.setUserName((String)map.get("codeNumber"));
   		 	req.setAddress((String)map.get("codeNumber"));
   		if(agentLoginService.findUname(customer)>0){
   			req.setUrl("<a href='"+sendEmailFindServicsePath+"?sendStatus=-1&sendusername="+map.get("codeNumber")+"'>找回密码！</a>");
      		 mailService.sendMailWithFilesAsynchronous(req);
      		 return Response.getSuccess("发送邮件成功！");
   		}else{
			return Response.getError("该用户不存在！");
		}
   	 }catch(Exception e){
   		logger.error("发送邮件(找回密码)异常！",e);
   		 return Response.getSuccess("发送邮件失败！");
   	 }
	}
	
	/**
	 * 找回密码
	 * @param customer
	 * @return
	 */
	@RequestMapping(value = "updatePassword", method = RequestMethod.POST)
	public Response updatePassword(@RequestBody Customer customer,HttpSession session){
		try {
			if(customer.getCode().equals(agentLoginService.findCode(customer))){
				if(agentLoginService.findUname(customer)>0){
					agentLoginService.updatePassword(customer);
					return Response.getSuccess("找回密码成功！");
				}else{
					return Response.getError("该用户不存在！");
				}
			}else{
				return Response.getError("验证码错误！");
			}
		} catch (Exception e) {
			logger.error("找回密码异常！",e);
			return Response.getError("修改失败！系统异常");
		}
	}
	
	/**
	 * 注册代理商
	 * @param customer
	 * @return
	 */
	@RequestMapping(value = "userRegistration", method = RequestMethod.POST)
	public Response userRegistration(@RequestBody Map<String, Object> map){
		try{
			//检查该代理商账号是否存在
			Customer customer = new Customer();
			Agent agent =new Agent();
			customer.setUsername((String) map.get("username"));
			if(agentLoginService.findUname(customer)>0){
				return Response.getError("用户已注册！");
			}else{
				//查找该城市中是否有状态为正常的代理商
				customer.setStatus(Customer.STATUS_NORMAL);
				customer.setCityId((Integer)map.get("cityId"));
				if(agentLoginService.judgeCityId(customer)>0){
					return Response.getError("该城市已有相关代理商！");
				}else{
					//向用户表添加数据
					customer.setPassword((String)map.get("password"));
					customer.setAccountType((Integer)map.get("accountType"));
					customer.setTypes(Customer.TYPE_AGENT);
					customer.setStatus(Customer.STATUS_NON_ACTIVE);
					customer.setPhone((String)map.get("phone"));
					customer.setEmail((String)map.get("email"));
					customer.setName((String)map.get("name"));
					customer.setIntegral(0);
					agentLoginService.addUser(customer);
					if(customer.getId()==null){
						return Response.getError("注册失败！");
					}else{
						//向代理商表添加数据
						Object ob = agentLoginService.getAgentCode(Agent.PARENT_ID);
						if(ob==null){
							agent.setCode("001");
						}else{
							String str=String.valueOf(Integer.parseInt((String)ob)+1);
							if(str.length()==1){
								str="00"+str;
							}
							if(str.length()==2){
								str="0"+str;
							}
							agent.setCode(str);
						}
						agent.setName((String)map.get("name"));
						agent.setCardId((String)map.get("cardId"));
						agent.setTypes((Integer)map.get("types"));
						agent.setCompanyName((String)map.get("companyName"));
						agent.setBusinessLicense((String)map.get("businessLicense"));
						agent.setPhone((String)map.get("phone"));
						agent.setEmail((String)map.get("email"));
						agent.setCustomerId(customer.getId());
						agent.setAddress((String)map.get("address"));
						agent.setFormTypes(Agent.FROM_TYPE_1);
						agent.setStatus(Agent.STATUS_1);
						agent.setParentId(Agent.PARENT_ID);
						agent.setIsHaveProfit(Agent.IS_HAVE_PROFIT_N);
						agent.setCardIdPhotoPath((String)map.get("cardIdPhotoPath"));
						agent.setTaxRegisteredNo((String)map.get("taxRegisteredNo"));
						agent.setLicenseNoPicPath((String)map.get("licenseNoPicPath"));
						agent.setTaxNoPicPath((String)map.get("taxNoPicPath"));
						agentLoginService.addAgent(agent);
						return Response.getSuccess("注册成功！");
					}
				}
			}
		}catch(Exception e){
			logger.error("注册代理商异常！",e);
			return Response.getError("请求失败！");
		}
		}
}
