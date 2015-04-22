package com.comdosoft.financial.user.controller.api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.comdosoft.financial.user.domain.Response;
import com.comdosoft.financial.user.domain.zhangfu.Merchant;
import com.comdosoft.financial.user.domain.zhangfu.OpeningApplie;
import com.comdosoft.financial.user.service.OpeningApplyWebService;
import com.comdosoft.financial.user.service.TerminalsService;

/**
 * 
 * 开通申请<br>
 * <功能描述>
 *
 * @author xfh 2015年2月11日
 *
 */
@RestController
@RequestMapping(value = "/api/applyWeb")
public class OpeningApplyWebController {
 
	private static final Logger logger = LoggerFactory.getLogger(TerminalsController.class);
	
	@Resource
	private OpeningApplyWebService openingApplyWebService;
	
	@Resource
	private TerminalsService terminalsService;
	
	@Value("${filePath}")
	private String filePath;
	
	/**
	 * 进入申请开通
	 * 
	 * @param id
	 */
	@RequestMapping(value = "getApplyDetails", method = RequestMethod.POST)
	public Response getApplyDetails(@RequestBody Map<Object, Object> maps) {
		try {
			Map<Object, Object> map = new HashMap<Object, Object>();
			// 获得终端详情
			Map<String, Object> mp = new HashMap<String, Object>();
			mp = openingApplyWebService.getApplyDetails((Integer)maps.get("terminalId"));
			map.put("applyDetails",mp);
			// 获得所有商户
			map.put("merchants", openingApplyWebService.getMerchants((Integer)maps.get("terminalId")));
			// 获得材料等级
			map.put("materialLevel", openingApplyWebService.getMaterialLevel((Integer)maps.get("terminalId")));
			//支付通道和周期列表
			List<Map<Object, Object>> list = openingApplyWebService.getChannels();
			List<Map<Object, Object>> li = new ArrayList<Map<Object,Object>>();
			 for(Map<Object, Object> m:list){
				 if((Integer)m.get("id") == mp.get("channelId")){
					 li.add(m);
				 }
			 }
			 for(Map<Object, Object> m:li){
					 m.put("billings", openingApplyWebService.channelsT(Integer.parseInt(m.get("id").toString())));
			 }
			map.put("channels", li);
			// 数据回显(针对重新开通申请)
			map.put("applyFor", openingApplyWebService.ReApplyFor((Integer)maps.get("terminalId")));
			//获得已有申请开通基本信息
						map.put("openingInfos",
								openingApplyWebService.getOppinfo((Integer)maps.get("terminalId")));
			//城市级联
			map.put("CitieChen", openingApplyWebService.getCities());
			return Response.getSuccess(map);
		} catch (Exception e) {
			logger.error("进入申请开通异常！",e);
			return Response.getError("请求失败！");
		}
	}
	
	/**
	 * 根据商户id获得商户详细信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "getMerchant", method = RequestMethod.POST)
	public Response getMerchant(@RequestBody Map<String, Object> map) {
		try {
			Map<Object, Object> maps = openingApplyWebService.getMerchant((Integer)map.get("merchantId"));
			return Response.getSuccess(maps);
		} catch (Exception e) {
			logger.error("根据商户id获得商户详细信息异常！",e);
			return Response.getError("请求失败！");
		}
	}
	/**
	 * 判断终端是否绑定
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "isopen", method = RequestMethod.POST)
	public Response isopen(@RequestBody Map<String, Object> map) {
		try {
			int count = openingApplyWebService.isopen((Integer)map.get("id"));
			if(count>0){
				return Response.getSuccess("可以开通！");
			}
			if(count == 0){
				return Response.getError("终端尚未绑定用户不能开通！");
			}
		} catch (Exception e) {
			logger.error("判断终端是否绑定失败！",e);
			return Response.getError("请求失败！");
		}
		return null;
	}
	
	/**
	 * 对公对私材料名称(1 对公， 2对私)
	 * 
	 * @param id
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "getMaterialName", method = RequestMethod.POST)
	public Response getMaterialName(@RequestBody Map<String, Integer> map) {
		try {
			
			// 数据回显(重新开通申请)
			List<Map<String, String>> list = openingApplyWebService.ReApplyFor((Integer)map.get("terminalId"));
			List<Map<Object, Object>> listMap = openingApplyWebService.getMaterialName(
					map.get("terminalId")
					,map.get("status"));
			for(Map<Object, Object> mp:listMap){
				for(Map<String, String> mp1:list){
					if(mp.get("id").equals(mp1.get("target_id")) && mp.get("opening_requirements_id").equals(mp1.get("opening_requirement_id"))){
						mp.put("value", mp1.get("value"));
					}
				}
			}
			return Response.getSuccess(listMap);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getError("请求失败！");
		}
	}
	
	/**
	 * 从第三方接口获得银行
	 * 
	 * @return
	 */
	@RequestMapping(value = "chooseBank", method = RequestMethod.POST)
	public Response chooseBank(@RequestBody Map<String, Object> map) {
		try {
			List<Map<String, String>> list = new ArrayList<Map<String,String>>();
			Map<String, String> map1 = new HashMap<String, String>();
			map1.put("name", "中国农业银行");
			map1.put("code", "111111");
			Map<String, String> map2 = new HashMap<String, String>();
			map2.put("name", "中国工商银行");
			map2.put("code", "222222");
			list.add(map1);
			list.add(map2);
			return Response.getSuccess(list);
		} catch (Exception e) {
			logger.error("从第三方接口获得银行异常！",e);
			return Response.getError("请求失败！");
		}
	}
	
