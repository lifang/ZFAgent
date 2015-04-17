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
import com.comdosoft.financial.user.domain.query.OrderReq;
import com.comdosoft.financial.user.domain.zhangfu.MyOrderReq;
import com.comdosoft.financial.user.service.OrderService;
import com.comdosoft.financial.user.utils.Exception.LessException;
import com.comdosoft.financial.user.utils.Exception.LowstocksException;
import com.comdosoft.financial.user.utils.Exception.NoneException;
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
//        try{
    		Integer id = myOrderReq.getCustomerId();
    		if(null == id){
    			return Response.getError("请传入用户id");
    		}
            Page<Object> centers = orderService.getWholesaleOrder(myOrderReq);
//            if(centers.getSize()<1){
//            	return Response.getError("请求的数据列表为空");
//            }
            return Response.getSuccess(centers);
//        }catch(NullPointerException e){
//            return Response.buildErrorWithMissing();
//        }catch(Exception e){
//            logger.debug("获取我的订单列表出错"+e);
//            return Response.getError("请求失败");
//        }
    }
    
    @RequestMapping(value="getWholesaleById" ,method=RequestMethod.POST)
    public Response getWholesaleById(@RequestBody MyOrderReq myOrderReq ) {
//        try{
    	Map<String,Object> centers = orderService.getWholesaleById(myOrderReq.getId());
            if(centers.isEmpty()){
                return Response.getError("此订单不存在");
            }
            return Response.getSuccess(centers);
//        }catch(NullPointerException e){
//            return Response.buildErrorWithMissing();
//        }catch(Exception e){
//            logger.debug("获取我的订单详情出错"+e);
//            return Response.getError("请求失败");
//        }
    }    
    
    @RequestMapping(value="cancelWholesale" ,method=RequestMethod.POST)
    public Response cancelWholesale(@RequestBody MyOrderReq myOrderReq ) {
        try{
          int i =   orderService.cancelMyOrder(myOrderReq);
            if(i==1){
                return Response.buildSuccess("", "取消成功");
            }else{
                return Response.getError( "操作失败");
            }
        }catch(Exception e){
            logger.debug("取消我的订单详情出错"+e);
            return Response.getError("取消失败");
        }
    }    
    
   
    
//    代购订单
    @RequestMapping(value="getProxyOrder" ,method=RequestMethod.POST)
    public Response getProxyOrder(@RequestBody MyOrderReq myOrderReq) {
//        try{
			Integer id = myOrderReq.getCustomerId();
			if(null == id){
				return Response.getError("请传入用户id");
			}
            Page<Object> centers = orderService.getProxyOrder(myOrderReq);
//            if(centers.getSize()<1){
//            	return Response.getError("请求的数据列表为空");
//            }
            return Response.getSuccess(centers);
//        }catch(NullPointerException e){
//            return Response.buildErrorWithMissing();
//        }catch(Exception e){
//            logger.debug("获取我的订单列表出错"+e);
//            return Response.getError("请求失败");
//        }
    }
    
    @RequestMapping(value="getProxyById" ,method=RequestMethod.POST)
    public Response getProxyById(@RequestBody MyOrderReq myOrderReq ) {
        try{
            Object centers = orderService.getProxyById(myOrderReq.getId());
            if(centers.equals("-1")){
                return Response.getError("此订单不存在");
            }
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
        	myOrderReq.setP("3");
            int  i = orderService.cancelMyOrder(myOrderReq);
            if(i==1){
                return Response.buildSuccess("", "取消成功");
            }else{
                return Response.getError( "操作失败");
            }
        }catch(Exception e){
            logger.debug("取消我的订单详情出错"+e);
            return Response.getError("取消失败");
        }
    }   
    
    // 订单搜索筛选
    @RequestMapping(value = "orderSearch", method = RequestMethod.POST)
    public Response orderSearch(@RequestBody MyOrderReq myOrderReq) {
//        try {
            Page<Object> centers = orderService.orderSearch(myOrderReq);
            return Response.getSuccess(centers);
//        } catch (NullPointerException e) {
//            return Response.buildErrorWithMissing();
//        } catch (Exception e) {
//            logger.debug("获取我的订单列表出错" + e);
//            return Response.getError("请求失败");
//        }
    }
    
    @RequestMapping(value = "payOrder", method = RequestMethod.POST)
    public Response orderPay(@RequestBody MyOrderReq myOrderReq) {
    	Map<String,Object> centers = orderService.orderPay(myOrderReq);
    	return Response.getSuccess(centers);
    }

   //  gch  end
  
    @RequestMapping(value = "agent", method = RequestMethod.POST)
    public Response createOrderFromAgent(@RequestBody OrderReq orderreq){
        Response resp=new Response();
        try {
            int  result = orderService.createOrderFromAgent(orderreq);
            resp.setCode(Response.SUCCESS_CODE);
            resp.setResult(result);
        } catch (LowstocksException e) {
            resp.setCode(-2);
            resp.setMessage("库存不足");
        } catch (LessException e) {
            resp.setCode(-3);
            resp.setMessage("批购少于最少批购数量");
        } catch (NoneException e) {
            resp.setCode(-4);
            resp.setMessage("订单类型不存在");
        }catch (Exception e) {
            resp.setCode(Response.ERROR_CODE);
            resp.setMessage("系统错误");
            e.printStackTrace();
        }
        return resp;
    }
    
}
