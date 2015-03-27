package com.comdosoft.financial.user.controller.api;

import java.text.SimpleDateFormat;
import java.util.HashMap;
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
import com.comdosoft.financial.user.domain.zhangfu.WebMessage;
import com.comdosoft.financial.user.service.WebMessageService;
import com.comdosoft.financial.user.utils.page.Page;

/**
 * 
 * 系统公告<br>
 * <功能描述>
 *
 * @author gch 2015年2月4日
 *
 */
@RestController
@RequestMapping(value = "/api/web/message")
public class WebMessageController {
    private static final Logger logger = LoggerFactory.getLogger(WebMessageController.class);
    @Resource
    private WebMessageService webMessageService;

    @RequestMapping(value = "getAll", method = RequestMethod.POST)
    public Response getAll(@RequestBody MyOrderReq myOrderReq) {
        try{
            logger.debug("获取系统公告列表 start");
            Page<Object>  mrs= webMessageService.findAll(myOrderReq);
            logger.debug("获取系统公告列表 end"+mrs);
            return Response.getSuccess(mrs);
        }catch(NullPointerException e){
            return Response.buildErrorWithMissing();
        }catch(Exception e){
            logger.debug("获取系统公告表出错"+e);
            return Response.getError("请求失败");
        }
    }

    @RequestMapping(value = "getById", method = RequestMethod.POST)
    public Response findById(@RequestBody MyOrderReq myOrderReq) {
        try{
            logger.debug("获取系统公告详情 start");
            WebMessage wm= webMessageService.findById(myOrderReq.getId());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
            Map<String,String> map = new HashMap<String,String>();
            map.put("id", wm.getId().toString());
            map.put("title", wm.getTitle());
            map.put("create_at",sdf.format(wm.getCreateAt()));
            map.put("content", wm.getContent());
            logger.debug("获取系统公告详情 end"+wm);
            return Response.getSuccess(map);
        }catch(NullPointerException e){
            return Response.buildErrorWithMissing();
        }catch(Exception e){
            logger.debug("id:"+myOrderReq.getId()+"获取系统公告详情出错"+e);
            return Response.getError("请求失败");
        }
    }

}
