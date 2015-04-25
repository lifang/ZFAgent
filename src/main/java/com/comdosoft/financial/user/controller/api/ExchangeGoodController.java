package com.comdosoft.financial.user.controller.api;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;










import com.comdosoft.financial.user.domain.Response;
import com.comdosoft.financial.user.domain.query.ExchangeGoodReq;
import com.comdosoft.financial.user.service.ExchangeGoodService;

@RestController
@RequestMapping("api/exchangegood")
public class ExchangeGoodController {

    @Autowired
    private ExchangeGoodService exchangeGoodService ;

    @RequestMapping(value = "list", method = RequestMethod.POST)
    public Response getList(@RequestBody  ExchangeGoodReq req){
        Response response = new Response();
        Map<String,Object> result= exchangeGoodService.getList(req);
        response.setCode(Response.SUCCESS_CODE);
        response.setResult(result);
        return response;
    }
    
    @RequestMapping(value = "info", method = RequestMethod.POST)
    public Response getInfo(@RequestBody  ExchangeGoodReq req){
        Response response = new Response();
        Map<String,Object> result= exchangeGoodService.getInfo(req);
        response.setCode(Response.SUCCESS_CODE);
        response.setResult(result);
        return response;
    }
    
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public Response add(@RequestBody  ExchangeGoodReq req){
        if(1==req.getWeb()){
            String[] ss = req.getSerialNum().split("\\s+|,|;");
            List<String> list = new ArrayList<String>();
            for (String string : ss) {
                if (!"".equals(string.trim())) {
                    list.add(string.trim());
                }
            }
            req.setSerialNums((String[])list.toArray(new String[list.size()]));
        }
        int result= exchangeGoodService.add(req);
        if(result==1){
            return Response.getSuccess();
        }else if(result==-1){
            return Response.getError("终端为空!");
        }else{
            return Response.getError("系统出错!");
        }
        
    }
   
    @RequestMapping(value = "getterminalslist", method = RequestMethod.POST)
    public Response getTerminalsList(@RequestBody  ExchangeGoodReq req){
        Response response = new Response();
        response.setCode(Response.SUCCESS_CODE);
        Map<String,Object> list=exchangeGoodService.getTerminalsList(req);
        response.setResult(list);
        return response;
    }
    
    @RequestMapping(value = "checkTerminals", method = RequestMethod.POST)
    public Response checkTerminals(@RequestBody  ExchangeGoodReq req){
        Response response = new Response();
        response.setCode(Response.SUCCESS_CODE);
        Map<String,Object> list=exchangeGoodService.checkTerminals(req);
        response.setResult(list);
        return response;
    }
}
