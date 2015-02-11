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
    
    //  gch  begin
    //订单列表
    @RequestMapping(value="getMyOrderAll" ,method=RequestMethod.POST)
    public Response getMyOrderAll(@RequestBody MyOrderReq myOrderReq) {
        try{
            logger.debug("获取我的订单列表 start");
            Page<Object> centers = orderService.findMyOrderAll(myOrderReq.getPage(), myOrderReq.getPageSize(),myOrderReq.getCustomer_id());
            logger.debug("获取我的订单列表 end"+centers);
            return Response.getSuccess(centers);
        }catch(Exception e){
            logger.debug("获取我的订单列表出错"+e);
            return Response.getError("请求失败");
        }
    }
    
    @RequestMapping(value="getMyOrderById" ,method=RequestMethod.POST)
    public Response getMyOrderById(@RequestBody MyOrderReq myOrderReq ) {
        try{
            logger.debug("获取我的订单详情 start");
            Object centers = orderService.findMyOrderById(myOrderReq.getId());
            logger.debug("获取我的订单详情 end"+centers);
            return Response.getSuccess(centers);
        }catch(Exception e){
            logger.debug("获取我的订单详情出错"+e);
            return Response.getError("请求失败");
        }
    }    
    
    @RequestMapping(value="cancelMyOrder" ,method=RequestMethod.POST)
    public Response cancelMyOrder(@RequestBody MyOrderReq myOrderReq ) {
        try{
            orderService.cancelMyOrder(myOrderReq);
            return Response.buildSuccess(null, "取消成功");
        }catch(Exception e){
            logger.debug("取消我的订单详情出错"+e);
            return Response.getError("取消失败");
        }
    }    
    
    @RequestMapping(value="saveComment" ,method=RequestMethod.POST)
    public Response comment(@RequestBody MyOrderReq myOrderReq ) {
        try{
            orderService.comment(myOrderReq);
            return Response.buildSuccess(null, "评论成功");
        }catch(Exception e){
            logger.debug("取消我的订单详情出错"+e);
            return Response.getError("评论失败");
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
