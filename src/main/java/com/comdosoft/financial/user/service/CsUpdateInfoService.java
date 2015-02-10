package com.comdosoft.financial.user.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.comdosoft.financial.user.domain.zhangfu.MyOrderReq;
import com.comdosoft.financial.user.domain.zhangfu.OrderStatus;
import com.comdosoft.financial.user.mapper.zhangfu.CsUpdateInfoMapper;
import com.comdosoft.financial.user.utils.page.Page;
import com.comdosoft.financial.user.utils.page.PageRequest;
@Service
public class CsUpdateInfoService {

    @Resource
    private CsUpdateInfoMapper csUpdateInfoMapper;
    
    public Page<Map<String,Object>> findAll(MyOrderReq myOrderReq) {
        PageRequest request = new PageRequest(myOrderReq.getPage(), myOrderReq.getPageSize());
        List<Map<String, Object>> o = csUpdateInfoMapper.findAll(myOrderReq);
        return new Page<Map<String,Object>>(request, o);
    }

    public void cancelApply(MyOrderReq myOrderReq) {
        myOrderReq.setOrderStatus(OrderStatus.CANCEL);
        csUpdateInfoMapper.cancelApply(myOrderReq);
    }

    public Object findById(MyOrderReq myOrderReq) {
        Object o = csUpdateInfoMapper.findById(myOrderReq);
        return o;
    }
}
