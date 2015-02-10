package com.comdosoft.financial.user.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.comdosoft.financial.user.domain.zhangfu.MyOrderReq;
import com.comdosoft.financial.user.domain.zhangfu.RepairStatus;
import com.comdosoft.financial.user.mapper.zhangfu.CsCencelsMapper;
import com.comdosoft.financial.user.utils.page.Page;
import com.comdosoft.financial.user.utils.page.PageRequest;
@Service
public class CsCencelsService {

    @Resource
    private CsCencelsMapper csCencelsMapper;
    public Page<Map<String,Object>> findAll(MyOrderReq myOrderReq) {
        PageRequest request = new PageRequest(myOrderReq.getPage(), myOrderReq.getPageSize());
        List<Map<String, Object>> o = csCencelsMapper.findAll(myOrderReq);
        return new Page<Map<String,Object>>(request, o);
    }

    public void cancelApply(MyOrderReq myOrderReq) {
        myOrderReq.setRepairStatus(RepairStatus.CANCEL);
        csCencelsMapper.cancelApply(myOrderReq);
    }

    public Object findById(MyOrderReq myOrderReq) {
        Object o = csCencelsMapper.findById(myOrderReq);
        return o;
    }

}
