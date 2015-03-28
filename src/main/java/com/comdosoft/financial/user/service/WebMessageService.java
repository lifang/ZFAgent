package com.comdosoft.financial.user.service;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.comdosoft.financial.user.domain.zhangfu.MyOrderReq;
import com.comdosoft.financial.user.domain.zhangfu.WebMessage;
import com.comdosoft.financial.user.mapper.zhangfu.WebMessageMapper;
import com.comdosoft.financial.user.utils.page.Page;
import com.comdosoft.financial.user.utils.page.PageRequest;
@Service
public class WebMessageService {
    @Resource
    private WebMessageMapper webMessageMapper;
    
    public Page<Object> findAll(MyOrderReq myOrderReq) {
        PageRequest request = new PageRequest(myOrderReq.getPage(),myOrderReq.getRows());
        int count = webMessageMapper.count();
        List<WebMessage> centers = webMessageMapper.findAll(myOrderReq);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
        List<Object> listMap = new LinkedList<Object>();
        Map<String,String> map = null;
        for(WebMessage o: centers){
            map = new HashMap<String,String>();
            map.put("id", o.getId().toString());
            map.put("title", o.getTitle());
            map.put("create_at",sdf.format(o.getCreateAt()));
            map.put("content", o.getContent());
            listMap.add(map);
        }
        return new Page<Object>(request, listMap, count);
    }

    public WebMessage findById(Integer id) {
        WebMessage webMessage = webMessageMapper.findById(id);
        return webMessage;
    }

}
