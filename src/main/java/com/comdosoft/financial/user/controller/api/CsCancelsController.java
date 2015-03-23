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
import com.comdosoft.financial.user.service.CsCencelsService;
import com.comdosoft.financial.user.utils.page.Page;

/**
 * 
 * 注销记录<br>
 * <功能描述>
 *
 * @author gch 2015年2月8日
 *
 */
@RestController
@RequestMapping(value="/api/cs/cancels")
public class CsCancelsController {
    
    private static final Logger logger = LoggerFactory.getLogger(CsCancelsController.class);
    @Resource
    private CsCencelsService csCencelsService;
    
    @RequestMapping(value="getAll" ,method=RequestMethod.POST)
    public Response getAll(@RequestBody MyOrderReq myOrderReq) {
        try{
            Page<List<Object>> centers = csCencelsService.findAll(myOrderReq);
//            if(centers.getSize()<1){
//            	return Response.getError("请求的数据列表为空");
//            }
            return Response.getSuccess(centers);
        }catch(Exception e){
            logger.debug("出错"+e+"==>>"+myOrderReq);
            return Response.getError("请求失败,获取数据出错。");
        }
    }
    
    @RequestMapping(value="getCanCelById" ,method=RequestMethod.POST)
    public Response getCanCelById(@RequestBody MyOrderReq myOrderReq){
        try{
            Map<String,Object> centers = csCencelsService.findById(myOrderReq);
            if(centers.isEmpty()){
            	return Response.getError("列表为空或请求出错");
            }
            return Response.getSuccess(centers);
        }catch(Exception e){
            logger.debug("出错"+e+"==>>"+myOrderReq);
            return Response.getError("请求失败,获取数据出错。");
        }
    }
    
    @RequestMapping(value="cancelApply" ,method=RequestMethod.POST)
    public Response cancelRepair(@RequestBody MyOrderReq myOrderReq ) {
        try{
          int i =   csCencelsService.cancelApply(myOrderReq);
            if(i==1){
                return Response.buildSuccess(null, "取消成功");
            }else{
                return Response.getError( "操作失败");
            }
        }catch(Exception e){
            logger.debug("出错"+e+"==>>"+myOrderReq);
            return Response.getError("取消失败");
        }
    }  
    
    /**
     * 重新提交注销
     * @param myOrderReq
     * @return
     */
    @RequestMapping(value="resubmitCancel" ,method=RequestMethod.POST)
    public Response resubmitCancel(@RequestBody MyOrderReq myOrderReq ) {
        try{
            csCencelsService.resubmitCancel(myOrderReq);
            return Response.buildSuccess(null, "提交成功");
        }catch(Exception e){
            logger.debug("出错"+e+"==>>"+myOrderReq);
            return Response.getError("提交失败");
        }
    }  
}
