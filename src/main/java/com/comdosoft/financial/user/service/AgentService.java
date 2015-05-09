package com.comdosoft.financial.user.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
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
	
	@Value("${filePath}")
	private String filePath;

	public Map<Object, Object> getOne(Customer param) {
		Map<Object, Object>  m = agentMapper.getOne(param);
		if(null != m){
			Object card_id_photo_path =  m.get("card_id_photo_path") ;
			Object license_no_pic_path = m.get("license_no_pic_path");
			Object tax_no_pic_path = m.get("tax_no_pic_path");
			m.put("card_id_photo_path", filePath+card_id_photo_path);
			m.put("license_no_pic_path", filePath+license_no_pic_path);
			m.put("tax_no_pic_path", filePath+tax_no_pic_path);
		}
		return m;
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
	public int insertAddress(Map<Object, Object> param) {
		String idt = param.get("isDefault")+"";
		int isDefault = Integer.parseInt(idt);
		if (isDefault == CustomerAddress.ISDEFAULT_1) {
			param.put("is_default", CustomerAddress.ISDEFAULT_2);
			agentMapper.updateAddress(param);
		}
		 CustomerAddress ca = new CustomerAddress();
        ca.setAddress(param.get("address")==null?"":param.get("address")+"");
        String cityId = param.get("cityId")==null?"":param.get("cityId")+"";
        if(cityId !=""){
        	ca.setCityId(Integer.parseInt(cityId));
        }
        ca.setCreatedAt(new Date());
        ca.setCustomerId((Integer) (param.get("customerId")==null?0:param.get("customerId")));
        ca.setIsDefault(isDefault);
        ca.setMoblephone(param.get("moblephone")==null?"":param.get("moblephone")+"");
        ca.setReceiver(param.get("receiver")==null?"":param.get("receiver")+"");
        ca.setTelphone(param.get("telphone")==null?"":param.get("telphone")+"");
        ca.setZipCode(param.get("zipCode")==null?"":param.get("zipCode")+"");
		agentMapper.insertAddress(ca);
		int i = ca.getId();
		return i ;
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
		Map<Object, Object>  mm = agentMapper.queryAgent(id);
		  String sphone = mm.get("phone")+"";
          if(sphone != ""){
        	  int a=sphone.length()/3;
              String s2=sphone.substring(0,a);
              String s3=sphone.substring(sphone.length()-a,sphone.length());
              sphone =s2+"****"+s3;
          }
          mm.put("sphone", sphone);
		return mm;
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
		  int isDefault = Integer.parseInt(param.get("isDefault").toString());
	        Integer customerId = (Integer) param.get("customerId");
	        Integer addressId = (Integer) param.get("id");
	        if(null == customerId){
	        	CustomerAddress ca = agentMapper.findAddressById(addressId);
	        	param.put("customerId", ca.getCustomerId());
	        }
	        if (isDefault == CustomerAddress.ISDEFAULT_1) {
	            param.put("is_default", CustomerAddress.ISDEFAULT_2);
	            agentMapper.updateAddress(param);
	            param.put("is_default", isDefault);
	            agentMapper.update_Address(param);
	        }else  {
	        	agentMapper.update_Address(param);
	        }
	}


    @Transactional(value = "transactionManager-zhangfu")
    public void setDefaultAddress(Map<Object, Object> param) {
        param.put("is_default", CustomerAddress.ISDEFAULT_2); // 其它设置为非默认
//        customerMapper.updateDefaultAddress(param);
        agentMapper.setNotDefaultAddress(param);
        agentMapper.setDefaultAddress(param);
    }

	public int countAddress(Map<Object, Object> param) {
			return agentMapper.countAddress(param);
	}


}
