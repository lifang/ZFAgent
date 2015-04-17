package com.comdosoft.financial.user.controller.api;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.comdosoft.financial.user.domain.Response;
import com.comdosoft.financial.user.domain.zhangfu.Merchant;
import com.comdosoft.financial.user.domain.zhangfu.OpeningApplie;
import com.comdosoft.financial.user.service.CommentService;
import com.comdosoft.financial.user.service.OpeningApplyService;
import com.comdosoft.financial.user.service.TerminalsService;
import com.comdosoft.financial.user.utils.page.PageRequest;

/**
 * 
 * 开通申请<br>
 * <功能描述>
 *
 * @author xfh 2015年2月11日
 *
 */
@RestController
@RequestMapping(value = "/api/apply")
public class OpeningApplyController {
 
	private static final Logger logger = LoggerFactory.getLogger(TerminalsController.class);
	
	@Resource
	private OpeningApplyService openingApplyService;
	
	@Resource
	private TerminalsService terminalsService;
	
	@Autowired
	private CommentService commentService ;
	
	@Value("${uploadPictureTempsPath}")
    private String uploadPictureTempsPath;

	/**
	 * 根据代理商ID获得开通申请列表
	 * 
	 * @param page
	 * @param rows
	 * @param customerId
	 * @return
	 */
	@RequestMapping(value = "getApplyList", method = RequestMethod.POST)
	public Response getApplyList(@RequestBody Map<String, Object> map) {
		try {
			PageRequest PageRequest = new PageRequest((Integer)map.get("page"),
					(Integer)map.get("rows"));
			
			int offSetPage = PageRequest.getOffset();
			
			Map<Object, Object> resultMap = new HashMap<Object, Object>();
			resultMap.put("applyList", openingApplyService.getApplyList(
					(Integer)map.get("agentId"),
					offSetPage, (Integer)map.get("rows")));
			resultMap.put("total", openingApplyService.getApplyListSize(
					(Integer)map.get("agentId")));
			return Response.getSuccess(resultMap);
		} catch (Exception e) {
			logger.error("根据用户ID获得开通申请列表异常！",e);
			return Response.getError("请求失败！");
		}
	}
	
	/**
	 * 根据终端号模糊查询相关终端
	 * 
	 * @param page
	 * @param rows
	 * @param customerId
	 * @param serialNum
	 * @return
	 */
	@RequestMapping(value = "searchApplyList", method = RequestMethod.POST)
	public Response searchApplyList(@RequestBody Map<String, Object> map) {
		try {
			PageRequest PageRequest = new PageRequest((Integer)map.get("page"),
					(Integer)map.get("rows"));

			int offSetPage = PageRequest.getOffset();
			Map<Object, Object> resultMap = new HashMap<Object, Object>();
			resultMap.put("applyList", openingApplyService.searchApplyList(
					(Integer)map.get("agentId"),
					offSetPage, (Integer)map.get("rows"),
					(String)map.get("serialNum")));
			resultMap.put("total", openingApplyService.searchApplyListSize(
					(Integer)map.get("agentId"),
					(String)map.get("serialNum")));
			return Response.getSuccess(resultMap);
		} catch (Exception e) {
			logger.error("根据终端号获得开通申请列表异常！",e);
			return Response.getError("请求失败！");
		}
	}

	/**
	 * 进入申请开通
	 * 
	 * @param id
	 */
	@RequestMapping(value = "getApplyDetails", method = RequestMethod.POST)
	public Response getApplyDetails(@RequestBody Map<String, Object> maps) {
		try {
			Response re = isopen((Integer)maps.get("terminalsId"));
			if(re.getCode() == 1){
				Map<Object, Object> map = new HashMap<Object, Object>();
				// 获得终端详情
				map.put("applyDetails",
						openingApplyService.getApplyDetails((Integer)maps.get("terminalsId")));
				// 数据回显(针对重新开通申请)
				map.put("applyFor", openingApplyService.ReApplyFor((Integer)maps.get("terminalsId")));
				// 材料名称
				map.put("materialName",
						openingApplyService.getMaterialName((Integer)maps.get("terminalsId"),
								(Integer)maps.get("status")));
				// 获得已有申请开通基本信息
							map.put("openingInfos",
									openingApplyService.getOppinfo((Integer)maps.get("terminalsId")));
				return Response.getSuccess(map);
			}else {
				return re;
			}
		} catch (Exception e) {
			logger.error("进入申请开通异常！",e);
			return Response.getError("请求失败！");
		}
	}
	
