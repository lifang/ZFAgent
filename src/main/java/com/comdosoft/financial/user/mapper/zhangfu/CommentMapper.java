package com.comdosoft.financial.user.mapper.zhangfu;


import java.util.List;
import java.util.Map;

import com.comdosoft.financial.user.domain.query.CommentReq;


public interface CommentMapper {

    int getCommentCount(int id);

    List<Map<String, Object>> getCommentList(CommentReq req);

}