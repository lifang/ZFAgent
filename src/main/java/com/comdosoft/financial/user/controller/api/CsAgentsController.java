package com.comdosoft.financial.user.controller.api;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.comdosoft.financial.user.domain.Response;
import com.comdosoft.financial.user.domain.zhangfu.MyOrderReq;
import com.comdosoft.financial.user.service.CsAgentsService;
import com.comdosoft.financial.user.utils.page.Page;

/**
 * 
 * 售后单记录<br>
 * <功能描述>
 *
 * @author gch 2015年2月12日
 *
 */
@RestController
@RequestMapping(value="/api/cs/agents")
public class CsAgentsController {
    
    private static final Logger logger = LoggerFactory.getLogger(CsAgentsController.class);
    @Resource
    private CsAgentsService csAgentsService;
    
    @RequestMapping(value="getAll" ,method=RequestMethod.POST)
    public Response getAll(@RequestBody MyOrderReq myOrderReq) {
        try{
            Page<List<Object>> centers = csAgentsService.findAll(myOrderReq);
            if(centers.getTotal()<1){
            	return Response.buildMisSuccess();
            }
            return Response.getSuccess(centers);
        }catch(Exception e){
            logger.debug("出错"+e+"==>>"+myOrderReq);
            return Response.getError("请求失败,获取数据出错。");
        }
    }
    
    @RequestMapping(value="getById" ,method=RequestMethod.POST)
    public Response getCanCelById(@RequestBody MyOrderReq myOrderReq){
        try{
            Map<String,Object> centers = csAgentsService.findById(myOrderReq);
            if(centers.isEmpty()){
            	return Response.buildMisSuccess();
            }
            return Response.getSuccess(centers);
        }catch(Exception e){
        	e.printStackTrace();
            return Response.getError("出错了");
        }
    }
    
    @RequestMapping(value="cancelApply" ,method=RequestMethod.POST)
    public Response cancelRepair(@RequestBody MyOrderReq myOrderReq ) {
        try{
            Integer i =  csAgentsService.cancelApply(myOrderReq);
            if(i==1){
                return Response.buildSuccess("", "取消成功");
            }else{
            	return Response.buildMisSuccess();
            }
        }catch(Exception e){
        	e.printStackTrace();
            return Response.getError("操作出错");
        }
    }  
    
}
