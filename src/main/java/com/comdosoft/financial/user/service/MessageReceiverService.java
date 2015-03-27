package com.comdosoft.financial.user.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.comdosoft.financial.user.domain.zhangfu.MyOrderReq;
import com.comdosoft.financial.user.domain.zhangfu.SysMessage;
import com.comdosoft.financial.user.mapper.zhangfu.MessageReceiverMapper;
import com.comdosoft.financial.user.utils.page.Page;
import com.comdosoft.financial.user.utils.page.PageRequest;

@Service
public class MessageReceiverService {
    @Resource
    private MessageReceiverMapper messageReceiverMapper;

    public Page<Object> findAll(MyOrderReq myOrderReq) {
        PageRequest request = new PageRequest(myOrderReq.getPage(),myOrderReq.getRows());
        int count = messageReceiverMapper.count(myOrderReq.getCustomer_id());
        List<SysMessage> centers = messageReceiverMapper.findAll(myOrderReq);
        List<Object> list =new LinkedList<Object>();
        Map<String,String> map = null;
        for(SysMessage s: centers){
            map = new HashMap<String,String>();
            map.put("id", s.getId().toString());
            map.put("title", s.getTitle());
            Date d = s.getCreatedAt();
            if(null ==d){
                map.put("create_at","");
            }else{
            	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            	map.put("create_at",sdf.format(d));
            }
            map.put("content", s.getContent());
            list.add(map);
        }
        return new Page<Object>(request, list, count);
    }
    
    public SysMessage findById(MyOrderReq myOrderReq) {
        SysMessage sysMessage = messageReceiverMapper.findById(myOrderReq);
        messageReceiverMapper.isRead(myOrderReq);
        return sysMessage;
    }
    
    public String delete(MyOrderReq myOrderReq){
        SysMessage sysMessage = messageReceiverMapper.findById(myOrderReq);
        if(null == sysMessage){
            return "-1";
        }
        messageReceiverMapper.delete(myOrderReq);
        return "1";
    }
    
    public void batchDelete(MyOrderReq myOrderReq){
        messageReceiverMapper.batchDelete(myOrderReq);
    }
    
    public void batchRead(MyOrderReq myOrderReq){
        messageReceiverMapper.batchUpdate(myOrderReq);
    }

//    public void isRead(String id) {
//        messageReceiverMapper.isRead(id);
//    }

}
