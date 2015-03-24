package com.comdosoft.financial.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.comdosoft.financial.user.domain.query.MailReq;
import com.comdosoft.financial.user.domain.zhangfu.Agent;
import com.comdosoft.financial.user.domain.zhangfu.Customer;
import com.comdosoft.financial.user.domain.zhangfu.CustomerAddress;
import com.comdosoft.financial.user.mapper.zhangfu.AgentMapper;
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

    public Map<Object, Object> getOne(int id) {
        return agentMapper.getOne(id);
    }

    public Customer getOneCustomer(int id) {
        return agentMapper.getOneCustomer(id);
    }

    public void updateCustomer(Customer param) {
        agentMapper.updateCustomer(param);
    }

    public Map<Object, Object> getUpdatePhoneDentcode(int customerId, String phone) {
        Map<Object, Object> result = new HashMap<Object, Object>();

        // 生成随机6位验证码
        String dentcode = SysUtils.getRandNumberString(6);
        result.put("dentcode", dentcode);

        // 保存验证码入库
        Customer customer = new Customer();
        customer.setCustomerId(customerId);
        customer.setDentcode(dentcode);
        agentMapper.updateCustomer(customer);

        
        //send the check code to the phone
        try{
            CommUtils.sendPhoneCode("请输入6位验证码："+dentcode, phone);
        	
        }catch (Exception e){
        	e.printStackTrace();
        }

        return result;
    }

    public Object getUpdateEmailDentcode(int customerId, String email) {
        Map<Object, Object> result = new HashMap<Object, Object>();

        // 生成随机6位验证码
        String dentcode = SysUtils.getRandNumberString(6);
        result.put("dentcode", dentcode);

        // 保存验证码入库
        Customer customer = new Customer();
        customer.setCustomerId(customerId);
        customer.setDentcode(dentcode);
        agentMapper.updateCustomer(customer);

        // email
        MailReq req = new MailReq();
        req.setAddress(email);
        req.setUrl("<a href='localhost:8080/ZFMerchant/#/findpassEmail'>激活账号</a>");
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

    public List<Map<Object, Object>> getAddressList(int customerId) {
        return agentMapper.getAddressList(customerId);
    }

    public Map<Object, Object> getOneAddress(int id) {
        return agentMapper.getOneAddress(id);
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

    public void deleteAddress(int id) {
        agentMapper.deleteAddress(id);
    }

}