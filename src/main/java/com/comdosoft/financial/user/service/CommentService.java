package com.comdosoft.financial.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comdosoft.financial.user.domain.Paging;
import com.comdosoft.financial.user.domain.query.CommentReq;
import com.comdosoft.financial.user.mapper.zhangfu.CommentMapper;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    public Map<String, Object> getList(CommentReq req) {
        Map<String, Object> map=new HashMap<String, Object>();
        int total=commentMapper.getCommentCount(req.getGoodId());
        if(null==req.getPaging()){
            req.setPaging(new Paging(1, 10));
        }
        List<Map<String, Object>> list=commentMapper.getCommentList(req);
        map.put("total", total);
        map.put("list", list);
        return map;
    }


}
