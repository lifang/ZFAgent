package com.comdosoft.financial.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.comdosoft.financial.user.mapper.zhangfu.OpeningApplyMapper;
import com.comdosoft.financial.user.mapper.zhangfu.TerminalsMapper;
import com.comdosoft.financial.user.mapper.zhangfu.UserManagementMapper;

@Service
public class UserManagementService {
	@Resource
	private UserManagementMapper userManagementMapper;
	
	@Resource
	private OpeningApplyMapper openingApplyMapper;
	
	@Resource
	private TerminalsMapper terminalsMapper;
	
	/**
	 * 获得该代理商所有相关用户
	 * @param customerId
	 * @return
	 */
	public List<Map<String, Object>> getUser(int customerId){
		return userManagementMapper.getUser(customerId);
	}

	/**
	 * 删除用户与代理商之间的关联
	 * @param id
	 */
	public void delectAgentUser(int id,int customerId,int status){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("customerId", customerId);
		map.put("status", status);
		userManagementMapper.delectAgentUser(map);
	}
	
	/**
	 * 获得该用户所用终端
	 * @param customerId
	 * @return
	 */
	public List<Map<String, Object>> getTerminals(int customerId){
		return userManagementMapper.getTerminals(customerId);
	}
	
}
