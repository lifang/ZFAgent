package com.comdosoft.financial.user.mapper.zhangfu;

import java.util.List;
import java.util.Map;

import com.comdosoft.financial.user.domain.query.ShopReq;





public interface ShopMapper {

    Map<String, Object> getLeaseOne(ShopReq shopReq);

    Map<String, Object> getShopOne(ShopReq shopReq);

    Map<String, Object> getPayOrder(ShopReq shopReq);

    List<Map<String, Object>> getPayOrderGood(ShopReq shopReq);
    

}