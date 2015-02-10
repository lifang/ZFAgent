package com.comdosoft.financial.user.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.comdosoft.financial.user.domain.Response;
import com.comdosoft.financial.user.domain.query.CartReq;
import com.comdosoft.financial.user.service.ShopCartService;

@RestController
@RequestMapping("api/cart")
public class ShopCartController {

    @Autowired
    private ShopCartService shopCartService ;
    
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public Response getList(@RequestBody CartReq cartreq){
        Response resp=new Response();
        List<?> cartList=shopCartService.getList(cartreq);
        resp.setCode(Response.SUCCESS_CODE);
        resp.setResult(cartList);
        return resp;
    }
    
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public Response delete(@RequestBody CartReq cartreq){
        Response resp=new Response();
        int result= shopCartService.delete(cartreq.getId());
        if(result==1){
            resp.setCode(Response.SUCCESS_CODE);
        }else{
            resp.setCode(Response.ERROR_CODE);
        }
        return resp;
    }
    
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public Response add(@RequestBody CartReq cartreq){
        Response resp=new Response();
        int result= shopCartService.add(cartreq);
        if(result==1){
            resp.setCode(Response.SUCCESS_CODE);
        }else{
            resp.setCode(Response.ERROR_CODE);
        }
        return resp;
    }
   
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public Response update(@RequestBody CartReq cartreq){
        Response resp=new Response();
        int result= shopCartService.update(cartreq);
        if(result==1){
            resp.setCode(Response.SUCCESS_CODE);
        }else{
            resp.setCode(Response.ERROR_CODE);
        }
        return resp;
    }
}