	/**
	 * 添加申请信息
	 * 
	 * @param paramMap
	 * @throws ParseException 
	 */
	@RequestMapping(value = "addOpeningApply", method = RequestMethod.POST)
	@ResponseBody
	public Response addOpeningApply(@RequestBody Map<Object, Object> applyMap){
		try {
			OpeningApplie openingApplie = new OpeningApplie();
			String openingAppliesId = null;
			Integer terminalId = null;
			String key = null;
			Object value = null;
			Integer types = null;
			Integer openingRequirementId = null;
			Integer targetId =null;
			@SuppressWarnings("unchecked")
			List<Map<Object, Object>> list = (List<Map<Object, Object>>) applyMap.get("arrsy");
		int y = 0;
		for (Map<Object, Object> map : list) {
			if (y == 0) {
				terminalId = (Integer)map.get("terminalId");
				//获得申请基本资料
				openingApplie.setTerminalId((Integer) map
						.get("terminalId"));
				openingApplie.setApplyCustomerId((Integer) map
						.get("applyCustomerId"));
				openingApplie.setTypes((Integer) map
						.get("publicPrivateStatus"));
				openingApplie.setMerchantName((String) map
						.get("merchantName"));
				openingApplie.setSex((Integer) map
						.get("sex"));
				openingApplie.setBirthday( new SimpleDateFormat("yyyy-MM-dd").parse((String) map.get("birthday")));
				openingApplie.setCardId((String) map
						.get("cardId"));
				openingApplie.setPhone((String) map
						.get("phone"));
				openingApplie.setEmail((String) map
						.get("email"));
				openingApplie.setCityId((Integer) map
						.get("cityId"));
				openingApplie.setName((String) map
						.get("name"));
				openingApplie.setPayChannelId((Integer) map
						.get("channel"));
				openingApplie.setBillingCydeId((Integer) map
						.get("billingId")); 
				openingApplie.setAccountBankNum((String) map
						.get("bankNum"));
				openingApplie.setAccountBankName((String) map
						.get("bankName"));
				openingApplie.setAccountBankCode((String) map
						.get("bankCode"));
				openingApplie.setTaxRegisteredNo((String) map
						.get("registeredNo"));
				openingApplie.setOrganizationCodeNo((String) map
						.get("organizationNo"));
				if((Integer) map.get("needPreliminaryVerify") == 0){
					openingApplie.setStatus(OpeningApplie.STATUS_5);
				}
				if((Integer) map.get("needPreliminaryVerify") == 1){
					openingApplie.setStatus(OpeningApplie.STATUS_1);
				}
				//判断该商户是否存在
				Map<Object, Object> countMap =  openingApplyWebService.getMerchantsIsNo((String) map.get("merchantName"),(String) map.get("phone"));
				if(countMap == null){
					//添加商户
					Merchant merchant = new Merchant();
					merchant.setLegalPersonName((String) map
							.get("name"));
					merchant.setLegalPersonCardId((String) map
							.get("cardId"));
					merchant.setTitle((String) map
							.get("merchantName"));
					merchant.setTaxRegisteredNo((String) map
							.get("registeredNo"));
					merchant.setOrganizationCodeNo((String) map
							.get("organizationNo"));
					merchant.setAccountBankNum((String) map
							.get("bankNum"));
					merchant.setPhone((String) map
							.get("phone"));
					merchant.setCityId((Integer)map.get("cityId"));
					//得到该终端绑定用户
					merchant.setCustomerId(openingApplyWebService.isopenMessage(terminalId));//终端绑定用户id
					openingApplyWebService.addMerchan(merchant);
					if(merchant.getId() != null){
						openingApplie.setMerchantId(merchant.getId());
					}else{
						return Response.getError("申请失败！");
					}
				}else if(countMap !=null){
					openingApplie.setMerchantId((Integer)countMap.get("id"));
				}
				//为终端表关联对应的商户id和通道周期ID 
				openingApplyWebService.updateterminal(openingApplie.getMerchantId(),terminalId,(Integer) map
						.get("billingId"));
				//判断该申请是否为从新申请
				if(openingApplyWebService.judgeOpen(terminalId) != 0){
					openingAppliesId = String.valueOf(openingApplyWebService
							.getApplyesId(terminalId));
					// 删除旧数据
					openingApplyWebService.deleteOpeningInfos(Integer
							.valueOf(openingAppliesId));
					//修改申请表中的基本资料
					openingApplie.setId(Integer.parseInt(openingAppliesId));
					openingApplyWebService.updateApply(openingApplie);
				} else {
					openingApplyWebService.addOpeningApply(openingApplie);
					openingAppliesId = String
							.valueOf(openingApplie.getId());
				}
			} else {
					key = (String) map.get("key");
					types = (Integer) map.get("types");
					if(types == 2){
						value =  map.get("value").toString().substring(filePath.length());
					}else{
						value = map.get("value");
					}
					openingRequirementId = (Integer) map.get("openingRequirementId");
					targetId =(Integer) map.get("targetId");
				openingApplyWebService.addApply(key, value,types, openingAppliesId,openingRequirementId,targetId);
			}
			y++;
		}
		return Response.getSuccess("添加成功！");
		} catch (Exception e) {
			logger.error("添加申请信息异常！",e);
			return Response.getError("请求失败！");
		}
	}
	
}
