package com.comdosoft.financial.user.controller.api;

import java.text.SimpleDateFormat;
import java.util.HashMap;
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
@RequestMapping(value = "/api/message/receiver")
public class MessageReceiverController {
	private static final Logger logger = LoggerFactory.getLogger(MessageReceiverController.class);
	@Resource
	private MessageReceiverService messageReceiverService;

	@RequestMapping(value = "getAll", method = RequestMethod.POST)
	public Response getAll(@RequestBody MyOrderReq myOrderReq) {
		// try{
		Page<Object> mrs = messageReceiverService.findAll(myOrderReq);
		return Response.getSuccess(mrs);
		// }catch(NullPointerException e){
		// return Response.buildErrorWithMissing();
		// }catch(Exception e){
		// logger.debug("获取我的消息列表出错"+e);
		// return Response.getError("请求失败");
		// }
	}

	@RequestMapping(value = "getById", method = RequestMethod.POST)
	public Response getById(@RequestBody MyOrderReq myOrderReq) {
		try {
			SysMessage sysMessage = messageReceiverService.findById(myOrderReq);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", sysMessage.getId().toString());
			map.put("title", sysMessage.getTitle());
			map.put("create_at", sdf.format(sysMessage.getCreatedAt()));
			map.put("content", sysMessage.getContent());
			return Response.getSuccess(map);
		} catch (NullPointerException e) {
			return Response.buildErrorWithMissing();
		} catch (Exception e) {
			logger.debug("获取我的消息详情出错" + e);
			return Response.getError("请求失败");
		}
	}

	@RequestMapping(value = "deleteById", method = RequestMethod.POST)
	public Response deleteById(@RequestBody MyOrderReq myOrderReq) {
		try {
			int i = messageReceiverService.delete(myOrderReq);
			if (i == 1) {
				return Response.buildSuccess("", "删除成功");
			} else {
				return Response.buildMisSuccess();
			}
		} catch (Exception e) {
			logger.debug("根据id删除我的消息出错" + e);
			return Response.getError("请求失败");
		}
	}

	@RequestMapping(value = "batchDelete", method = RequestMethod.POST)
	public Response batchDelete(@RequestBody MyOrderReq myOrderReq) {
		try {
			int i = messageReceiverService.batchDelete(myOrderReq);
			if (i == 1) {
				return Response.buildSuccess("", "删除成功");
			} else {
				return Response.buildMisSuccess();
			}
		} catch (Exception e) {
			logger.debug("根据ids[]批量删除我的消息出错" + e);
			return Response.getError("请求失败");
		}
	}

	@RequestMapping(value = "deleteAll", method = RequestMethod.POST)
	public Response deleteAll(@RequestBody MyOrderReq myOrderReq) {
		try {
			messageReceiverService.deleteAll(myOrderReq);
			return Response.buildSuccess("", "删除成功");
		} catch (Exception e) {
			return Response.getError("请求失败");
		}
	}

	@RequestMapping(value = "batchRead", method = RequestMethod.POST)
	public Response batchRead(@RequestBody MyOrderReq myOrderReq) {
		try {
			messageReceiverService.batchRead(myOrderReq);
			return Response.buildSuccess("", "已读设置成功");
		} catch (Exception e) {
			logger.debug("根据ids[]批量设置我的消息已读 出错" + e);
			return Response.getError("请求失败");
		}
	}

	@RequestMapping(value = "getServerDynamic", method = RequestMethod.POST)
	public Response getServerDynamic(@RequestBody MyOrderReq myOrderReq) {
		List<Map<String, Object>> ts = messageReceiverService.getServerDynamic(myOrderReq);
		Response rs = Response.buildSuccess(ts, "");
		return rs;
	}
}
