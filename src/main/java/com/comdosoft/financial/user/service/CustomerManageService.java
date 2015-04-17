package com.comdosoft.financial.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.comdosoft.financial.user.domain.Response;
import com.comdosoft.financial.user.domain.query.CustomerManageReq;
import com.comdosoft.financial.user.domain.query.LowerAgentReq;
import com.comdosoft.financial.user.mapper.zhangfu.CustomerManageMapper;
import com.comdosoft.financial.user.mapper.zhangfu.LowerAgentMapper;
import com.comdosoft.financial.user.utils.SysUtils;

/**
 * 用于App用户管理
 * @author yangyibin
 *
 */
@Service
public class CustomerManageService {
	@Autowired
	private CustomerManageMapper customerManageMapper;
	@Autowired
	private LowerAgentMapper lowerAgentMapper;
	
	@Autowired
	private SystemSetService sys;
	
	public Map<String, Object> getList(CustomerManageReq req) {
        Map<String, Object> map=new HashMap<String, Object>();
        int total=customerManageMapper.getTotal(req);
        map.put("total", total);
        List<Map<String, Object>> list=customerManageMapper.getCustomerList(req);
        map.put("list", list);
        return map;
    }
	/**
	 * 新增
	 * @param req
	 * @return
	 * @throws Exception 
	 */
	@Transactional(value="transactionManager-zhangfu",propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, Object> insert(CustomerManageReq req) throws Exception{
		Map<String, Object> map=new HashMap<String, Object>();
		LowerAgentReq lowerAgentReq=new LowerAgentReq();
		int resultCode=Response.ERROR_CODE;
		StringBuilder resultInfo=new StringBuilder();
		//校验格式等 名字，密码，登陆ID不存在可用
		if(req.getUserName().trim().equals("")){
			resultInfo.setLength(0);
			resultInfo.append("姓名不能为空");
		}else{
			if(req.getPwd().trim().equals("")){
				resultInfo.setLength(0);
				resultInfo.append("密码不能为空");
			}else{
				if(!req.getPwd().equals(req.getPwd1())){
					resultInfo.setLength(0);
					resultInfo.append("两次输入的密码不一致");
				}else{
					lowerAgentReq.setLoginId(req.getLoginId());
					Map<String, Object> mapTemp=lowerAgentMapper.checkLoginId(lowerAgentReq);
					int temp=0;
					if(null!=mapTemp.get("num")){
						temp=Integer.parseInt(mapTemp.get("num").toString());
					}
					if(temp>=1){
						resultInfo.setLength(0);
						resultInfo.append("该登陆ID已经存在");
					}else{
						//密码加密，执行存入数据库
						req.setPwd(SysUtils.string2MD5(req.getPwd()));
						//向customers表插入记录
						lowerAgentReq.setAgentName(req.getUserName());
						lowerAgentReq.setPwd(req.getPwd());
						int temp1=lowerAgentMapper.addNewCustomer(lowerAgentReq);
						if(temp1<1){
							resultInfo.setLength(0);
							resultInfo.append("新增customers表出错");
						}else{
							//更新关联关系表
							int customerId=lowerAgentMapper.getCustomerId(lowerAgentReq);
							req.setCustomerId(customerId);
							//检查是否已经存在
							int temp2=customerManageMapper.getCusAgentInfo(req);
							if(temp2>0){
								resultInfo.setLength(0);
								resultInfo.append("已存在该用户与代理商的关联关系");
								throw new Exception("save_error");
							}else{
								req.setTypes(2);
								req.setStatus(1);
								int temp3=customerManageMapper.creCusAgentRelation(req);
								if(temp3<1){
									resultInfo.setLength(0);
									resultInfo.append("插入customerAgent关系出错");
								}else{
									//循环权限
									String[] roles=req.getRoles().split("\\,");
									int sign=0;
									for(int i=0;i<roles.length;i++){
										req.setRoleId(Integer.parseInt(roles[i].toString()));
										int temp4=customerManageMapper.getCusRoleInfo(req);
										if(temp4>=1){
											sign=1;
											resultInfo.setLength(0);
											resultInfo.append("已存在该用户与所选权限的关联关系");
										}else{
											int temp5=customerManageMapper.creCusRoleRelation(req);
											if(temp5<1){
												sign=1;
												resultInfo.setLength(0);
												resultInfo.append("插入该用户权限的关联关系出错");
											}else{
												resultInfo.setLength(0);
												resultInfo.append("插入该用户权限的关联关系成功");
											}
										}
									}
									
									if(sign==0){
										resultCode=Response.SUCCESS_CODE;
									}
								}
							}
						}
					}
				}
			}
		}
		
		String result="代理商新增用户操作，结果为"+resultInfo.toString();
		sys.operateRecord(result,req.getAgentsId());
		map.put("resultCode", resultCode);
		map.put("resultInfo", resultInfo.toString());
		
		return map;
	}

	@Transactional(value="transactionManager-zhangfu",propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, Object> edit(CustomerManageReq req){
		Map<String, Object> map=new HashMap<String, Object>();
		int resultCode=Response.ERROR_CODE;
		StringBuilder resultInfo=new StringBuilder();
		//req.setCustomerId(customerManageMapper.getCustomerIdByLoginId(req));
		//密码加密，执行存入数据库
		req.setPwd(SysUtils.string2MD5(req.getPwd()));
		int temp=customerManageMapper.changePwd(req);
		if(temp<1){
			resultInfo.setLength(0);
			resultInfo.append("修改该用户的密码出错");
		}else{
			//根据customerId
			customerManageMapper.delCusRoleRel(req);
			//循环权限
			String[] roles=req.getRoles().split("\\,");
			int sign=0;
			for(int i=0;i<roles.length;i++){
				req.setRoleId(Integer.parseInt(roles[i].toString()));
				
				int temp5=customerManageMapper.creCusRoleRelation(req);
				if(temp5<1){
					sign=1;
					resultInfo.setLength(0);
					resultInfo.append("插入该用户权限的关联关系出错");
				}else{
					resultInfo.setLength(0);
					resultInfo.append("插入该用户权限的关联关系成功");
				}
			}
			if(sign==0){
				resultCode=Response.SUCCESS_CODE;
			}
		 }
		
		map.put("resultCode", resultCode);
		map.put("resultInfo", resultInfo);
		return map;
	}
	
	
	/**
	 * 单个删除
	 * @param req
	 * @return
	 */
	@Transactional(value="transactionManager-zhangfu",propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, Object> deleteOne(CustomerManageReq req){
		Map<String, Object> map=new HashMap<String, Object>();
		int resultCode=Response.ERROR_CODE;
		StringBuilder resultInfo=new StringBuilder();
		int temp=customerManageMapper.delCustomer(req);
		if(temp<1){
			resultInfo.setLength(0);
			resultInfo.append("删除customers表出错");
		}else{
			int temp1=customerManageMapper.delCusAgentRel(req);
			if(temp1<1){
				resultInfo.setLength(0);
				resultInfo.append("删除customer_agent_relations表出错");
			}else{
				int temp2=customerManageMapper.delCusRoleRel(req);
				if(temp2<1){
					resultInfo.setLength(0);
					resultInfo.append("删除customer_role_relations表出错");
				}else{
					resultCode=Response.SUCCESS_CODE;
					resultInfo.setLength(0);
					resultInfo.append("删除用户成功");
				}
			}
		}
		
		String result="代理商删除用户操作，结果为"+resultInfo.toString();
		sys.operateRecord(result,req.getAgentsId());
		map.put("resultCode", resultCode);
		map.put("resultInfo", resultInfo.toString());
		return map;
	}

	/**
	 * 批量删除
	 * @param req
	 * @return
	 */
	@Transactional(value="transactionManager-zhangfu",propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String,Object> deleteAll(CustomerManageReq req){
		Map<String, Object> map=new HashMap<String, Object>();
		int resultCode=Response.ERROR_CODE;
		StringBuilder resultInfo=new StringBuilder();
		
		String[] customerIds=req.getCustomerIds().split("\\,");
		for(int i=0;i<customerIds.length;i++){
			req.setCustomerId(Integer.parseInt(customerIds[i].toString()));
			
			int temp=customerManageMapper.delCustomer(req);
			if(temp<1){
				resultCode=Response.ERROR_CODE;
				resultInfo.setLength(0);
				resultInfo.append("删除customers表出错");
				break;
			}else{
				int temp1=customerManageMapper.delCusAgentRel(req);
				if(temp1<1){
					resultCode=Response.ERROR_CODE;
					resultInfo.setLength(0);
					resultInfo.append("删除customer_agent_relations表出错");
					break;
				}else{
					int temp2=customerManageMapper.delCusRoleRel(req);
					if(temp2<1){
						resultCode=Response.ERROR_CODE;
						resultInfo.setLength(0);
						resultInfo.append("删除customer_role_relations表出错");
						break;
					}else{
						resultCode=Response.SUCCESS_CODE;
						resultInfo.setLength(0);
						resultInfo.append("删除用户成功");
					}
				}
			}
			
		}
		
		String result="代理商批量删除用户操作，结果为"+resultInfo.toString();
		sys.operateRecord(result,req.getAgentsId());
		map.put("resultCode", resultCode);
		map.put("resultInfo", resultInfo.toString());
		return map;
	}
	
	
	/**
	 * 获取用户详细信息
	 * @param req
	 * @return
	 */
	public Map<String, Object> getInfo(CustomerManageReq req){
		Map<String, Object> map=new HashMap<String, Object>();
		int resultCode=Response.SUCCESS_CODE;
		StringBuilder resultInfo=new StringBuilder();
		List<Map<String, Object>> list=customerManageMapper.getInfo(req);
		
		if(list!=null && list.size()>0){
			String loginId=list.get(0).get("username").toString();
			String name=list.get(0).get("name").toString();
			String createdTime=list.get(0).get("created_at").toString();
			StringBuilder rolesStr=new StringBuilder();
			for(int i=0;i<list.size();i++){
				String roleId=list.get(i).get("role_id").toString();
				if(rolesStr.length()<1){
					rolesStr.append(roleId);
				}else{
					rolesStr.append(","+roleId);
				}
			}
			Map<String, Object> result=new HashMap<String, Object>();
			result.put("loginId", loginId);
			result.put("name", name);
			result.put("rolesStr", rolesStr);
			result.put("createdAt",createdTime);
			map.put("result", result);
			resultInfo.setLength(0);
			resultInfo.append("成功");
		}else{
			resultInfo.setLength(0);
			resultInfo.append("不存在数据");
		}
		map.put("resultCode", resultCode);
		map.put("resultInfo", resultInfo);
		return map;	
	}
	
}
