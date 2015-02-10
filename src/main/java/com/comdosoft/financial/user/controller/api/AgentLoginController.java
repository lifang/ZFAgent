package com.comdosoft.financial.user.controller.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.comdosoft.financial.user.domain.Response;
import com.comdosoft.financial.user.domain.zhangfu.Agent;
import com.comdosoft.financial.user.domain.zhangfu.Customer;
import com.comdosoft.financial.user.service.AgentLoginService;
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
	
	@Resource
	private AgentLoginService agentLoginService;

	@Value("${passPath}")
	private String passPath;
	
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
			Object count = agentLoginService.doLogin(customer);
			if(count!=null){
				agentLoginService.updateLastLoginedAt(customer);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("customerId", count);
				//登陆成功并且获得权限
				map.put("Machtigingen", agentLoginService.Toestemming(customer));
				return Response.getSuccess(map);
			} else {
				return Response.getError("用户名或密码错误！");
			}
		} catch (Exception e) {
			return Response.getError("系统异常！");
		}
	}

	
	
	/**
	 * 发送手机验证码
	 * @param number
	 */
	@RequestMapping(value = "sendPhoneVerificationCode/{codeNumber}", method = RequestMethod.GET)
	public Response sendPhoneVerificationCode(@PathVariable("codeNumber") String codeNumber,HttpSession session){
		try{
			char[] randchar=SysUtils.getRandNum(6);
			String str ="";
			for(int i=0;i<randchar.length;i++){
				str+=randchar[i];
			}
			session.setAttribute("code", str);
			return Response.getSuccess(str);
		}catch(Exception e){
			return Response.getError("获取验证码失败！");
		}
	}
	
	/**
	 * 发送邮箱验证
	 * @param number
	 */
	@RequestMapping(value = "sendEmailVerificationCode/{codeNumber}", method = RequestMethod.GET)
	public void sendEmailVerificationCode(@PathVariable("codeNumber") String codeNumber){
		
	}
	
	/**
	 * 找回密码
	 * @param customer
	 * @return
	 */
	@RequestMapping(value = "updatePassword", method = RequestMethod.POST)
	public Response updatePassword(@RequestBody Customer customer,HttpSession session){
		try {
			if(customer.getCode().equals(session.getAttribute("code"))){
				customer.setPassword(SysUtils.Encryption(customer.getPassword(),passPath));
				if(agentLoginService.findUname(customer)>0){
					agentLoginService.updatePassword(customer);
					return Response.getSuccess("找回密码成功！");
				}else{
					return Response.getError("用户名错误！");
				}
			}else{
				return Response.getError("验证码错误！");
			}
		} catch (Exception e) {
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
				return Response.getError("用户已存在！");
			}else{
				//查找该城市中是否有状态为正常的代理商
				customer.setStatus(Customer.STATUS_NORMAL);
				customer.setCityId((Integer)map.get("cityId"));
				if(agentLoginService.judgeCityId(customer)>0){
					return Response.getError("该城市已有相关代理商！");
				}else{
					//向用户表添加数据
					customer.setPassword((String)map.get("password"));
					if((Integer)map.get("accountType")==0){
						customer.setAccountType(false);
					}
					if((Integer)map.get("accountType")==1){
						customer.setAccountType(true);
					}
					customer.setTypes(Customer.TYPE_AGENT);
					customer.setStatus(Customer.STATUS_NON_ACTIVE);
					customer.setPhone((String)map.get("phone"));
					customer.setEmail((String)map.get("email"));
					agentLoginService.addUser(customer);
					if(customer.getId()==null){
						return Response.getError("注册失败！");
					}else{
						//向代理商表添加数据
						Object ob = agentLoginService.getAgentCode();
						if(ob==null){
							agent.setCode("001");
						}else{
							String str=String.valueOf((int)ob+1);
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
						agent.setTypes((Boolean)map.get("types"));
						agent.setCompanyName((String)map.get("companyName"));
						agent.setBusinessLicense((String)map.get("businessLicense"));
						agent.setPhone((String)map.get("phone"));
						agent.setEmail((String)map.get("email"));
						agent.setCustomerId(customer.getId());
						agent.setAddress((String)map.get("address"));
						agent.setFormTypes(Agent.FROM_TYPE_RE);
						agent.setStatus(Agent.STATUS_NON_ACTIVE);
						agent.setParentId(0);
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
			return Response.getError("请求失败！");
		}
		}
	
	/**
	 * 材料图片上传
	 * 
	 * @param filePath
	 * @return
	 */
	@RequestMapping(value = "uploadFile", method = RequestMethod.POST)
	@ResponseBody
	public Response uploadFile(@RequestBody Map<String, String> map,
			HttpServletRequest request) {
		try {
			String filePath = null;
			Set<String> keys = map.keySet();
			for (String str : keys) {
				filePath = map.get(str);
			}
			String fileOutPath = request.getServletContext().getRealPath("/")
					+ "WEB-INF/tempAgents/";
			int byteread = 0;// 读取的位数
			FileInputStream in = null;
			FileOutputStream out = null;
			File fileIn = new File(filePath);
			File fileOut = new File(fileOutPath);

			if (!fileOut.exists()) {
				fileOut.mkdirs();
			}
			fileOut = new File(fileOutPath
					+ filePath.substring(filePath.lastIndexOf("\\") + 1));
			if (!fileIn.exists()) {
				return Response.getSuccess("该路径图片不存在！");
			}
			try {
				in = new FileInputStream(fileIn);
				out = new FileOutputStream(fileOut);
				byte[] buffer = new byte[1024];
				while ((byteread = in.read(buffer)) != -1) {
					// 将读取的字节写入输出流
					out.write(buffer, 0, byteread);
				}
				return Response.getSuccess(fileOut);
			} catch (Exception e) {
				return Response.getSuccess("上传失败！");
			} finally {
				try {
					if (out != null) {
						out.close();
					}
					if (in != null) {
						in.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			return Response.getError("请求失败！");
		}
	}

}
