package com.comdosoft.financial.user.mapper.zhangfu;

import java.util.List;
import java.util.Map;

import com.comdosoft.financial.user.domain.zhangfu.MyOrderReq;

public interface CsChangeMapper {

    List<Map<String, Object>> findAll(MyOrderReq myOrderReq);

    void cancelApply(MyOrderReq myOrderReq);
    
    void addMark(MyOrderReq myOrderReq);

    Object findById(MyOrderReq myOrderReq);
}
