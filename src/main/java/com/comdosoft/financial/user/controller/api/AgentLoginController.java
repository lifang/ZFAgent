package com.comdosoft.financial.user.controller.api;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.comdosoft.financial.user.domain.Response;
import com.comdosoft.financial.user.domain.query.MailReq;
import com.comdosoft.financial.user.domain.zhangfu.Agent;
import com.comdosoft.financial.user.domain.zhangfu.Customer;
import com.comdosoft.financial.user.service.AgentLoginService;
import com.comdosoft.financial.user.service.CommentService;
import com.comdosoft.financial.user.service.MailService;
import com.comdosoft.financial.user.utils.SysUtils;

/**
 * 
 * 代理商登陆 <功能描述>
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
	
	@Autowired
	private CommentService commentService ;
	
	@Value("${uploadPictureTempsPath}")
    private String uploadPictureTempsPath;

	@Value("${uploadPictureTempsRegisterPath}")
	private String uploadPictureTempsRegisterPath;
	
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
			customer.setStatusEnd(Customer.TYPE_AGENT_STAFF);
			Map<Object, Object> customerMes = new HashMap<Object, Object>();
			// 先判断是代理商还是员工
			Map<Object, Object> obj = agentLoginService.isAgentOrPerson(customer.getUsername(), customer.getStatus().toString(), Customer.TYPE_AGENT.toString(), Customer.TYPE_AGENT_STAFF.toString());
			if (obj == null) {
				return Response.getError("用户名不存在,或者未激活！");
			} else {

				if (obj.get("types") == Customer.TYPE_AGENT_STAFF) {// 员工
					customerMes = agentLoginService.doLoginPersn(customer);
				} else if (obj.get("types") == Customer.TYPE_AGENT) {// 代理商
					customerMes = agentLoginService.doLogin(customer);
				} else {
					customerMes = null;
				}
				if (customerMes != null) {
					agentLoginService.updateLastLoginedAt(customer.getUsername(), customer.getStatus().toString(), Customer.TYPE_AGENT.toString(), Customer.TYPE_AGENT_STAFF.toString());
					// 登陆成功并且获得权限
					customer.setId((Integer) customerMes.get("id"));
					customerMes.put("machtigingen", agentLoginService.Toestemming(customer) == null ? "" : agentLoginService.Toestemming(customer));
					return Response.getSuccess(customerMes);
				} else {
					return Response.getError("用户名/密码错误！账号不可用！");
				}
			}
		} catch (Exception e) {
			logger.error("代理商登陆异常！", e);
			return Response.getError("系统异常！");
		}
	}

	/**
	 * 发送手机验证码(找回密码)
	 * 
	 * @param number
	 */
	@RequestMapping(value = "sendPhoneVerificationCode", method = RequestMethod.POST)
	public Response sendPhoneVerificationCode(@RequestBody Map<String, Object> map) {
		try {
			Customer customer = new Customer();
			customer.setUsername((String) map.get("codeNumber"));
			customer.setStatus(Customer.STATUS_NORMAL);

			String str = SysUtils.getCode();
			customer.setDentcode(str);
			if (agentLoginService.findUname(customer.getUsername(), customer.getStatus().toString(), Customer.TYPE_AGENT.toString(), Customer.TYPE_AGENT_STAFF.toString()) > 0) {
				agentLoginService.updateDentcode(customer);
				Boolean is_sucess = SysUtils.sendPhoneCode("感谢您注册华尔街金融，您的验证码为：" + str, (String) map.get("codeNumber"));
				if (!is_sucess) {
					return Response.getError("获取验证码失败！");
				} else {
					return Response.getSuccess(str);
				}
			} else {
				return Response.getError("该用户不存在！");
			}
		} catch (Exception e) {
			logger.error("发送手机验证码(找回密码)异常！", e);
			return Response.getError("获取验证码失败！");
		}
	}

	/**
	 * 发送邮箱验证(找回密码)
	 * 
	 * @param number
	 */
	@RequestMapping(value = "sendEmailVerificationCode", method = RequestMethod.POST)
	public Response sendEmailVerificationCode(@RequestBody Map<String, Object> map) {
		try {
			Customer customer = new Customer();
			customer.setUsername((String) map.get("codeNumber"));
			customer.setStatus(Customer.STATUS_NORMAL);
			MailReq req = new MailReq();
			req.setUserName((String) map.get("codeNumber"));
			req.setAddress((String) map.get("codeNumber"));
			if (agentLoginService.findEmail(customer.getUsername(), customer.getStatus().toString(), Customer.TYPE_AGENT.toString(), Customer.TYPE_AGENT_STAFF.toString()) > 0) {
				req.setUrl("<a href='" + sendEmailFindServicsePath + "?sendStatus=-1&sendusername=" + map.get("codeNumber") + "'>找回密码！</a>");
				mailService.sendMailWithFilesAsynchronous(req);
				return Response.getSuccess("发送邮件成功！");
			} else {
				return Response.getError("该用户不存在！");
			}
		} catch (Exception e) {
			logger.error("发送邮件(找回密码)异常！", e);
			return Response.getError("发送邮件失败！");
		}
	}

	/**
	 * 找回密码
	 * 
	 * @param customer
	 * @return
	 */
	@RequestMapping(value = "updatePassword", method = RequestMethod.POST)
	public Response updatePassword(@RequestBody Customer customer, HttpSession session) {
		try {
			customer.setStatus(Customer.STATUS_NORMAL);
			if (customer.getCode().equals(agentLoginService.findCode(customer.getUsername(), customer.getStatus().toString(), Customer.TYPE_AGENT.toString(), Customer.TYPE_AGENT_STAFF.toString()))) {

				if (agentLoginService.findUname(customer.getUsername(), customer.getStatus().toString(), Customer.TYPE_AGENT.toString(), Customer.TYPE_AGENT_STAFF.toString()) > 0) {
					agentLoginService.updatePassword(customer.getPassword(), customer.getUsername(), customer.getStatus().toString(), Customer.TYPE_AGENT.toString(), Customer.TYPE_AGENT_STAFF.toString());
					return Response.getSuccess("找回密码成功！");
				} else {
					return Response.getError("该用户不存在！");
				}
			} else {
				return Response.getError("验证码错误！");
			}
		} catch (Exception e) {
			logger.error("找回密码异常！", e);
			return Response.getError("修改失败！系统异常");
		}
	}
	
	/**
     * 上传注册图片文件
     * 
     * @param request
     * @param response
     * @param id
     */
    @RequestMapping(value = "upload/register", method = RequestMethod.POST)
    public Response tempOpenImg(@RequestParam(value="img") MultipartFile updatefile, HttpServletRequest request) {
        try {
        	return Response.getSuccess(commentService.saveTmpImage(uploadPictureTempsRegisterPath,updatefile, request));
        } catch (IOException e) {
        	return Response.getError("请求失败！");
        }
    }

	/**
	 * 注册代理商
	 * 
	 * @param customer
	 * @return
	 */
	@RequestMapping(value = "userRegistration", method = RequestMethod.POST)
	public Response userRegistration(@RequestBody Map<String, Object> map) {
		try {
			// 检查该代理商账号是否存在
			Customer customer = new Customer();
			Agent agent = new Agent();
			customer.setUsername((String) map.get("username"));
			/*
			 * if(agentLoginService.findUname(customer)>0){ return
			 * Response.getError("用户已注册！"); }else{
			 */
			// 查找该城市中是否有状态为正常的代理商
			customer.setTypes(Customer.TYPE_AGENT);
			customer.setStatus(Customer.STATUS_NORMAL);
			customer.setCityId((Integer) map.get("cityId"));
			customer.setPhone((String) map.get("phone"));
			customer.setEmail((String) map.get("email"));
			if (agentLoginService.judgePhone(customer) > 0) {
				// 检查手机号和邮箱是否有存在的正常代理商
				return Response.getError("该手机已使用！");
			}
			if (agentLoginService.judgeEmail(customer) > 0) {
				// 检查手机号和邮箱是否有存在的正常代理商
				return Response.getError("该邮箱已使用！");
			}
			if (agentLoginService.judgeCityId(customer) > 0) {
				return Response.getError("该城市已有相关代理商！");
			} else {
				// 向用户表添加数据
				customer.setPassword((String) map.get("password"));
				// customer.setAccountType((Integer)map.get("accountType"));
				customer.setTypes(Customer.TYPE_AGENT);
				customer.setStatus(Customer.STATUS_NON_ACTIVE);
				customer.setPhone((String) map.get("phone"));
				customer.setEmail((String) map.get("email"));
				customer.setName((String) map.get("name"));
				customer.setIntegral(0);
				agentLoginService.addUser(customer);
				if (customer.getId() == null) {
					return Response.getError("注册失败！");
				} else {
					// 向代理商表添加数据
					Object ob = agentLoginService.getAgentCode(Agent.PARENT_ID);
					if (ob == null) {
						agent.setCode("001");
					} else {
						String str = String.valueOf(Integer.parseInt((String) ob) + 1);
						if (str.length() == 1) {
							str = "00" + str;
						}
						if (str.length() == 2) {
							str = "0" + str;
						}
						agent.setCode(str);
					}
					agent.setName((String) map.get("name"));
					agent.setCardId((String) map.get("cardId"));
					agent.setTypes((Integer) map.get("types"));

					agent.setPhone((String) map.get("phone"));
					agent.setEmail((String) map.get("email"));
					agent.setCustomerId(customer.getId());
					agent.setAddress((String) map.get("address"));
					agent.setFormTypes(Agent.FROM_TYPE_1);
					agent.setStatus(Agent.STATUS_1);
					agent.setParentId(Agent.PARENT_ID);
					agent.setIsHaveProfit(Agent.IS_HAVE_PROFIT_N);
					agent.setCardIdPhotoPath((String) map.get("cardIdPhotoPath"));
					if (agent.getTypes() == 1) {// 公司选项多出几个
						agent.setCompanyName((String) map.get("companyName"));
						agent.setBusinessLicense((String) map.get("businessLicense"));
						agent.setTaxRegisteredNo((String) map.get("taxRegisteredNo"));
						agent.setLicenseNoPicPath((String) map.get("licenseNoPicPath"));
						agent.setTaxNoPicPath((String) map.get("taxNoPicPath"));
					}
					agentLoginService.addAgent(agent);
					return Response.getSuccess("注册成功！");
				}
			}
			// }
		} catch (Exception e) {
			logger.error("注册代理商异常！", e);
			return Response.getError("请求失败！");
		}
	}

	/**
	 * 获取验证码（登录用）
	 * 
	 * @param response
	 */
	@RequestMapping("getRandCodeImg")
	public void getRandCodeImg(HttpServletResponse response, HttpSession session) {

		// 设置页面不缓存数据
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		// 获取4位随机验证码
		char[] randNum = SysUtils.getRandNum(4);
		String randNumStr = new String(randNum);

		// 将验证码存入session
		session.setAttribute("imageCode", randNumStr);

		// 生成验证码图片
		BufferedImage image = SysUtils.generateRandImg(randNum);

		try {// 输出图象到页面
			ImageIO.write(image, "JPEG", response.getOutputStream());
		} catch (IOException ioEx) {
			// logger.error("验证码图片显示异常", ioEx);
		}
	}

	/**
	 * (pc端，含有图片验证码校验)
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "sizeUpImgCode", method = RequestMethod.POST)
	public Response sizeUpImgCode(@RequestBody Map<String, String> map, HttpSession session) {
		try {
			if ((String) session.getAttribute("imageCode") == null) {
				return Response.getError("图片验证码错误！");
			} else {
				if (((String) session.getAttribute("imageCode")).equalsIgnoreCase(map.get("imgnum"))) {
					return Response.getSuccess("true");
				} else {
					return Response.getError("图片验证码错误！");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getError("系统异常！");
		}
	}

	/**
	 * 代理商登陆(web)
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "agentLoginWeb", method = RequestMethod.POST)
	public Response agentLoginWeb(@RequestBody Customer customer) {
		try {
			customer.setTypes(Customer.TYPE_AGENT);
			customer.setStatus(Customer.STATUS_NORMAL);
			customer.setStatusEnd(Customer.TYPE_AGENT_STAFF);
			customer.setPassword(SysUtils.string2MD5(customer.getPassword()));

			Map<Object, Object> customerMes = new HashMap<Object, Object>();
			// 先判断是代理商还是员工
			Map<Object, Object> obj = agentLoginService.isAgentOrPerson(customer.getUsername(), customer.getStatus().toString(), Customer.TYPE_AGENT.toString(), Customer.TYPE_AGENT_STAFF.toString());
			if (obj == null) {
				return Response.getError("用户名不存在,或者未激活！");
			} else {
				if (obj.get("types") == Customer.TYPE_AGENT_STAFF) {// 员工
					customerMes = agentLoginService.doLoginPersn(customer);
				} else if (obj.get("types") == Customer.TYPE_AGENT) {// 代理商
					customerMes = agentLoginService.doLogin(customer);
				} else {
					customerMes = null;
				}
				if (customerMes != null) {
					agentLoginService.updateLastLoginedAt(customer.getUsername(), customer.getStatus().toString(), Customer.TYPE_AGENT.toString(), Customer.TYPE_AGENT_STAFF.toString());
					// 登陆成功并且获得权限
					customer.setId((Integer) customerMes.get("id"));
					customerMes.put("machtigingen", agentLoginService.Toestemming(customer) == null ? "" : agentLoginService.Toestemming(customer));
					return Response.getSuccess(customerMes);
				} else {
					return Response.getError("用户名/密码错误！账号不可用！");
				}
			}
		} catch (Exception e) {
			logger.error("代理商登陆异常！", e);
			return Response.getError("系统异常！");
		}
	}

	/**
	 * (找回密码web端第一步)
	 * 
	 * @param number
	 */
	@RequestMapping(value = "getFindUser", method = RequestMethod.POST)
	public Response getFindUser(@RequestBody Map<String, Object> map, HttpSession session) {
		try {
			Customer customer = new Customer();
			customer.setStatus(Customer.STATUS_NORMAL);
			customer.setUsername((String) map.get("username"));
			if (agentLoginService.findUname(customer.getUsername(), customer.getStatus().toString(), Customer.TYPE_AGENT.toString(), Customer.TYPE_AGENT_STAFF.toString()) == 0)
				return Response.getError("用户不存在！");
				return Response.getSuccess("用户存在！");
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getError("系统异常！");
		}
	}

	/**
	 * 找回密码(web)校验验证码
	 * 
	 * @param customer
	 * @return
	 */
	@RequestMapping(value = "webFicationCode", method = RequestMethod.POST)
	public Response webFicationCode(@RequestBody Customer customer) {
		try {
			customer.setStatus(Customer.STATUS_NORMAL);
			if (customer.getCode().equals(agentLoginService.findCode(customer.getUsername(), customer.getStatus().toString(), Customer.TYPE_AGENT.toString(), Customer.TYPE_AGENT_STAFF.toString()))) {
				return Response.getSuccess("验证码正确！");
			} else {
				return Response.getError("验证码错误！");
			}
		} catch (Exception e) {
			return Response.getError("请求失败！");
		}
	}

	/**
	 * 找回密码(web)手机修改密码
	 * 
	 * @param customer
	 * @return
	 */
	@RequestMapping(value = "webUpdatePass", method = RequestMethod.POST)
	public Response webUpdatePass(@RequestBody Customer customer) {
		try {
			customer.setStatus(Customer.STATUS_NORMAL);
			if (agentLoginService.findUname(customer.getUsername(), customer.getStatus().toString(), Customer.TYPE_AGENT.toString(), Customer.TYPE_AGENT_STAFF.toString()) > 0) {
				customer.setPassword(SysUtils.string2MD5(customer.getPassword()));
				agentLoginService.updatePassword(customer.getPassword(), customer.getUsername(), customer.getStatus().toString(), Customer.TYPE_AGENT.toString(), Customer.TYPE_AGENT_STAFF.toString());

				return Response.getSuccess("找回密码成功！");
			} else {
				return Response.getError("该用户不存在！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getError("请求失败！");
		}
	}

	/**
	 * 找回密码(web)邮箱修改密码
	 * 
	 * @param customer
	 * @return
	 */
	@RequestMapping(value = "webUpdateEmailPass", method = RequestMethod.POST)
	public Response webUpdateEmainPass(@RequestBody Customer customer) {
		try {
			customer.setStatus(Customer.STATUS_NORMAL);
			if (agentLoginService.findEmail(customer.getUsername(), customer.getStatus().toString(), Customer.TYPE_AGENT.toString(), Customer.TYPE_AGENT_STAFF.toString()) > 0) {
				customer.setPassword(SysUtils.string2MD5(customer.getPassword()));
				agentLoginService.updateEmailPassword(customer.getPassword(), customer.getUsername(), customer.getStatus().toString(), Customer.TYPE_AGENT.toString(), Customer.TYPE_AGENT_STAFF.toString());

				return Response.getSuccess("找回密码成功！");
			} else {
				return Response.getError("该用户不存在！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getError("请求失败！");
		}
	}

	/**
	 * 发送手机验证码(注册)
	 * 
	 * @param number
	 */
	@RequestMapping(value = "sendPhoneVerificationCodeReg", method = RequestMethod.POST)
	public Response sendPhoneVerificationCodeReg(@RequestBody Map<String, Object> map) {
		try {
			Customer customer = new Customer();
			String str = SysUtils.getCode();
			try {
				customer.setPhone((String) map.get("codeNumber"));// 手机号
				customer.setTypes(Customer.TYPE_AGENT);
				customer.setStatus(Customer.STATUS_NORMAL);
				if (agentLoginService.judgePhone(customer) > 0) {
					// 检查手机号和邮箱是否有存在的正常代理商
					return Response.getError("该手机已使用！");
				}
				Boolean is_sucess = SysUtils.sendPhoneCode("感谢您注册华尔街金融，您的验证码为：" + str, customer.getPhone());
				if (!is_sucess) {
					return Response.getError("获取验证码失败！");
				} else {
					return Response.getSuccess(str);
				}
			} catch (IOException e) {
				e.printStackTrace();
				return Response.getError("系统异常！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getError("系统异常！");
		}
	}

	/**
	 * 注册代理商(web)
	 * 
	 * @param customer
	 * @return
	 */
	@RequestMapping(value = "userRegistrationWeb", method = RequestMethod.POST)
	public Response userRegistrationWeb(@RequestBody Map<String, Object> map) {
		try {
			// 检查该代理商账号是否存在
			Customer customer = new Customer();
			Agent agent = new Agent();
			customer.setUsername((String) map.get("userId"));
			/*
			 * if(agentLoginService.findUname(customer)>0){ return
			 * Response.getError("用户已注册！"); }else{
			 */
			// 查找该城市中是否有状态为正常的代理商
			customer.setTypes(Customer.TYPE_AGENT);
			customer.setStatus(Customer.STATUS_NORMAL);
			customer.setCityId((Integer) map.get("cityId"));
			customer.setPhone((String) map.get("phone"));
			customer.setEmail((String) map.get("email"));
			if (agentLoginService.judgePhone(customer) > 0) {
				// 检查手机号和邮箱是否有存在的正常代理商
				return Response.getError("该手机已使用！");
			}
			if (agentLoginService.judgeEmail(customer) > 0) {
				// 检查手机号和邮箱是否有存在的正常代理商
				return Response.getError("该邮箱已使用！");
			}
			if (agentLoginService.judgeCityId(customer) > 0) {
				return Response.getError("该城市已有相关代理商！");
			} else {
				// 向用户表添加数据
				customer.setPassword((String) map.get("passworda"));
				customer.setPassword(SysUtils.string2MD5(customer.getPassword()));
				// customer.setAccountType((Integer)map.get("accountType"));
				customer.setTypes(Customer.TYPE_AGENT);
				customer.setStatus(Customer.STATUS_NON_ACTIVE);
				customer.setPhone((String) map.get("phone"));
				customer.setEmail((String) map.get("email"));
				customer.setName((String) map.get("name"));
				customer.setIntegral(0);// 积分
				agentLoginService.addUser(customer);
				if (customer.getId() == null) {
					return Response.getError("申请失败！");
				} else {
					// 向代理商表添加数据
					Object ob = agentLoginService.getAgentCode(Agent.PARENT_ID);
					if (ob == null) {
						agent.setCode("001");
					} else {
						String str = String.valueOf(Integer.parseInt((String) ob) + 1);
						if (str.length() == 1) {
							str = "00" + str;
						}
						if (str.length() == 2) {
							str = "0" + str;
						}
						agent.setCode(str);
					}
					agent.setName((String) map.get("name"));
					// agent.setCardId((String)map.get("cityId"));
					agent.setTypes((Integer) map.get("types"));
					agent.setCompanyName((String) map.get("companyName"));
					agent.setBusinessLicense((String) map.get("licenseCode"));
					agent.setPhone((String) map.get("phone"));
					agent.setEmail((String) map.get("email"));
					agent.setCustomerId(customer.getId());
					// agent.setAddress((String)map.get("address"));
					agent.setFormTypes(Agent.FROM_TYPE_1);
					agent.setStatus(Agent.STATUS_1);
					agent.setParentId(Agent.PARENT_ID);
					agent.setIsHaveProfit(Agent.IS_HAVE_PROFIT_N);
					// agent.setCardIdPhotoPath((String)map.get("cardIdPhotoPath"));
					// agent.setTaxRegisteredNo((String)map.get("taxRegisteredNo"));
					// agent.setLicenseNoPicPath((String)map.get("licenseNoPicPath"));
					// agent.setTaxNoPicPath((String)map.get("taxNoPicPath"));
					agent.setCardId((String) map.get("card"));
					agentLoginService.addAgent(agent);
					return Response.getSuccess("注册成功！");
				}
			}
			// }
		} catch (Exception e) {
			logger.error("注册代理商异常！", e);
			return Response.getError("请求失败！");
		}
	}

	/**
	 * 修改密码
	 * 
	 * @param customer
	 * @return
	 */
	@RequestMapping(value = "modifyPassword", method = RequestMethod.POST)
	public Response modifyPassword(@RequestBody Customer customer) {

		Response response = new Response();
		String oldPassword = agentLoginService.findCustomerPwdById(customer.getCustomerId());
		customer.setPasswordOld(SysUtils.string2MD5(customer.getPasswordOld()));
		if (!customer.getPasswordOld().equals(oldPassword)) {
			response.setMessage("修改密码失败，原密码错误！");
			response.setCode(Response.ERROR_CODE);
			return response;
		}
		customer.setPassword(SysUtils.string2MD5(customer.getPassword()));
		if (agentLoginService.modifyPassword(customer) > 0) {
			response.setMessage("修改密码成功!");
			response.setCode(Response.SUCCESS_CODE);
		} else {
			response.setMessage("修改密码失敗!");
			response.setCode(Response.ERROR_CODE);
		}
		return response;
	}

	/**
	 * 申请成为代理商
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "applyForAgents", method = RequestMethod.POST)
	public Response applyForAgents(@RequestBody Map<String, Object> map) {
		try {
			Customer customer = new Customer();
			Agent agent = new Agent();
			customer.setTypes(Customer.TYPE_AGENT);
			customer.setStatus(Customer.STATUS_NORMAL);
			customer.setCityId((Integer) map.get("cityId"));
			customer.setPhone((String) map.get("phone"));
			if (agentLoginService.judgePhone(customer) > 0) {// 检查手机号和邮箱是否有存在的正常代理商
				return Response.getError("该手机已使用！");
			}
			if (agentLoginService.judgeCityId(customer) > 0) {
				return Response.getError("该城市已有相关代理商！");
			} else {
				// 向用户表添加数据
				customer.setTypes(Customer.TYPE_AGENT);
				customer.setStatus(Customer.STATUS_NON_ACTIVE);
				customer.setPhone((String) map.get("phone"));
				customer.setName((String) map.get("name"));
				customer.setIntegral(0);// 积分
				agentLoginService.insertUser(customer);
				if (customer.getId() == null) {
					return Response.getError("申请失败！");
				} else {
					// 向代理商表添加数据
					Object ob = agentLoginService.getAgentCode(Agent.PARENT_ID);
					if (ob == null) {
						agent.setCode("001");
					} else {
						String str = String.valueOf(Integer.parseInt((String) ob) + 1);
						if (str.length() == 1) {
							str = "00" + str;
						}
						if (str.length() == 2) {
							str = "0" + str;
						}
						agent.setCode(str);
					}
					agent.setName((String) map.get("name"));
					agent.setPhone((String) map.get("phone"));
					agent.setCustomerId(customer.getId());
					agent.setFormTypes(Agent.FROM_TYPE_1);
					agent.setStatus(Agent.STATUS_1);
					agent.setParentId(Agent.PARENT_ID);
					agent.setIsHaveProfit(Agent.IS_HAVE_PROFIT_N);
					agentLoginService.insertAgent(agent);
					return Response.getSuccess("注册成功！");
				}
			}
		} catch (Exception e) {
			logger.error("注册代理商异常！", e);
			return Response.getError("请求失败！");
		}
	}
}
