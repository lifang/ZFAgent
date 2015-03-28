package com.comdosoft.financial.user.mapper.zhangfu;

import java.util.List;
import java.util.Map;

import com.comdosoft.financial.user.domain.zhangfu.MessageReceiver;
import com.comdosoft.financial.user.domain.zhangfu.MyOrderReq;
import com.comdosoft.financial.user.domain.zhangfu.SysMessage;

public interface MessageReceiverMapper {

    List<MessageReceiver> findAll(MyOrderReq myOrderReq);

    int count(MyOrderReq myOrderReq);

    SysMessage findById(MyOrderReq myOrderReq);
    
    int delete(MyOrderReq myOrderReq);
    
    int batchDelete(MyOrderReq myOrderReq);
    /**
     * 批量设置为已读
     * @param ids
     */
    int batchUpdate(MyOrderReq myOrderReq );

    int isRead(MyOrderReq myOrderReq);

    List<Map<String, Object>> getServerDynamic(MyOrderReq myOrderReq);

    void deleteAll(MyOrderReq myOrderReq);

}
