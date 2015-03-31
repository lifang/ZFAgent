package com.comdosoft.financial.user.mapper.zhangfu;

import java.util.List;
import java.util.Map;

import com.comdosoft.financial.user.domain.zhangfu.MyOrderReq;

public interface CsUpdateInfoMapper {

    List<Map<String, Object>> findAll(MyOrderReq myOrderReq);

    void cancelApply(MyOrderReq myOrderReq);
    
    Map<String, Object> findById(MyOrderReq myOrderReq);

    List<Map<String, Object>> findTraceById(MyOrderReq myOrderReq);

    int count(MyOrderReq myOrderReq);

    List<Map<String, Object>> search(MyOrderReq myOrderReq);

    int countSearch(MyOrderReq myOrderReq);
    
    void changeStatus(MyOrderReq myOrderReq);

}
