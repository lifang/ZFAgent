package com.comdosoft.financial.user.controller.api;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;





import com.comdosoft.financial.user.domain.Response;
import com.comdosoft.financial.user.domain.query.StockReq;
import com.comdosoft.financial.user.service.StockService;

@RestController
@RequestMapping("api/stock")
public class StockController {

    @Autowired
    private StockService stockService ;

    @RequestMapping(value = "list", method = RequestMethod.POST)
    public Response getStocksList(@RequestBody  StockReq req){
        Response response = new Response();
        Map<String,Object> stock= stockService.getList(req);
        response.setCode(Response.SUCCESS_CODE);
        response.setResult(stock);
        return response;
    }
    
    @RequestMapping(value = "info", method = RequestMethod.POST)
    public Response getStockInfo(@RequestBody  StockReq req){
        Response response = new Response();
        List<Map<String,Object>> stock= stockService.getInfo(req);
        response.setCode(Response.SUCCESS_CODE);
        response.setResult(stock);
        return response;
    }
    
    @RequestMapping(value = "rename", method = RequestMethod.POST)
    public Response rename(@RequestBody  StockReq req){
        Response response = new Response();
        response.setCode(Response.ERROR_CODE);
        int stock= stockService.rename(req);
        if(stock==1){
            response.setCode(Response.SUCCESS_CODE);
        }
        return response;
    }
   
}
