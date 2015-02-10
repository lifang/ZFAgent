package com.comdosoft.financial.user.mapper.zhangfu;

import java.util.List;
import java.util.Map;

import com.comdosoft.financial.user.domain.query.CartReq;




public interface ShopCartMapper {



    void delete(int cartId);

    void add(CartReq cartreq);

    void update(CartReq cartreq);

    int isExist(CartReq cartreq);

    List<Map<String,Object>> getList(CartReq cartreq);
    
    
}