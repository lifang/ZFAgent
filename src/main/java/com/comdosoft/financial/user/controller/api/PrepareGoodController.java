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
import com.comdosoft.financial.user.domain.query.PrepareGoodReq;
import com.comdosoft.financial.user.service.PrepareGoodService;

@RestController
@RequestMapping("api/preparegood")
public class PrepareGoodController {

    @Autowired
    private PrepareGoodService repareGoodService ;

    @RequestMapping(value = "list", method = RequestMethod.POST)
    public Response getList(@RequestBody  PrepareGoodReq req){
        Response response = new Response();
        Map<String,Object> result= repareGoodService.getList(req);
        response.setCode(Response.SUCCESS_CODE);
        response.setResult(result);
        return response;
    }
    
   
    
    @RequestMapping(value = "info", method = RequestMethod.POST)
    public Response getInfo(@RequestBody  PrepareGoodReq req){
        Response response = new Response();
        Map<String,Object> result= repareGoodService.getInfo(req);
        response.setCode(Response.SUCCESS_CODE);
        response.setResult(result);
        return response;
    }
    
    @RequestMapping(value = "winfo", method = RequestMethod.POST)
    public Response getwInfo(@RequestBody  PrepareGoodReq req){
        Response response = new Response();
        Map<String,Object> result= repareGoodService.getwInfo(req);
        response.setCode(Response.SUCCESS_CODE);
        response.setResult(result);
        return response;
    }
    
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public Response add(@RequestBody  PrepareGoodReq req){
        Response response = new Response();
        response.setCode(Response.ERROR_CODE);
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
        int result= repareGoodService.add(req);
        if(result==1){
            response.setCode(Response.SUCCESS_CODE);
        }
        return response;
    }
    
    @RequestMapping(value = "getsonagent", method = RequestMethod.POST)
    public Response getSonAgent(@RequestBody  PrepareGoodReq req){
        Response response = new Response();
        response.setCode(Response.SUCCESS_CODE);
        List<Map<String,Object>> list=repareGoodService.getSonAgent(req);
        response.setResult(list);
        return response;
    }
    
    @RequestMapping(value = "getgoodlist", method = RequestMethod.POST)
    public Response getGoodList(@RequestBody  PrepareGoodReq req){
        Response response = new Response();
        response.setCode(Response.SUCCESS_CODE);
        List<Map<String,Object>> list=repareGoodService.getGoodList(req);
        response.setResult(list);
        return response;
    }
    
    @RequestMapping(value = "getpaychannellist", method = RequestMethod.POST)
    public Response getPayChannelList(@RequestBody  PrepareGoodReq req){
        Response response = new Response();
        response.setCode(Response.SUCCESS_CODE);
        List<Map<String,Object>> list=repareGoodService.getPayChannelList(req);
        response.setResult(list);
        return response;
    }
    
    @RequestMapping(value = "getterminalslist", method = RequestMethod.POST)
    public Response getTerminalsList(@RequestBody  PrepareGoodReq req){
        Response response = new Response();
        response.setCode(Response.SUCCESS_CODE);
        Map<String,Object> list=repareGoodService.getTerminalsList(req);
        response.setResult(list);
        return response;
    }
    
    @RequestMapping(value = "checkTerminals", method = RequestMethod.POST)
    public Response checkTerminals(@RequestBody  PrepareGoodReq req){
        Response response = new Response();
        response.setCode(Response.SUCCESS_CODE);
        Map<String,Object> list=repareGoodService.checkTerminals(req);
        response.setResult(list);
        return response;
    }
   
}
