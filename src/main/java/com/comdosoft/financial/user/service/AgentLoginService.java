package com.comdosoft.financial.user.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.comdosoft.financial.user.domain.zhangfu.Agent;
import com.comdosoft.financial.user.domain.zhangfu.Customer;
import com.comdosoft.financial.user.mapper.zhangfu.AgentLoginMapper;

@Service
public class AgentLoginService {
	@Resource
	private AgentLoginMapper agentLoginMapper;
	
	/**
	 * 用户登陆
	 * @param customer
	 * @return
	 */
	public Customer doLogin(Customer customer){
		Customer cu = new Customer();
		cu = agentLoginMapper.doLogin(customer);
		
		return cu;
	}
	
	/**
	 * 找回密码
	 * @param customer
	 */
	public void updatePassword(Customer customer){
		agentLoginMapper.updatePassword(customer);
	}
	
	/**
	 * 添加用户
	 * @param customer
	 */
	public void addUser(Customer customer){
		agentLoginMapper.addUser(customer);
	}
	
	/**
	 * 查找用户
	 * @param customer
	 * @return
	 */
	public int findUname(Customer customer){
		return agentLoginMapper.findUname(customer);
	}
	
	/**
	 * 修改验证码
	 * @param customer
	 */
	public void updateDentcode(Customer customer){
		agentLoginMapper.updateDentcode(customer);
	}
	
	/**
	 * 修改登陆时间
	 * @param customer
	 */
	public void updateLastLoginedAt(Customer customer){
		agentLoginMapper.updateLastLoginedAt(customer);
	}
	
	/**
	 * 判断该城市中是否有代理商
	 * @param customer
	 * @return
	 */
	public int judgeCityId(Customer customer){
		return agentLoginMapper.judgeCityId(customer);
	}
	
	/**
	 * 获取代理商编号
	 * @return
	 */
	public Object getAgentCode(int parentId){
		return agentLoginMapper.getAgentCode(parentId);
	}
	
	/**
	 * 添加代理商
	 * @param agent
	 */
	public void addAgent(Agent agent){
		agentLoginMapper.addAgent(agent);
	}
	
	/**
	 * 代理商登陆后获的权限
	 * @param customer
	 * @return
	 */
	public List<Map<String, String>> Toestemming(Customer customer){
		return agentLoginMapper.Toestemming(customer);
	}
	
	/**
	 * 获得验证码
	 * @param customer
	 * @return
	 */
	public String findCode(Customer customer){
		return agentLoginMapper.findCode(customer);
	}
	
	
}
