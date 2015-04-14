package com.comdosoft.financial.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.comdosoft.financial.user.domain.query.CommercialReq;
import com.comdosoft.financial.user.domain.query.MailReq;
import com.comdosoft.financial.user.domain.zhangfu.Agent;
import com.comdosoft.financial.user.domain.zhangfu.Customer;
import com.comdosoft.financial.user.domain.zhangfu.CustomerAddress;
import com.comdosoft.financial.user.domain.zhangfu.MyOrderReq;
import com.comdosoft.financial.user.mapper.zhangfu.AgentMapper;
import com.comdosoft.financial.user.mapper.zhangfu.TerminalsMapper;
import com.comdosoft.financial.user.utils.CommUtils;
import com.comdosoft.financial.user.utils.SysUtils;

/**
 * 代理商 - 业务层<br>
 *
 * @author Java-007 2015年2月7日
 *
 */
@Service
public class AgentService {

	@Resource
	private AgentMapper agentMapper;

	@Resource
	private MailService MailService;

	@Resource
	private TerminalsMapper terminalsMapper;

	public Map<Object, Object> getOne(Customer param) {
		return agentMapper.getOne(param);
	}

	public Customer getOneCustomer(Customer param) {
		return agentMapper.getOneCustomer(param);
	}

	public void updateCustomer(Customer param) {
		agentMapper.updateCustomer(param);
	}

	public Map<Object, Object> getUpdatePhoneDentcode(int customerId, String phone) {
		Map<Object, Object> result = new HashMap<Object, Object>();

		// 生成随机6位验证码
		String dentcode = SysUtils.getCode();
		result.put("dentcode", dentcode);

		// send the check code to the phone
		try {
			CommUtils.sendPhoneCode("感谢您使用华尔街金融，您的验证码为：" + dentcode, phone);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 保存验证码入库
		Customer customer = new Customer();
		customer.setCustomerId(customerId);
		customer.setDentcode(dentcode);
		agentMapper.updateCustomer(customer);

		return result;
	}

	public Object getUpdateEmailDentcode(int customerId, String email) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		// 生成随机6位验证码
		String dentcode = SysUtils.getCode();
		result.put("dentcode", dentcode);
		Customer c = new Customer();
		c.setCustomerId(customerId);
		c.setDentcode(dentcode);
		agentMapper.updateCustomer(c);
		// 保存验证码入库
		Map<String, Object> m = agentMapper.findAgentByCustomerId(customerId);
		if (null != m) {
			MailReq req = new MailReq();
			req.setUserName(m.get("username") + "");// 姓名
			req.setAddress(email);// 邮箱
			try {
				MailService.sendMail_phone(req, dentcode);
				return result;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// Customer customer = new Customer();
		// customer.setCustomerId(customerId);
		// customer.setDentcode(dentcode);
		// agentMapper.updateCustomer(customer);

		return result;
	}

	@Transactional(value = "transactionManager-zhangfu")
	public void update(Customer customer, int i) {
		if (i == 1) {// 更新密码
			// 再更新customer
			agentMapper.updateCustomer(customer);
		} else if (i == 2) {// email
			// 先更新agent
			Agent agent = new Agent();
			agent.setCustomerId(customer.getCustomerId());
			agent.setEmail(customer.getEmail());
			agentMapper.update(agent);

			// 再更新customer
			agentMapper.updateCustomer(customer);
		} else if (i == 3) {// 手机
			// 先更新agent
			Agent agent = new Agent();
			agent.setCustomerId(customer.getCustomerId());
			agent.setPhone(customer.getPhone());
			agentMapper.update(agent);

			// 再更新customer
			agentMapper.updateCustomer(customer);
		}

	}

	public List<Map<Object, Object>> getAddressList(Customer param) {
		return agentMapper.getAddressList(param);
	}

	public Map<Object, Object> getOneAddress(Customer param) {
		return agentMapper.getOneAddress(param);
	}

	@Transactional(value = "transactionManager-zhangfu")
	public void insertAddress(Map<Object, Object> param) {
		int isDefault = (int) param.get("isDefault");
		if (isDefault == CustomerAddress.ISDEFAULT_1) {
			param.put("is_default", CustomerAddress.ISDEFAULT_2);
			agentMapper.updateAddress(param);
		}
		agentMapper.insertAddress(param);
	}

	public void deleteAddress(Customer param) {
		agentMapper.deleteAddress(param);
	}

	/**
	 * 查询代理商下的商户列表
	 * 
	 * @param req
	 * @return
	 */
	public Map<String, Object> getCommercialTenantList(CommercialReq req) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", agentMapper.getCommercialTenantCount(req));
		result.put("list", agentMapper.getCommercialTenantList(req));
		return result;
	}

	/**
	 * 获取单个商户的终端信息
	 * 
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getOneCommercialTerminalList(Map<Object, Object> param) {
		return terminalsMapper.getOneCommercialTerminalList(param);
	}

	/**
	 * 根据ID删除单个商户
	 * 
	 * @param param
	 * @return
	 */
	public int deleteCommercial(Map<Object, Object> param) {
		if (agentMapper.deleteCommercial(param) > 0) {
			if (agentMapper.updateCommercialStatus(param) > 0) {
				return 1;
			}
		}
		return 0;
	}

	public Map<Object, Object> queryAgent(int id) {
		return agentMapper.queryAgent(id);
	}

	public void updatePhoneNumber(Customer param) {
		agentMapper.updatePhoneNumber(param);
	}

	public void updateEmailAddr(Customer param) {
		agentMapper.updateEmailAddr(param);
	}

	public void batchDeleteAddress(MyOrderReq req) {
		agentMapper.batchDeleteAddress(req);
	}

	public void updateAddress(Map<Object, Object> param) {
		agentMapper.update_Address(param);
	}

}
