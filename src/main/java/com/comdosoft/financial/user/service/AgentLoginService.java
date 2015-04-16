package com.comdosoft.financial.user.service;

import java.util.HashMap;
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
	 * 代理商登陆
	 * @param customer
	 * @return
	 */
	public Map<Object, Object> doLogin(Customer customer){
		return agentLoginMapper.doLogin(customer);
	}
	
	/**
	 * 员工登陆
	 * @param customer
	 * @return
	 */
	public Map<Object, Object> doLoginPersn(Customer customer){
		return agentLoginMapper.doLoginPersn(customer);
	}
	/**
	 * 判断是代理商还是代理商下面员工
	 * @param username
	 * @return
	 */
	public Map<Object, Object> isAgentOrPerson(String username,String status,String agent,String agentPer){
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("username", username);
		map.put("status", status);
		map.put("agent", agent);
		map.put("agentPer", agentPer);
		return agentLoginMapper.isAgentOrPerson(map);
	}
	/**
	 * 找回密码
	 * @param customer
	 */
	public void updatePassword(String password,String username,String status,String agent,String agentPer){
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("username", username);
		map.put("status", status);
		map.put("agent", agent);
		map.put("agentPer", agentPer);
		agentLoginMapper.updatePassword(map);
	}
	/**
	 * 找回密码email
	 * @param customer
	 */
	public void updateEmailPassword(String password,String username,String ststus,String agent,String agentPer){
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("username", username);
		map.put("ststus", ststus);
		map.put("agent", agent);
		map.put("agentPer", agentPer);
		agentLoginMapper.updateEmailPassword(map);
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
	public int findUname(String username,String status,String agent,String agentPer){
		
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("username", username);
		map.put("status", status);
		map.put("agent", agent);
		map.put("agentPer", agentPer);
		return agentLoginMapper.findUname(map);
	}
	
	/**
	 * 查找用户
	 * @param customer
	 * @return
	 */
	public int findEmail(String username,String status,String agent,String agentPer){
		
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("username", username);
		map.put("status", status);
		map.put("agent", agent);
		map.put("agentPer", agentPer);
		return agentLoginMapper.findEmail(map);
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
	public void updateLastLoginedAt(String username,String ststus,String agent,String agentPer){
		
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("username", username);
		map.put("ststus", ststus);
		map.put("agent", agent);
		map.put("agentPer", agentPer);
		agentLoginMapper.updateLastLoginedAt(map);
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
	 * 判断手机号是否可用
	 * @param customer
	 * @return
	 */
	public int judgePhone(Customer customer){
		return agentLoginMapper.judgePhone(customer);
	}
	/**
	 * 判断邮箱是否可用
	 * @param customer
	 * @return
	 */
	public int judgeEmail(Customer customer){
		return agentLoginMapper.judgeEmail(customer);
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
	public List<Object> Toestemming(Customer customer){
		return agentLoginMapper.Toestemming(customer);
	}
	
	/**
	 * 获得验证码
	 * @param customer
	 * @return
	 */
	public String findCode(String username,String ststus,String agent,String agentPer){
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("username", username);
		map.put("ststus", ststus);
		map.put("agent", agent);
		map.put("agentPer", agentPer);
		return agentLoginMapper.findCode(map);
	}
	
	/**
	 * 修改密码
	 * 
	 * @param customer
	 * 
	 */
	public int modifyPassword(Customer customer) {
		return agentLoginMapper.modifyPassword(customer);
	}

	
	/**
	 * 查找旧密码
	 * @param id
	 * @return
	 */
	public String findCustomerPwdById(Integer id) {
		return agentLoginMapper.findCustomerPwdById(id);
	}
	
}
