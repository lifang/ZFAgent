package com.comdosoft.financial.user.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.comdosoft.financial.user.domain.zhangfu.MyOrderReq;
import com.comdosoft.financial.user.domain.zhangfu.OrderStatus;
import com.comdosoft.financial.user.mapper.zhangfu.CsRepairMapper;
import com.comdosoft.financial.user.utils.page.Page;
import com.comdosoft.financial.user.utils.page.PageRequest;

@Service
public class CsRepairService {
    
    @Resource
    private CsRepairMapper repairMapper;

    public Page<Map<String,Object>> findAll(MyOrderReq myOrderReq) {
        PageRequest request = new PageRequest(myOrderReq.getPage(), myOrderReq.getPageSize());
        List<Map<String, Object>> o = repairMapper.findRepairsAll(myOrderReq);
        return new Page<Map<String,Object>>(request, o);
    }

    public void cancelApply(MyOrderReq myOrderReq) {
        myOrderReq.setOrderStatus(OrderStatus.CANCEL);
        repairMapper.cancelApply(myOrderReq);
    }

    public Object findById(MyOrderReq myOrderReq) {
        Object o = repairMapper.findById(myOrderReq);
        return o;
    }
    
    public void addMark(MyOrderReq myOrderReq) {
        repairMapper.addMark(myOrderReq);
    }
}
