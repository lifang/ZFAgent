package com.comdosoft.financial.user.service;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comdosoft.financial.user.domain.query.ShopReq;
import com.comdosoft.financial.user.mapper.zhangfu.GoodMapper;
import com.comdosoft.financial.user.mapper.zhangfu.ShopMapper;

@Service
public class ShopService {

    @Autowired
    private ShopMapper shopMapper;
    
    @Autowired
    private GoodMapper goodMapper;
    
    

    public Map<String, Object> getShop(ShopReq shopReq) {
        Map<String, Object> map=null;
        if(4==shopReq.getOrderType()){
            map=shopMapper.getLeaseOne(shopReq);
        }else if(3==shopReq.getOrderType()){
            map=shopMapper.getShopOne(shopReq);
        }else if(5==shopReq.getOrderType()){
            map=shopMapper.getShopOne(shopReq);
        }
        if(map==null){
            return map;
        }
        int goodId =Integer.valueOf(""+map.get("goodId")); 
        //图片
        List<String> goodPics=goodMapper.getgoodPics(goodId);
        if(null!=goodPics&&goodPics.size()>0){
            map.put("url_path",goodPics.get(0));
        }
        return map;
    }

    public Map<String, Object> payOrder(ShopReq shopReq) {
        Map<String, Object> map = shopMapper.getPayOrder(shopReq);
        map.put("good", shopMapper.getPayOrderGood(shopReq));
        return map;
    }

  
}
