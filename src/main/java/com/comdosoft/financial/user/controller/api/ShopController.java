package com.comdosoft.financial.user.controller.api;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.comdosoft.financial.user.domain.Response;
import com.comdosoft.financial.user.domain.query.ShopReq;
import com.comdosoft.financial.user.service.ShopService;

@RestController
@RequestMapping("api/shop")
public class ShopController {

    @Autowired
    private ShopService shopService ;
    
   
    
    @RequestMapping(value = "getShop", method = RequestMethod.POST)
    public Response getShopOne(@RequestBody ShopReq shopReq){
        Response resp=new Response();
        Map<String,Object> map=shopService.getShop(shopReq);
        if(map==null){
            resp.setCode(Response.ERROR_CODE);
        }else{
            resp.setCode(Response.SUCCESS_CODE);
            resp.setResult(map);
        }
        return resp;
    }
    
    @RequestMapping(value = "payOrder", method = RequestMethod.POST)
    public Response payOrder(@RequestBody ShopReq shopReq) {
        Response resp = new Response();
        try {
            Map<String,Object> result = shopService.payOrder(shopReq);
            resp.setCode(Response.SUCCESS_CODE);
            resp.setResult(result);
        }catch (Exception e) {
            resp.setCode(Response.ERROR_CODE);
            resp.setMessage("系统出错");
            e.printStackTrace();
        }
        return resp;
    }


    
    
}
