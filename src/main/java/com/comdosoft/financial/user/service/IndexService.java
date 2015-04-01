package com.comdosoft.financial.user.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.comdosoft.financial.user.domain.query.MailReq;
import com.comdosoft.financial.user.domain.zhangfu.MyOrderReq;
import com.comdosoft.financial.user.mapper.zhangfu.IndexMapper;
import com.comdosoft.financial.user.utils.SysUtils;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
@Service
public class IndexService {
//    private static final Logger logger = LoggerFactory.getLogger(IndexService.class);
    @Resource
    private IndexMapper indexMapper;
    @Resource
    private MailService MailService;
    
    public List<Map<String, Object>> getFactoryList() {
        List<Map<String, Object>> list = indexMapper.getFactoryList();
        List<Map<String, Object>> newlist = new ArrayList<Map<String,Object>>();
        Map<String,Object> map = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
        for(Map<String,Object> m: list){
            map = new HashMap<String,Object>();
            try {
                String d = (m.get("created_at")+"");
                Date date = sdf.parse(d);
                String c_date = sdf.format(date);
                map.put("created_at", c_date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            map.put("logo_file_path", m.get("logo_file_path")==null?"":m.get("logo_file_path"));
            map.put("name", m.get("name")==null?"":m.get("name"));
            String des =  m.get("description")==null?"":m.get("description").toString();
            if(des.length()>12){
                des = des.substring(0, 12)+"...";
            }
            map.put("description",des);
            map.put("website_url", m.get("website_url")==null?"": m.get("website_url"));
            newlist.add(map);
        }
        return newlist;
    }

    public List<Map<String, Object>> getPosList() {
        List<Map<String, Object>> list = indexMapper.getPosList();
        List<Map<String, Object>> newlist = new ArrayList<Map<String,Object>>();
        Map<String,Object> map = null;
        for(Map<String,Object> m: list){
            map = new HashMap<String,Object>();
            String id =  m.get("id")==null?"":m.get("id").toString();
            map.put("id", id);
            map.put("brand_number", m.get("brand_number")==null?"":m.get("brand_number"));
            map.put("volume_number", m.get("volume_number")==null?"":m.get("volume_number"));
            map.put("title", m.get("title")==null?"":m.get("title"));
            map.put("brand_name", m.get("brand_name")==null?"": m.get("brand_name"));
            map.put("good_logo", m.get("url_path")==null?"": m.get("url_path"));
            newlist.add(map);
        }
        return newlist;
    }

    public List<Map<String, Object>> findAllCity() {
        List<Map<String, Object>> parent_list = indexMapper.getParentCitiesList();
        List<Map<String, Object>> newlist = new ArrayList<Map<String,Object>>();
        Map<String,Object> map = null;
        for(Map<String,Object> m: parent_list){
            map = new HashMap<String,Object>();
            String id =  m.get("id")==null?"":m.get("id").toString();
            map.put("id", id);
            map.put("name", m.get("name")==null?"":m.get("name"));
            List<Map<String, Object>> children_list = indexMapper.getChildrenCitiesList(id);
            List<Map<String, Object>> new_children_list = new ArrayList<Map<String,Object>>();
            Map<String,Object> cmap = null;
            for(Map<String,Object> c:children_list){
                cmap = new HashMap<String,Object>();
                String cid =  c.get("id")==null?"":c.get("id").toString();
                cmap.put("id", cid);
                cmap.put("name", c.get("name")==null?"":c.get("name"));
                new_children_list.add(cmap);
            }
            map.put("childrens",new_children_list );
            newlist.add(map);
        }
        return newlist;
    }

    public String  getPhoneCode(MyOrderReq req) {
        String phone = req.getPhone();
        String code = SysUtils.getCode();
        if(SysUtils.isMobileNO(phone)){
            try {
                Boolean b = SysUtils.sendPhoneCode("感谢您使用华尔街金融，您的验证码为："+code, phone);
                if(!b) return "-1";
            } catch (JsonParseException e) {
                e.printStackTrace();
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return code;
    }

    public void changePhone(MyOrderReq req) {
        indexMapper.changePhone(req);
    }

    public void change_email(HttpServletRequest request, MyOrderReq myOrderReq) {
        String   url  = request.getScheme()+"://";  
        String name = myOrderReq.getQ();
        String id = myOrderReq.getId().toString();
        url+=request.getHeader("host");   
        url+=request.getContextPath();      
        MailReq req = new MailReq();
        req.setUserName(myOrderReq.getQ());//姓名
        req.setAddress(myOrderReq.getContent());//邮箱
        String data;
        try {
             data = SysUtils.string2MD5(name+"zf_vc");  ///#/myinfobase
            req.setUrl("<a href='"+url+"/#/myinfobase?id="+id+"&q="+data+"'>点击修改</a>");
            MailService.sendMail(req);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	public String update_email(MyOrderReq myOrderReq) {
	        MailReq req = new MailReq();
	        req.setUserName(myOrderReq.getUserName());//姓名
	        req.setAddress(myOrderReq.getEmail());//邮箱
	        try {
	        	String code = SysUtils.getCode() ;
	            MailService.sendMail_phone(req,code);
	            return code;
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return "";
	}

	   public List<Map<String, Object>> wxlist(MyOrderReq myOrderReq) {
	        return indexMapper.wxlist(myOrderReq);
	    }

    public String getRoleByAgentId(int agentId){
    	StringBuilder rolesStr=new StringBuilder();
    	List<Map<String,Object>> list=indexMapper.getRoleByAgentId(agentId);
    	if(null!=list && list.size()>0){
    		for(int i=0;i<list.size();i++){
    			int roleId=(Integer)list.get(i).get("roleId");
    			if(rolesStr.length()==0){
    				rolesStr.append(roleId);
    			}else{
    				rolesStr.append(","+roleId);
    			}
    		}
    	}
    	return rolesStr.toString();
    	
    }
}
