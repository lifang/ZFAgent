package com.comdosoft.financial.user.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.comdosoft.financial.user.domain.zhangfu.MessageReceiver;
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
        int count = messageReceiverMapper.count(myOrderReq);
        List<MessageReceiver> centers = messageReceiverMapper.findAll(myOrderReq);
        List<Object> list = new ArrayList<Object>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String,Object> map = null;
        for(MessageReceiver s: centers){
            SysMessage sm = s.getSysMessage();
            if(null != sm){
            	  map.put("id", sm.getId() ==null ?"":sm.getId()+"");
                  map.put("title", sm.getTitle()==null ?"":sm.getTitle());
                  Date d = sm.getCreatedAt();
                  if(null ==d){
                	  map.put("create_at","");
                  }else{
                	  map.put("create_at",sdf.format(d));
                  }
                  map.put("content",sm.getContent() ==null ?"":sm.getContent());
            }
            map = new HashMap<String,Object>();
            if(null != s.getStatus() && s.getStatus()==1){
                map.put("status", true);
            }else{
                map.put("status", false);
            }
            list.add(map);
        }
        return new Page<Object>(request, list, count);
    }
    
    public SysMessage findById(MyOrderReq myOrderReq) {
        SysMessage sysMessage = messageReceiverMapper.findById(myOrderReq);
        messageReceiverMapper.isRead(myOrderReq);
        return sysMessage;
    }
     
    
    public int delete(MyOrderReq myOrderReq){
       return messageReceiverMapper.delete(myOrderReq);
    }
    
    public int batchDelete(MyOrderReq myOrderReq){
        return messageReceiverMapper.batchDelete(myOrderReq);
    }
    
    public int batchRead(MyOrderReq myOrderReq){
        return messageReceiverMapper.batchUpdate(myOrderReq);
    }

    public List<Map<String,Object>> getServerDynamic(MyOrderReq myOrderReq) {
        List<Map<String,Object>> list = messageReceiverMapper.getServerDynamic(myOrderReq);
        return list;
    }

    public void deleteAll(MyOrderReq myOrderReq) {
        messageReceiverMapper.deleteAll(myOrderReq);
    }

}
