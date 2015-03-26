package com.comdosoft.financial.user.mapper.zhangfu;


import java.util.List;
import java.util.Map;

import com.comdosoft.financial.user.domain.query.CommentReq;


public interface CommentMapper {

    int getCommentCount(int id);

    List<Map<String, Object>> getCommentList(CommentReq req);
    
    /**
     * 获得所有城市（省份）
     */
    List<Map<String, Object>> getParentCitiesList();
    
    /**
     * 根据省份ID获得该省份下面的市级
     */
    List<Map<String, Object>> getChildrenCitiesList(String id);

}