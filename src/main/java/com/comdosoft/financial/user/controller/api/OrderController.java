package com.comdosoft.financial.user.controller.api;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.comdosoft.financial.user.domain.Response;
import com.comdosoft.financial.user.domain.query.OrderReq;
import com.comdosoft.financial.user.domain.zhangfu.MyOrderReq;
import com.comdosoft.financial.user.service.OrderService;
import com.comdosoft.financial.user.utils.page.Page;

/**
 * 
 * 订单服务<br>
 * <功能描述>
 *
 * @author gch 2015年2月4日
 *
 */
@RestController
@RequestMapping(value="/api/order")
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    @Resource
    private OrderService orderService;
    //批购订单
    @RequestMapping(value="getWholesaleOrder" ,method=RequestMethod.POST)
    public Response getWholesaleOrder(@RequestBody MyOrderReq myOrderReq) {
        try{
            Page<Object> centers = orderService.getWholesaleOrder(myOrderReq);
            return Response.getSuccess(centers);
        }catch(NullPointerException e){
            return Response.buildErrorWithMissing();
        }catch(Exception e){
            logger.debug("获取我的订单列表出错"+e);
            return Response.getError("请求失败");
        }
    }
    
    @RequestMapping(value="getWholesaleById" ,method=RequestMethod.POST)
    public Response getWholesaleById(@RequestBody MyOrderReq myOrderReq ) {
        try{
            Object centers = orderService.getWholesaleById(myOrderReq.getId());
            return Response.getSuccess(centers);
        }catch(NullPointerException e){
            return Response.buildErrorWithMissing();
        }catch(Exception e){
            logger.debug("获取我的订单详情出错"+e);
            return Response.getError("请求失败");
        }
    }    
    
    @RequestMapping(value="cancelWholesale" ,method=RequestMethod.POST)
    public Response cancelWholesale(@RequestBody MyOrderReq myOrderReq ) {
        try{
            orderService.cancelMyOrder(myOrderReq);
            return Response.buildSuccess(null, "取消成功");
        }catch(Exception e){
            logger.debug("取消我的订单详情出错"+e);
            return Response.getError("取消失败");
        }
    }    
    
   
    
//    代购订单
    @RequestMapping(value="getProxyOrder" ,method=RequestMethod.POST)
    public Response getProxyOrder(@RequestBody MyOrderReq myOrderReq) {
        try{
            Page<Object> centers = orderService.getProxyOrder(myOrderReq);
            return Response.getSuccess(centers);
        }catch(NullPointerException e){
            return Response.buildErrorWithMissing();
        }catch(Exception e){
            logger.debug("获取我的订单列表出错"+e);
            return Response.getError("请求失败");
        }
    }
    
    @RequestMapping(value="getProxyById" ,method=RequestMethod.POST)
    public Response getProxyById(@RequestBody MyOrderReq myOrderReq ) {
        try{
            Object centers = orderService.getProxyById(myOrderReq.getId());
            return Response.getSuccess(centers);
        }catch(NullPointerException e){
            return Response.buildErrorWithMissing();
        }catch(Exception e){
            logger.debug("获取我的订单详情出错"+e);
            return Response.getError("请求失败");
        }
    }    
    
    @RequestMapping(value="cancelProxy" ,method=RequestMethod.POST)
    public Response cancelProxy(@RequestBody MyOrderReq myOrderReq ) {
        try{
            orderService.cancelMyOrder(myOrderReq);
            return Response.buildSuccess(null, "取消成功");
        }catch(Exception e){
            logger.debug("取消我的订单详情出错"+e);
            return Response.getError("取消失败");
        }
    }   
   //  gch  end
  
    @RequestMapping(value = "agent", method = RequestMethod.POST)
    public Response createOrderFromAgent(@RequestBody OrderReq orderreq){
        Response resp=new Response();
        int result= orderService.createOrderFromAgent(orderreq);
        if(result==1){
            resp.setCode(Response.SUCCESS_CODE);
        }else{
            resp.setCode(Response.ERROR_CODE);
        }
        return resp;
    }
    
}
