package com.comdosoft.financial.user.mapper.zhangfu;

import java.util.List;

import com.comdosoft.financial.user.domain.zhangfu.MyOrderReq;
import com.comdosoft.financial.user.domain.zhangfu.SysMessage;

public interface MessageReceiverMapper {

    List<SysMessage> findAll(MyOrderReq myOrderReq);

    int count(int person_id);

    SysMessage findById(int parseInt);
    
    void delete(int parseInt);
    
    void batchDelete(String[] ids);
    /**
     * 批量设置为已读
     * @param ids
     */
    void batchUpdate(String[] ids);

    void isRead(int id);

}
