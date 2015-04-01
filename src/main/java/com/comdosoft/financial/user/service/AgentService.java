package com.comdosoft.financial.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.comdosoft.financial.user.domain.query.MailReq;
import com.comdosoft.financial.user.domain.zhangfu.Agent;
import com.comdosoft.financial.user.domain.zhangfu.Customer;
import com.comdosoft.financial.user.domain.zhangfu.CustomerAddress;
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

		// 保存验证码入库
		Customer customer = new Customer();
		customer.setCustomerId(customerId);
		customer.setDentcode(dentcode);
		agentMapper.updateCustomer(customer);

		// send the check code to the phone
		try {
			CommUtils.sendPhoneCode("请输入6位验证码：" + dentcode, phone);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public Object getUpdateEmailDentcode(HttpServletRequest request, int customerId, String email) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		String url = request.getScheme() + "://"; // 请求协议 http 或 https
		url += request.getHeader("host"); // 请求服务器
		url += request.getContextPath();
		System.err.println("===>>>" + url);
		// 生成随机6位验证码
		String dentcode = SysUtils.getCode();
		result.put("dentcode", dentcode);

		// 保存验证码入库
		Customer customer = new Customer();
		customer.setCustomerId(customerId);
		customer.setDentcode(dentcode);
		agentMapper.updateCustomer(customer);

		// email
		MailReq req = new MailReq();
		req.setAddress(email);
		req.setUrl("<a href='" + url + "/#/findpassEmail'>激活账号</a>");
		req.setUserName(String.valueOf(customer.getCustomerId()));

		MailService.sendMail(req);

		return result;
	}

	@Transactional(value = "transactionManager-zhangfu")
	public void update(Customer customer) {

		// 先更新agent
		Agent agent = new Agent();
		agent.setCustomerId(customer.getCustomerId());
		agent.setPhone(customer.getPhone());
		agent.setEmail(customer.getEmail());
		agentMapper.update(agent);

		// 再更新customer
		agentMapper.updateCustomer(customer);
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

	public Map<Object, Object> queryAgent(int id) {
		return agentMapper.queryAgent(id);
	}

	/**
	 * 查询代理商下的商户总数
	 * 
	 * @param param
	 * @return
	 */
	public Map<String, Object> getCommercialTenantCount(Map<Object, Object> param) {
		return agentMapper.getCommercialTenantCount(param);
	}

	/**
	 * 查询代理商下的商户列表
	 * 
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getCommercialTenantList(Map<Object, Object> param) {
		return agentMapper.getCommercialTenantList(param);
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
}