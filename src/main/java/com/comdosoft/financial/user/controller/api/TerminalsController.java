package com.comdosoft.financial.user.controller.api;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.comdosoft.financial.user.domain.Response;
import com.comdosoft.financial.user.domain.zhangfu.Merchant;
import com.comdosoft.financial.user.domain.zhangfu.Terminal;
import com.comdosoft.financial.user.service.TerminalsService;
import com.comdosoft.financial.user.utils.SysUtils;
import com.comdosoft.financial.user.utils.page.PageRequest;

/**
 * 
 * 终端管理<br>
 * <功能描述>
 *
 * @author xfh 2015年2月7日
 *
 */
@RestController
@RequestMapping(value = "/api/terminal")
public class TerminalsController {
	 private static final Logger logger = LoggerFactory.getLogger(WebMessageController.class);
	
	@Resource
	private TerminalsService terminalsService;

	@Value("${passPath}")
	private String passPath;

	/**
	 * 根据用户ID获得终端列表
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "getApplyList/{customersId}/{indexPage}/{pageNum}", method = RequestMethod.GET)
	public Response getApplyList(
			@PathVariable("customersId") Integer customersId,
			@PathVariable("indexPage") Integer page,
			@PathVariable("pageNum") Integer pageNum) {
		try {
			PageRequest PageRequest = new PageRequest(page, pageNum);
			int offSetPage = PageRequest.getOffset();
			return Response.getSuccess(terminalsService.getTerminalList(
					customersId, offSetPage, pageNum));
		} catch (Exception e) {
			return Response.getError("请求失败！");
		}
	}

	/**
	 * 进入终端详情
	 * 
	 * @param id
	 */
	@RequestMapping(value = "getApplyDetails/{terminalsId}", method = RequestMethod.GET)
	public Response getApplyDetails(
			@PathVariable("terminalsId") Integer terminalsId) {
		try {
			Map<Object, Object> map = new HashMap<Object, Object>();
			// 获得终端详情
			map.put("applyDetails",
					terminalsService.getApplyDetails(terminalsId));
			// 终端交易类型
			map.put("rates", terminalsService.getRate(terminalsId));
			// 追踪记录
			map.put("trackRecord", terminalsService.getTrackRecord(terminalsId));
			// 开通详情
			map.put("openingDetails",
					terminalsService.getOpeningDetails(terminalsId));
			return Response.getSuccess(map);
		} catch (Exception e) {
			return Response.getError("请求失败！");
		}
	}

	/**
	 * 收单通道
	 */
	@RequestMapping(value = "getFactories", method = RequestMethod.GET)
	public Response getFactories() {
		try {
			return Response.getSuccess(terminalsService.getChannels());
		} catch (Exception e) {
			return Response.getError("请求失败！");
		}
	}

	/**
	 * 添加终端
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "addTerminal", method = RequestMethod.POST)
	public Response addTerminal(@RequestBody Map<String, String> map) {
		try {
			Response response = new Response();
			Merchant merchants = new Merchant();
			// 判断该终端号是否存在
			if (terminalsService.isExistence(map.get("serialNum")) > 0) {
				response.setMessage("终端号已存在！");
				return response;
			} else if (terminalsService.isMerchantName(map.get("title")) > 0) {
				response.setMessage("商户名已存在！");
				return response;
			} else {
				merchants.setTitle(map.get("title"));
				merchants.setCustomerId(Integer.parseInt(map.get("customerId")));
				// 添加商户
				terminalsService.addMerchants(merchants);
				// 添加终端
				map.put("merchantId", merchants.getId().toString());
				map.put("status", String.valueOf(Terminal.TerminalTYPEID_3));
				map.put("isReturnCsDepots", String.valueOf(Terminal.IS_RETURN_CS_DEPOTS_NO));
				map.put("type", String.valueOf(Terminal.SYSTYPE));
				terminalsService.addTerminal(map);
				return Response.getSuccess("添加成功！");
			}
		} catch (Exception e) {
			  logger.debug("添加终端 end"+e);
			return Response.getError("请求失败");
		}

	}

	/**
	 * 找回POS机密码
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "Encryption/{terminalid}", method = RequestMethod.GET)
	public Response Encryption(@PathVariable("terminalid") Integer terminalid) {
		try {
			String pass = SysUtils.Decrypt(
					terminalsService.findPassword(terminalid), passPath);
			return Response.getSuccess(pass);
		} catch (Exception e) {
			return Response.getError("请求失败!");
		}
	}

	/**
	 * 视频认证
	 */
	@RequestMapping(value = "videoAuthentication", method = RequestMethod.GET)
	public Response videoAuthentication() {
		try {
			return Response.getSuccess("认证成功！");
		} catch (Exception e) {
			return Response.getError("认证失败！");
		}
	}

	/**
	 * 同步
	 */
	@RequestMapping(value = "synchronous", method = RequestMethod.GET)
	public Response Synchronous() {
		try {
			return Response.getSuccess("同步成功！");
		} catch (Exception e) {
			return Response.getError("同步失败！");
		}
	}

	/**
	 * author jwb 查询终端开通情况
	 * 
	 * @param paramMap
	 * @return
	 */
	@RequestMapping(value = "openStatus", method = RequestMethod.POST)
	public Response openStatus(@RequestBody Map<String, Object> paramMap) {
		try {
			return Response.getSuccess(terminalsService.openStatus(paramMap));
		} catch (Exception e) {
			return Response.getError("请求失败！");
		}
	}
}
