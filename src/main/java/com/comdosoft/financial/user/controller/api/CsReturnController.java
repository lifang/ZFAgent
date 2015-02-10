package com.comdosoft.financial.user.controller.api;

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
import com.comdosoft.financial.user.service.CsReturnService;
import com.comdosoft.financial.user.utils.page.Page;

/**
 * 
 * 退货记录<br>
 * <功能描述>
 *
 * @author gch 2015年2月8日
 *
 */
@RestController
@RequestMapping(value="/api/return")
public class CsReturnController {
    
    private static final Logger logger = LoggerFactory.getLogger(CsReturnController.class);
    @Resource
    private CsReturnService csReturnService;
    
    @RequestMapping(value="getAll" ,method=RequestMethod.POST)
    public Response getAll(@RequestBody MyOrderReq myOrderReq) {
        try{
            Page<Map<String,Object>> centers = csReturnService.findAll(myOrderReq);
            return Response.getSuccess(centers);
        }catch(Exception e){
            logger.debug("出错"+e+"==>>"+myOrderReq);
            return Response.getError("请求失败");
        }
    }
    
    @RequestMapping(value="cancelApply" ,method=RequestMethod.POST)
    public Response cancelRepair(@RequestBody MyOrderReq myOrderReq ) {
        try{
            csReturnService.cancelApply(myOrderReq);
            return Response.buildSuccess(null, "取消成功");
        }catch(Exception e){
            logger.debug("出错"+e+"==>>"+myOrderReq);
            return Response.getError("取消失败");
        }
    }  
    
    @RequestMapping(value="getReturnById" ,method=RequestMethod.POST)
    public Response getCanCelById(@RequestBody MyOrderReq myOrderReq){
        try{
            Object centers = csReturnService.findById(myOrderReq);
            return Response.getSuccess(centers);
        }catch(Exception e){
            logger.debug("出错"+e+"==>>"+myOrderReq);
            return Response.getError("请求失败");
        }
    }
    
    @RequestMapping(value="addMark" ,method=RequestMethod.POST)
    public Response addMark(@RequestBody MyOrderReq myOrderReq ) {
        try{
            String content = myOrderReq.getComputer_name()+myOrderReq.getTrack_number();
            myOrderReq.setCentent(content);
            csReturnService.addMark(myOrderReq);
            return Response.buildSuccess(null, "保存成功");
        }catch(Exception e){
            logger.debug("出错"+e+"==>>"+myOrderReq);
            return Response.getError("保存失败");
        }
    } 
}
