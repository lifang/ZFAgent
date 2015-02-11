package com.comdosoft.financial.user.mapper.zhangfu;

import java.util.List;

import com.comdosoft.financial.user.domain.zhangfu.SysMessage;
import com.comdosoft.financial.user.utils.page.PageRequest;

public interface MessageReceiverMapper {

    List<SysMessage> findAll(PageRequest request,int person_id);

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
