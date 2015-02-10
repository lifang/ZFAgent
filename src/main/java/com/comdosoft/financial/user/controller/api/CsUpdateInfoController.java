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
import com.comdosoft.financial.user.service.CsUpdateInfoService;
import com.comdosoft.financial.user.utils.page.Page;

/**
 * 
 * 维修记录<br>
 * <功能描述>
 *
 * @author gch 2015年2月8日
 *
 */
@RestController
@RequestMapping(value="/api/update/info")
public class CsUpdateInfoController {
    
    private static final Logger logger = LoggerFactory.getLogger(CsUpdateInfoController.class);
    @Resource
    private CsUpdateInfoService csUpdateInfoService;
    
    //  gch  begin
    //维修记录
    @RequestMapping(value="getAll" ,method=RequestMethod.POST)
    public Response getAll(@RequestBody MyOrderReq myOrderReq) {
        try{
            Page<Map<String,Object>> centers = csUpdateInfoService.findAll(myOrderReq);
            return Response.getSuccess(centers);
        }catch(Exception e){
            logger.debug("出错"+e+"==>>"+myOrderReq);
            return Response.getError("请求失败");
        }
    }
    
    @RequestMapping(value="getInfoById" ,method=RequestMethod.POST)
    public Response getCanCelById(@RequestBody MyOrderReq myOrderReq){
        try{
            Object centers = csUpdateInfoService.findById(myOrderReq);
            return Response.getSuccess(centers);
        }catch(Exception e){
            logger.debug("出错"+e+"==>>"+myOrderReq);
            return Response.getError("请求失败");
        }
    }
    @RequestMapping(value="cancelApply" ,method=RequestMethod.POST)
    public Response cancelRepair(@RequestBody MyOrderReq myOrderReq ) {
        try{
            csUpdateInfoService.cancelApply(myOrderReq);
            return Response.buildSuccess(null, "取消成功");
        }catch(Exception e){
            logger.debug("出错"+e+"==>>"+myOrderReq);
            return Response.getError("取消失败");
        }
    }  
}
