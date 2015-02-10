package com.comdosoft.financial.user.controller.api;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.comdosoft.financial.user.domain.Response;
import com.comdosoft.financial.user.domain.zhangfu.MyOrderReq;
import com.comdosoft.financial.user.domain.zhangfu.SysMessage;
import com.comdosoft.financial.user.service.MessageReceiverService;
import com.comdosoft.financial.user.utils.page.Page;
/**
 * 
 * 我的消息<br>
 * <功能描述>
 *
 * @author gch 2015年2月4日
 *
 */
@RestController
@RequestMapping(value="/api/message/receiver")
public class MessageReceiverController {
    private static final Logger logger = LoggerFactory.getLogger(MessageReceiverController.class);
    @Resource
    private MessageReceiverService messageReceiverService;
    
    @RequestMapping(value="getAll",method=RequestMethod.POST)
    public Response getAll(@RequestBody MyOrderReq myOrderReq){
        try{
            logger.debug("获取我的消息列表 start");
            Page<Object> mrs= messageReceiverService.findAll(myOrderReq.getPage(),myOrderReq.getPageSize(),myOrderReq.getCustomer_id());
            logger.debug("获取我的消息列表 end"+mrs);
            return Response.getSuccess(mrs);
        }catch(Exception e){
            logger.debug("获取我的消息列表出错"+e);
            return Response.getError("请求失败");
        }
    }
    
    @RequestMapping(value="getById",method=RequestMethod.POST)
    public Response getById(@RequestBody MyOrderReq myOrderReq){
        try{
            logger.debug("获取我的消息详情 start");
            SysMessage sysMessage = messageReceiverService.findById(myOrderReq.getId());
            logger.debug("获取我的消息详情 end"+sysMessage);
            return Response.getSuccess(sysMessage);
        }catch(Exception e){
            logger.debug("获取我的消息详情出错"+e);
            return Response.getError("请求失败");
        }
    }
    
    @RequestMapping(value="deleteById",method=RequestMethod.POST)
    public Response deleteById(@RequestBody MyOrderReq myOrderReq){
        try{
            logger.debug("根据id删除我的消息 start");
            messageReceiverService.delete(myOrderReq.getId());
            logger.debug("根据id删除我的消息 end");
            return Response.buildSuccess(null, "删除成功");
        }catch(Exception e){
            logger.debug("根据id删除我的消息出错"+e);
            return Response.getError("请求失败");
        }
    }
    
    @RequestMapping(value="batchDelete",method=RequestMethod.POST)
    public Response batchDelete(@RequestBody MyOrderReq myOrderReq){
        try{
            logger.debug("根据ids[]批量删除我的消息 start");
            messageReceiverService.batchDelete(myOrderReq.getIds());
            logger.debug("根据ids[]批量删除我的消息end");
            return Response.buildSuccess(null, "删除成功");
        }catch(Exception e){
            logger.debug("根据ids[]批量删除我的消息出错"+e);
            return Response.getError("请求失败");
        }
    }
    
    @RequestMapping(value="batchRead",method=RequestMethod.POST)
    public Response batchRead(@RequestBody MyOrderReq myOrderReq){
        try{
            logger.debug("根据ids[]批量设置我的消息已读 start");
            messageReceiverService.batchRead(myOrderReq.getIds());
            logger.debug("根据ids[]批量设置我的消息已读 end");
            return Response.buildSuccess(null, "已读设置成功");
        }catch(Exception e){
            logger.debug("根据ids[]批量设置我的消息已读 出错"+e);
            return Response.getError("请求失败");
        }
    }
}
