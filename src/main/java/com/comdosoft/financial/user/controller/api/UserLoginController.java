package com.comdosoft.financial.user.controller.api;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.comdosoft.financial.user.domain.Response;
import com.comdosoft.financial.user.domain.zhangfu.Customer;
import com.comdosoft.financial.user.service.UserLoginService;
import com.comdosoft.financial.user.utils.SysUtils;

/**
 * 
 * 用户登陆
 * <功能描述>
 *
 * @author xfh 2015年2月7日
 *
 */
@RestController
@RequestMapping(value = "/api/user")
public class UserLoginController {
	
	@Resource
	private UserLoginService userLoginService;

	@Value("${passPath}")
	private String passPath;
	
	/**
	 * 用户登陆
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "studentLogin", method = RequestMethod.POST)
	public Response studentLogin(@RequestBody Customer customer) {
		try {
			customer.setPassword(SysUtils.Encryption(customer.getPassword(),passPath));
			int count = userLoginService.doLogin(customer);
			if(count>0){
				userLoginService.updateLastLoginedAt(customer);
				return Response.getSuccess("登陆成功！");
			}else if(count==0){
				return Response.getError("用户名或密码错误！");
			}else{
				return Response.getError("异常登录！");
			}
		} catch (Exception e) {
			return Response.getError("系统异常！");
		}
	}

	/**
	 * 发送手机验证码
	 * @param number
	 */
	@RequestMapping(value = "sendPhoneVerificationCode/codeNumber", method = RequestMethod.GET)
	public void sendPhoneVerificationCode(@PathVariable("number") String number){
			
	}
	
	/**
	 * 发送邮箱验证
	 * @param number
	 */
	@RequestMapping(value = "sendEmailVerificationCode/codeNumber", method = RequestMethod.GET)
	public void sendEmailVerificationCode(@PathVariable("number") String number){
		
	}
	
	/**
	 * 找回密码
	 * @param customer
	 * @return
	 */
	@RequestMapping(value = "updatePassword", method = RequestMethod.POST)
	public Response updatePassword(@RequestBody Customer customer){
		try {
			customer.setPassword(SysUtils.Encryption(customer.getPassword(),passPath));
			if(userLoginService.findUname(customer)>0){
				userLoginService.updatePassword(customer);
				return Response.getSuccess("找回密码成功！");
			}else{
				return Response.getError("用户名错误！");
			}
		} catch (Exception e) {
			return Response.getError("修改失败！系统异常");
		}
	}
	
	/**
	 * 注册用户
	 * @param customer
	 * @return
	 */
	@RequestMapping(value = "userRegistration", method = RequestMethod.POST)
	public Response userRegistration(@RequestBody Customer customer){
		try {
			customer.setPassword(SysUtils.Encryption(customer.getPassword(),passPath));
			customer.setTypes(Customer.TYPE_CUSTOMER);
			if(userLoginService.findUname(customer)==0){
				if(!customer.getAccountType()){
					customer.setPhone(customer.getUsername());
				}else{
					customer.setEmail(customer.getUsername());
				}
				userLoginService.addUser(customer);
				return Response.getSuccess("注册成功！");
			}else{
				return Response.getError("用户已存在！");
			}
		} catch (Exception e) {
			return Response.getError("注册失败！系统异常");
		}
	}
}
