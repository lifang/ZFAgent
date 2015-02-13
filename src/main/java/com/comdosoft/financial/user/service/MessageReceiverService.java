package com.comdosoft.financial.user.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
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
        PageRequest request = new PageRequest(myOrderReq.getPage(),myOrderReq.getPageSize());
        int count = messageReceiverMapper.count(myOrderReq.getCustomer_id());
        List<SysMessage> centers = messageReceiverMapper.findAll(myOrderReq);
        List<Object> list = new ArrayList<Object>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String,String> map = null;
        for(SysMessage s: centers){
            map = new HashMap<String,String>();
            map.put("id", s.getId().toString());
            map.put("title", s.getTitle());
            map.put("create_at",sdf.format(s.getCreatedAt()));
            map.put("content", s.getContent());
            list.add(map);
        }
        return new Page<Object>(request, list, count);
    }
    
    public SysMessage findById(Integer id) {
        SysMessage sysMessage = messageReceiverMapper.findById(id);
        messageReceiverMapper.isRead(id);
        return sysMessage;
    }
    
    public void delete(Integer id){
        messageReceiverMapper.delete(id);
    }
    
    public void batchDelete(String[] ids){
        messageReceiverMapper.batchDelete(ids);
    }
    
    public void batchRead(String[] ids){
        messageReceiverMapper.batchUpdate(ids);
    }

//    public void isRead(String id) {
//        messageReceiverMapper.isRead(id);
//    }

}