	/**
	 * 判断终端是否绑定
	 * 
	 * @param id
	 * @return
	 */
	public Response isopen(int id) {
		try {
			int count = openingApplyService.isopen(id);
			if(count>0){
				return Response.getSuccess("可以开通！");
			}
			if(count == 0){
				return Response.getError("终端尚未绑定不能开通！");
			}
		} catch (Exception e) {
			logger.error("判断终端是否绑定失败！",e);
			return Response.getError("请求失败！");
		}
		return null;
	}
	
	/**
	 * 选择已有商户分页显示
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "getMerchants", method = RequestMethod.POST)
	public Response getMerchants(@RequestBody Map<String, Object> map) {
		try {
			PageRequest PageRequest = new PageRequest((Integer)map.get("page"),
					(Integer)map.get("rows"));
			int offSetPage = PageRequest.getOffset();
			Map<Object,Object> resultMap = new HashMap<Object, Object>();
			resultMap.put("merchaneList", openingApplyService.getMerchants(
					(Integer)map.get("terminalId"),//代理商用户id
					offSetPage,
					(Integer)map.get("rows"),
					(String)map.get("title")));
			resultMap.put("total", openingApplyService.getMerchantSize(
					(Integer)map.get("terminalId"),//代理商用户id
					(String)map.get("title")));
			return Response.getSuccess(resultMap);
		} catch (Exception e) {
			logger.error("根据商户id获得商户详细信息异常！",e);
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
			Map<Object, Object> maps = openingApplyService.getMerchant((Integer)map.get("merchantId"));
			return Response.getSuccess(maps);
		} catch (Exception e) {
			logger.error("根据商户id获得商户详细信息异常！",e);
			return Response.getError("请求失败！");
		}
	}

	/**
	 * 获得所有通道
	 */
	@RequestMapping(value = "getChannels", method = RequestMethod.POST)
	public Response getChannels() {
		try {
			//支付通道和周期列表
			List<Map<Object, Object>> list = openingApplyService.getChannels();
			 for(Map<Object, Object> m:list){
				 List<Map<Object, Object>> listT = openingApplyService.channelsT(Integer.parseInt(m.get("id").toString()));
				 if(listT == null){
					 m.put("billings","");
				 }else if(listT != null){
					 m.put("billings",listT);
				 }
			 }
			return Response.getSuccess(list);
		} catch (Exception e) {
			logger.error("获得所有通道异常！",e);
			return Response.getError("请求失败！");
		}
	}

	/**
	 * 从第三方接口获得银行
	 */
	@RequestMapping(value = "chooseBank", method = RequestMethod.POST)
	public Response ChooseBank(@RequestBody Map<String, Object> map) {
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
	 * 对公对私材料名称(1 对公， 2对私)
	 * 
	 * @param id
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "getMaterialName", method = RequestMethod.POST)
	public Response getMaterialName(@RequestBody Map<String, Object> map) {
		try {
			return Response.getSuccess(openingApplyService.getMaterialName(
					(Integer)map.get("terminalId"),
					(Integer)map.get("status")));
		} catch (Exception e) {
			logger.error("对公对私材料名称获取异常！",e);
			return Response.getError("请求失败！");
		}
	}

