package com.comdosoft.financial.user.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.comdosoft.financial.user.domain.zhangfu.WebMessage;
import com.comdosoft.financial.user.mapper.zhangfu.WebMessageMapper;
import com.comdosoft.financial.user.utils.page.Page;
import com.comdosoft.financial.user.utils.page.PageRequest;
@Service
public class WebMessageService {
    @Resource
    private WebMessageMapper webMessageMapper;
    
    public Page<Object> findAll(Integer page,Integer pageSize) {
        PageRequest request = new PageRequest(page, pageSize);
        int count = webMessageMapper.count();
        List<Object> centers = webMessageMapper.findAll(request);
        return new Page<Object>(request, centers, count);
    }

    public WebMessage findById(Integer id) {
        WebMessage webMessage = webMessageMapper.findById(id);
        return webMessage;
    }

}
