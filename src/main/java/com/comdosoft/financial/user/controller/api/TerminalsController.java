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
import com.comdosoft.financial.user.domain.zhangfu.CsAgent;
import com.comdosoft.financial.user.service.TerminalsService;
import com.comdosoft.financial.user.utils.page.PageRequest;

/**
 * 
 * 终端管理<br>
 * <功能描述>
 *
 * @author xfh 2015年2月10日
 *
 */
@RestController
@RequestMapping(value = "/api/terminal")
public class TerminalsController {
	 private static final Logger logger = LoggerFactory.getLogger(TerminalsController.class);
	
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
			Integer status = 0; 
			PageRequest PageRequest = new PageRequest(page, pageNum);
			int offSetPage = PageRequest.getOffset();
			return Response.getSuccess(terminalsService.getTerminalList(
					customersId, offSetPage, pageNum,status));
		} catch (Exception e) {
			logger.error("根据用户ID获得终端列表异常！", e);
			return Response.getError("请求失败！");
		}
	}
	
	/**
	 * 根据状态选择查询
	 * @param status
	 * @param customersId
	 * @param page
	 * @param pageNum
	 * @return
	 */
	@RequestMapping(value="getTerminalList/{customersId}/{indexPage}/{pageNum}/{status}",method=RequestMethod.GET)
	public Response getTerminalList(@PathVariable("status") Integer status,
			@PathVariable("customersId") Integer customersId,
			@PathVariable("indexPage") Integer page,
			@PathVariable("pageNum") Integer pageNum){
		try {
			PageRequest PageRequest = new PageRequest(page, pageNum);
			int offSetPage = PageRequest.getOffset();
			return Response.getSuccess(terminalsService.getTerminalList(
					customersId, offSetPage, pageNum,status));
		} catch (Exception e) {
			logger.error("根据状态选择查询异常！", e);
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
			logger.error("进入终端详情失败！", e);
			return Response.getError("请求失败！");
		}
	}

	/**
	 * 获得代理商下面的用户
	 * @param customerId
	 * @return
	 */
	@RequestMapping(value="getMerchants/{customerId}",method=RequestMethod.GET)
	public Response getMerchants(@PathVariable("customerId") Integer customerId){
		try {
			return Response.getSuccess(terminalsService.getMerchants(customerId));
		} catch (Exception e) {
			logger.error("获得代理商下面的用户失败！", e);
			return Response.getError("请求失败！");
		}
	}
	
	/**
	 * 为用户绑定
	 * @param map
	 * @return
	 */
	@RequestMapping(value="BindingTerminals",method=RequestMethod.POST)
	public Response BindingTerminals(@RequestBody Map<String, String> map){
		try {
			if(terminalsService.getTerminalsNum(map.get("terminalsNum"))==null){
				return Response.getError("终端号不存在！");
			}else{
				if(terminalsService.numIsBinding(map.get("terminalsNum"))>0){
					return Response.getError("该终端已绑定！");
				}else{
					if(terminalsService.merchantsIsBinding(Integer.parseInt(map.get("erchantsId")))!=null){
						return Response.getError("该商户已绑定终端！");
					}else{
						String terId =(String)terminalsService.getTerminalsNum(map.get("terminalsNum"));
						map.put("terchantsId", terId);
						terminalsService.Binding(map);
						return Response.getSuccess("绑定成功！");
					}
				}
			}
		} catch (Exception e) {
			logger.error("为用户绑定失败！", e);
			return Response.getError("请求失败！");
		}
	}
	
	/**
	 * 选择终端号
	 * @param customerId
	 * @return
	 */
	//@RequestMapping(value="getTerminal/{customerId}",method=RequestMethod.GET)
	/*public Response getTerminal(@PathVariable("customerId") Integer customerId){
		try{
			return Response.getSuccess(terminalsService.getTerminal(customerId));
		}catch(Exception e){
			return Response.getError("请求失败！");
		}
	}*/
	
	/**
	 * 筛选终端
	 * @param map
	 * @return
	 */
	@RequestMapping(value="screeningTerminalNum",method=RequestMethod.POST)
	public Response screeningTerminalNum(@RequestBody Map<Object, Object> map){
		try{
			return Response.getSuccess(terminalsService.screeningTerminalNum(map));
		}catch(Exception e){
			logger.error("筛选终端失败！", e);
			return Response.getError("请求失败！");
		}
	}
	
	/**
	 * 收件人信息
	 * @param customerId
	 * @return
	 */
	@RequestMapping(value="getAddressee/{customerId}",method=RequestMethod.GET)
	public Response getAddressee(@PathVariable("customerId")Integer customerId){
		try{
			return Response.getSuccess(terminalsService.getAddressee(customerId));
		}catch(Exception e){
			logger.error("收件人信息异常！", e);
			return Response.getError("请求失败！");
		}
	}
	
	/**
	 * 提交申请售后
	 * @param customerId
	 * @return
	 */
	@RequestMapping(value="submitAgent",method=RequestMethod.POST)
	public Response submitAgent(@RequestBody CsAgent csAgent){
		try{
			csAgent.setStatus(CsAgent.STSTUS_1);
			csAgent.setApplyNum(String.valueOf(System.currentTimeMillis())+csAgent.getCustomerId());
			terminalsService.submitAgent(csAgent);
			return Response.getSuccess("提交申请成功！");
		}catch(Exception e){
			logger.error("提交申请售后失败！", e);
			return Response.getError("请求失败！");
		}
	}
	
	/**
	 * POS机选择
	 * @param customerId
	 * @return
	 */
	@RequestMapping(value="screeningPosName/{customerId}",method=RequestMethod.GET)
	public Response screeningPosName(@PathVariable("customerId") Integer customerId){
		try{
			return Response.getSuccess(terminalsService.screeningPosName(customerId));
		}catch(Exception e){
			logger.error("POS机选择失败！", e);
			return Response.getError("请求失败！");
		}
	}
	
	/**
	 * 所有通道列表
	 * @return
	 */
	@RequestMapping(value="getChannels",method=RequestMethod.GET)
	public Response getChannels(){
		try{
			return Response.getSuccess(terminalsService.getChannels());
		}catch(Exception e){
			logger.error("获取通道列表异常！", e);
			return Response.getError("请求失败！");
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
			logger.error("同步异常！", e);
			return Response.getError("同步失败！");
		}
	}

}