	/**
	 * 添加申请信息
	 * 
	 * @param paramMap
	 */
	@RequestMapping(value = "addOpeningApply", method = RequestMethod.POST)
	@ResponseBody
	public Response addOpeningApply(@RequestBody Map<Object, Object> applyMap) {
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
			List<Map<String, Object>> list = (List<Map<String, Object>>) applyMap.get("paramMap");
		int y = 0;
		for (Map<String, Object> map : list) {
			if (y == 0) {
				terminalId = (Integer)map.get("terminalId");
				//获得申请基本资料
				openingApplie.setTerminalId((Integer) map
						.get("terminalId"));
				openingApplie.setApplyCustomerId((Integer) map
						.get("applyCustomerId"));
				openingApplie.setStatus((Integer) map
						.get("status"));
				openingApplie.setTypes((Integer) map
						.get("publicPrivateStatus"));
				/*openingApplie.setMerchantId((Integer) map
						.get("merchantId"));*/
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
				//判断该商户是否存在
				Map<Object, Object> countMap =  openingApplyService.getMerchantsIsNo((String) map.get("merchantName"),(String) map.get("phone"));
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
					merchant.setCustomerId(openingApplyService.isopenMessage(terminalId));//终端绑定用户id
					openingApplyService.addMerchan(merchant);
					if(merchant.getId() != null){
						openingApplie.setMerchantId(merchant.getId());
					}else{
						return Response.getError("申请失败！");
					}
				}else if(countMap !=null){
					openingApplie.setMerchantId((Integer)countMap.get("id"));
				}
				//判断该申请是否为从新申请
				if(openingApplyService.judgeOpen(terminalId) != 0){
					openingAppliesId = String.valueOf(openingApplyService
							.getApplyesId(terminalId));
					// 删除旧数据
					openingApplyService.deleteOpeningInfos(Integer
							.valueOf(openingAppliesId));
					//修改申请表中的基本资料
					openingApplie.setId(Integer.parseInt(openingAppliesId));
					openingApplyService.updateApply(openingApplie);
				} else {
					openingApplyService.addOpeningApply(openingApplie);
					openingAppliesId = String
							.valueOf(openingApplie.getId());
				}
			} else {
					key = (String) map.get("key");
					value =  map.get("value");
					types = (Integer) map.get("types");
					openingRequirementId = (Integer) map.get("openingRequirementId");
					targetId =(Integer) map.get("targetId");
				openingApplyService.addApply(key, value,types, openingAppliesId,openingRequirementId,targetId);
			}
			y++;
		}
		return Response.getSuccess("添加成功！");
		} catch (Exception e) {
			logger.error("添加申请信息异常！",e);
			return Response.getError("请求失败！");
		}
	}
	
	/**
	 * 进入终端详情
	 * 
	 * @param id
	 */
	@RequestMapping(value = "getTernianlDetails", method = RequestMethod.POST)
	public Response getTernianlDetails(@RequestBody Map<String, Object> maps) {
		try {
			Map<Object, Object> map = new HashMap<Object, Object>();
			// 获得终端详情
			map.put("applyDetails",
					terminalsService.getApplyDetails((Integer)maps.get("terminalsId")));
			// 终端交易类型
			map.put("rates", terminalsService.getRate((Integer)maps.get("terminalsId")));
			// 追踪记录
			map.put("trackRecord", terminalsService.getTrackRecord((Integer)maps.get("terminalsId")));
			// 开通详情
			map.put("openingDetails",
					terminalsService.getOpeningDetails((Integer)maps.get("terminalsId")));
			// 获得已有申请开通基本信息
			map.put("openingInfos",
					openingApplyService.getOppinfo((Integer)maps.get("terminalsId")));
			return Response.getSuccess(map);
		} catch (Exception e) {
			logger.error("进入终端详情失败！", e);
			return Response.getError("请求失败！");
		}
	}
	
	/**
     * 上传开通图片文件
     * 
     * @param request
     * @param response
     * @param id
     */
    @RequestMapping(value = "upload/tempOpenImg/{id}", method = RequestMethod.POST)
    public Response tempOpenImg(@PathVariable("id") int id,@RequestParam(value="img") MultipartFile updatefile, HttpServletRequest request) {
        try {
        	return Response.getSuccess(commentService.saveTmpImage(uploadPictureTempsPath+id+"/opengImg/",updatefile, request));
        } catch (IOException e) {
        	return Response.getError("请求失败！");
        }
    }
}
