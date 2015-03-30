package com.comdosoft.financial.user.mapper.zhangfu;

import java.util.List;

import com.comdosoft.financial.user.domain.zhangfu.MyOrderReq;
import com.comdosoft.financial.user.domain.zhangfu.WebMessage;

public interface WebMessageMapper {

    int count();

    List<WebMessage> findAll(MyOrderReq myOrderReq);

    WebMessage findById(Integer id);

}
