package com.comdosoft.financial.user.controller.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.comdosoft.financial.user.domain.Response;
import com.comdosoft.financial.user.domain.zhangfu.Customer;
import com.comdosoft.financial.user.service.OpeningApplyService;
import com.comdosoft.financial.user.service.TerminalsWebService;
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
@RequestMapping(value = "/api/webTerminal")
public class TerminalsWebController {
	 private static final Logger logger = LoggerFactory.getLogger(TerminalsWebController.class);
	
	@Resource
	private TerminalsWebService terminalsWebService;
	
	@Resource
	private OpeningApplyService openingApplyService;

	@Value("${passPath}")
	private String passPath;
	
	/**
	 * 根据用户ID获得终端列表
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "getAgentApplyList", method = RequestMethod.POST)
	public Response getApplyList(@RequestBody Map<String, Object> map) {
		try {
			Map<Object, Object> maps = new HashMap<Object, Object>();
			PageRequest PageRequest = new PageRequest((Integer)map.get("page"),
					(Integer)map.get("rows"));
			int offSetPage = PageRequest.getOffset();
			
			maps.put("total", terminalsWebService.getTerminalListNums(
					((Integer)map.get("customersId")),
					offSetPage,
					(Integer)map.get("rows"),
					(Integer)map.get("frontStatus"),
					(String)map.get("serialNum")));
			//终端付款状态（2 已付   1未付  3已付定金）
			maps.put("frontPayStatus", terminalsWebService.getFrontPayStatus());
			//支付通道和周期列表
			/*List<Map<Object, Object>> list = terminalsService.getChannels();
			 for(Map<Object, Object> m:list){
				 m.put("billings", terminalsService.channelsT(Integer.parseInt(m.get("id").toString())));
			 }
			maps.put("channels",list);*/
			List<Map<Object, Object>> li = terminalsWebService.getTerminalList(
					((Integer)map.get("customersId")),
					offSetPage,
					(Integer)map.get("rows"),
					(Integer)map.get("frontStatus"),
					(String)map.get("serialNum"));
			maps.put("list", li == null?new ArrayList<Object>():li);
			return Response.getSuccess(maps);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getError("请求失败！");
		}
	}
	
	/**
	 * 搜索代理商相关所有用户
	 * 
	 * @param namemap
	 * @return
	 */
	@RequestMapping(value="searchUser",method=RequestMethod.POST)
	public Response searchUser(@RequestBody Map<Object, Object> namemap){
		try{
			//namemap.put("type", Customer.TYPE_CUSTOMER);
			return Response.getSuccess(terminalsWebService.searchUser(namemap));
		}catch(Exception e){
			e.printStackTrace();
			return Response.getError("系统异常");
		}
	}
	
	/**
	 * 为用户绑定
	 * @param map
	 * @return
	 */
	@RequestMapping(value="BindingTerminals",method=RequestMethod.POST)
	public Response BindingTerminals(@RequestBody Map<Object, Object> map){
		try {
			if(terminalsWebService.getTerminalsNum((String)map.get("terminalsNum"))==null){
				return Response.getError("终端号不存在！");
			}else{
				if(terminalsWebService.numIsBinding((String)map.get("terminalsNum"))==0){
					return Response.getError("该终端已绑定！");
				}else{
					/*if(terminalsWebService.merchantsIsBinding((Integer)map.get("merchantsId"))>0){
						return Response.getError("该商户已绑定终端！");
					}else{*/
						//Integer terId =(Integer) terminalsWebService.getTerminalsNum((String)map.get("terminalsNum"));
						//map.put("terchantsId", terId);
						terminalsWebService.Binding(map);
						return Response.getSuccess("绑定成功！");
					//}
				}
			}
		} catch (Exception e) {
			logger.error("为用户绑定失败！", e);
			return Response.getError("请求失败！");
		}
	}
	
	/**
	 * 新创建用户
	 * @param map
	 * @return
	 */
	@RequestMapping(value="addCustomer",method=RequestMethod.POST)
	public Response addCustomer(@RequestBody Map<Object, Object> map){
		try {
			if(terminalsWebService.findUname(map)>0){
				return Response.getError("用户已存在！");
			}else{
				//添加新用户
				Customer customer = new Customer();
				customer.setUsername((String)map.get("username"));
				customer.setName((String)map.get("name"));
				customer.setPassword((String)map.get("pass1"));
				customer.setCityId((Integer)map.get("cityid"));
				customer.setTypes(Customer.TYPE_CUSTOMER);
				customer.setStatus(Customer.STATUS_NORMAL);
				customer.setIntegral(0);
				terminalsWebService.addUser(customer);
				return Response.getSuccess(customer);
			}
		} catch (Exception e) {
			logger.error("为用户绑定失败！", e);
			return Response.getError("请求失败！");
		}
	}
	
	

	/**
	 * 根据用户ID获得终端列表
	 * 
	 * @param id
	 * @return
	 */
	/*@RequestMapping(value = "getApplyList", method = RequestMethod.POST)
	public Response getApplyList(@RequestBody Map<String, Object> map) {
		try {
			Integer status = 0; 
			PageRequest PageRequest = new PageRequest(
					(Integer)map.get("page"),
					(Integer)map.get("rows"));
			int offSetPage = PageRequest.getOffset();
			return Response.getSuccess(terminalsService.getTerminalList(
					(Integer)map.get("customersId"),
					offSetPage,
					(Integer)map.get("rows"),
					status));
		} catch (Exception e) {
			logger.error("根据用户ID获得终端列表异常！", e);
			return Response.getError("请求失败！");
		}
	}
	*/
	/**
	 * 根据状态选择查询
	 * @param status
	 * @param customersId
	 * @param page
	 * @param pageNum
	 * @return
	 */
	/*@RequestMapping(value="getTerminalList",method=RequestMethod.POST)
	public Response getTerminalList(@RequestBody Map<String, Object> map){
		try {
			PageRequest PageRequest = new PageRequest(
					(Integer)map.get("page"),
					(Integer)map.get("rows"));
			int offSetPage = PageRequest.getOffset();
			return Response.getSuccess(terminalsService.getTerminalList(
					(Integer)map.get("customersId"),
					offSetPage,
					(Integer)map.get("rows"),
					(Integer)map.get("status")));
		} catch (Exception e) {
			logger.error("根据状态选择查询异常！", e);
			return Response.getError("请求失败！");
		}
	}*/
	
	/**
	 * 根据终端号模糊查询相关终端
	 * 
	 * @param page
	 * @param rows
	 * @param customerId
	 * @param serialNum
	 * @return
	 */
	/*@RequestMapping(value = "searchApplyList", method = RequestMethod.POST)
	public Response searchApplyList(@RequestBody Map<String, Object> map) {
		try {
			PageRequest PageRequest = new PageRequest((Integer)map.get("page"),
					(Integer)map.get("rows"));

			int offSetPage = PageRequest.getOffset();
			return Response.getSuccess(openingApplyService.searchApplyList(
					(Integer)map.get("customerId"),
					offSetPage, (Integer)map.get("rows"),
					(String)map.get("serialNum")));
		} catch (Exception e) {
			logger.error("根据终端号获得开通申请列表异常！",e);
			return Response.getError("请求失败！");
		}
	}*/

	/**
	 * 进入终端详情
	 * 
	 * @param id
	 */
	@RequestMapping(value = "getApplyDetails", method = RequestMethod.POST)
	public Response getApplyDetails(@RequestBody Map<String, Object> maps) {
		try {
			Map<Object, Object> map = new HashMap<Object, Object>();
			// 获得终端详情
			map.put("applyDetails",
					terminalsWebService.getApplyDetails((Integer)maps.get("terminalsId")));
			// 终端交易类型
			map.put("rates", terminalsWebService.getRate((Integer)maps.get("terminalsId")));
			// 追踪记录
			map.put("trackRecord", terminalsWebService.getTrackRecord((Integer)maps.get("terminalsId")));
			// 开通详情
			map.put("openingDetails",
					terminalsWebService.getOpeningDetails((Integer)maps.get("terminalsId")));
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
	/*@RequestMapping(value="getMerchants",method=RequestMethod.POST)
	public Response getMerchants(@RequestBody Map<String, Object> map){
		try {
			return Response.getSuccess(terminalsService.getMerchants((Integer)map.get("customerId")));
		} catch (Exception e) {
			logger.error("获得代理商下面的用户失败！", e);
			return Response.getError("请求失败！");
		}
	}*/
	
	
	
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
	 * 筛选终端（pos机，通道，价格）
	 * @param map
	 * @return
	 */
	/*@RequestMapping(value="screeningTerminalNum",method=RequestMethod.POST)
	public Response screeningTerminalNum(@RequestBody Map<Object, Object> map){
		try{
			return Response.getSuccess(terminalsService.screeningTerminalNum(map));
		}catch(Exception e){
			logger.error("筛选终端失败！", e);
			return Response.getError("请求失败！");
		}
	}*/
	
	/**
	 * 批量终端号筛选终端
	 * @param map
	 * @return
	 */
	/*@RequestMapping(value="batchTerminalNum",method=RequestMethod.POST)
	public Response batchTerminalNum(@RequestBody Map<Object, Object> map){
		try{
			return Response.getSuccess(terminalsService.screeningTerminalNum(map));
		}catch(Exception e){
			logger.error("筛选终端失败！", e);
			return Response.getError("请求失败！");
		}
	}
	*/
	
	/**
	 * 收件人信息
	 * @param customerId
	 * @return
	 */
	/*@RequestMapping(value="getAddressee",method=RequestMethod.POST)
	public Response getAddressee(@RequestBody Map<String, Object> map){
		try{
			return Response.getSuccess(terminalsService.getAddressee((Integer)map.get("customerId")));
		}catch(Exception e){
			logger.error("收件人信息异常！", e);
			return Response.getError("请求失败！");
		}
	}*/
	
	/**
	 * 提交申请售后
	 * @param customerId
	 * @return
	 */
	/*@RequestMapping(value="submitAgent",method=RequestMethod.POST)
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
	}*/
	
	/**
	 * POS机选择
	 * @param customerId
	 * @return
	 */
	/*@RequestMapping(value="screeningPosName",method=RequestMethod.POST)
	public Response screeningPosName(@RequestBody Map<String, Object> map){
		try{
			return Response.getSuccess(terminalsService.screeningPosName((Integer)map.get("customerId")));
		}catch(Exception e){
			logger.error("POS机选择失败！", e);
			return Response.getError("请求失败！");
		}
	}*/
	
	/**
	 * 所有通道列表
	 * @return
	 */
	@RequestMapping(value="getChannels",method=RequestMethod.POST)
	public Response getChannels(){
		try{
			return Response.getSuccess(terminalsWebService.getChannels());
		}catch(Exception e){
			logger.error("获取通道列表异常！", e);
			return Response.getError("请求失败！");
		}
	}
	
	/**
	 * 同步
	 */
	@RequestMapping(value = "synchronous", method = RequestMethod.POST)
	public Response Synchronous() {
		try {
			return Response.getSuccess("同步成功！");
		} catch (Exception e) {
			logger.error("同步异常！", e);
			return Response.getError("同步失败！");
		}
	}

}
