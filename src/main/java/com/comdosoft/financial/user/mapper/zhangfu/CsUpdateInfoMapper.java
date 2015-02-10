package com.comdosoft.financial.user.mapper.zhangfu;

import java.util.List;
import java.util.Map;

import com.comdosoft.financial.user.domain.zhangfu.MyOrderReq;

public interface CsUpdateInfoMapper {

    List<Map<String, Object>> findAll(MyOrderReq myOrderReq);

    void cancelApply(MyOrderReq myOrderReq);
    
    Object findById(MyOrderReq myOrderReq);

}
