package com.comdosoft.financial.user.controller.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.comdosoft.financial.user.domain.Response;
import com.comdosoft.financial.user.domain.zhangfu.Merchant;
import com.comdosoft.financial.user.domain.zhangfu.OpeningApplie;
import com.comdosoft.financial.user.domain.zhangfu.Terminal;
import com.comdosoft.financial.user.service.OpeningApplyService;
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

	/**
	 * 根据用户ID获得开通申请列表
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "getApplyList", method = RequestMethod.POST)
	public Response getApplyList(@RequestBody Map<String, Object> map) {
		try {
			PageRequest PageRequest = new PageRequest(Integer.parseInt((String)map.get("page")),
					Integer.parseInt((String)map.get("pageNum")));

			int offSetPage = PageRequest.getOffset();
			return Response.getSuccess(openingApplyService.getApplyList(
					Integer.parseInt((String)map.get("id")),
					offSetPage, Integer.parseInt((String)map.get("pageNum"))));
		} catch (Exception e) {
			logger.error("根据用户ID获得开通申请列表异常！",e);
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
			Map<Object, Object> map = new HashMap<Object, Object>();
			// 获得终端详情
			map.put("applyDetails",
					openingApplyService.getApplyDetails(Integer.parseInt((String)maps.get("terminalsId"))));
			// 获得所有商户
			map.put("merchants", openingApplyService.getMerchants(Integer.parseInt((String)maps.get("customerId"))));
			// 数据回显(针对重新开通申请)
			map.put("applyFor", openingApplyService.ReApplyFor(Integer.parseInt((String)maps.get("terminalsId"))));
			// 材料名称
			map.put("materialName",
					openingApplyService.getMaterialName(Integer.parseInt((String)maps.get("terminalsId")),
							Integer.parseInt((String)maps.get("status"))));
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
			Merchant merchant = new Merchant();
			merchant = openingApplyService.getMerchant(Integer.parseInt((String)map.get("merchantId")));
			return Response.getSuccess(merchant);
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
			return Response.getSuccess(openingApplyService.getChannels());
		} catch (Exception e) {
			logger.error("获得所有通道异常！",e);
			return Response.getError("请求失败！");
		}
	}

	/**
	 * 从第三方接口获得银行
	 */
	@RequestMapping(value = "ChooseBank", method = RequestMethod.POST)
	public Response ChooseBank() {
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("code1", "中国农业银行");
			map.put("code2", "中国工商银行");
			return Response.getSuccess(map);
		} catch (Exception e) {
			logger.error("从第三方接口获得银行异常！",e);
			return Response.getError("请求失败！");
		}
	}

	/**
	 * 对公对私材料名称
	 * 
	 * @param id
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "getMaterialName", method = RequestMethod.POST)
	public Response getMaterialName(@RequestBody Map<String, Object> map) {
		try {
			return Response.getSuccess(openingApplyService.getMaterialName(
					Integer.parseInt((String)map.get("terminalId")),
					Integer.parseInt((String)map.get("status"))));
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
	public Response addOpeningApply(
			@RequestBody List<Map<String, Object>> paramMap) {
		try {
			OpeningApplie openingApplie = new OpeningApplie();
			//开通申请状态（申请、重新申请）
			Integer openStatus = null;
			//对公对私状态
			Integer publicPrivateStatus = null;
			String openingAppliesId = null;
			Integer terminalId = null;
			String key = null;
			String value = null;
			Integer types = null;
			int i = 0;
			int y = 0;
			for (Map<String, Object> map : paramMap) {
				Set<String> keys = map.keySet();
				if (y == 0) {
					openStatus = (Integer) map.get("openStatus");
					terminalId = (Integer) map.get("terminalId");
					publicPrivateStatus =(Integer)map.get("publicPrivateStatus");
					if (openStatus == Terminal.TerminalTYPEID_2) {
						openingAppliesId = String.valueOf(openingApplyService
								.getApplyesId(terminalId));
						// 删除旧数据
						openingApplyService.deleteOpeningInfos(Integer
								.valueOf(openingAppliesId));
						openingApplyService.updateOpeningApplyStatus(Integer.parseInt(openingAppliesId));
					} else {
						openingApplie.setTerminalId((Integer) map
								.get("terminalId"));
						openingApplie.setApplyCustomerId((Integer) map
								.get("applyCustomerId"));
						openingApplie.setTypes(publicPrivateStatus);
						openingApplie.setStatus(OpeningApplie.STATUS_1);
						openingApplyService.addOpeningApply(openingApplie);
						openingAppliesId = String
								.valueOf(openingApplie.getId());
					}
				} else {
					for (String str : keys) {
						if (i == 0)
							key = (String) map.get(str);
						if (i == 1)
							value = (String) map.get(str);
						if (i == 2)
							types = (Integer) map.get(str);
						i++;
					}
					openingApplyService.addApply(key, value,types, openingAppliesId);
					i = 0;
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
	 * 材料图片上传
	 * 
	 * @param filePath
	 * @return
	 */
	@RequestMapping(value = "uploadFile", method = RequestMethod.POST)
	@ResponseBody
	public Response uploadFile(@RequestBody Map<String, String> map,
			HttpServletRequest request) {
		try {
			String filePath = null;
			Set<String> keys = map.keySet();
			for (String str : keys) {
				filePath = map.get(str);
			}
			String fileOutPath = request.getServletContext().getRealPath("/")
					+ "WEB-INF/temp/";
			int byteread = 0;// 读取的位数
			FileInputStream in = null;
			FileOutputStream out = null;
			File fileIn = new File(filePath);
			File fileOut = new File(fileOutPath);

			if (!fileOut.exists()) {
				fileOut.mkdirs();
			}
			fileOut = new File(fileOutPath
					+ filePath.substring(filePath.lastIndexOf("\\") + 1));
			if (!fileIn.exists()) {
				return Response.getSuccess("该路径图片不存在！");
			}
			try {
				in = new FileInputStream(fileIn);
				out = new FileOutputStream(fileOut);
				byte[] buffer = new byte[1024];
				while ((byteread = in.read(buffer)) != -1) {
					// 将读取的字节写入输出流
					out.write(buffer, 0, byteread);
				}
				return Response.getSuccess(fileOut);
			} catch (Exception e) {
				return Response.getSuccess("上传失败！");
			} finally {
				try {
					if (out != null) {
						out.close();
					}
					if (in != null) {
						in.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			logger.error("材料图片上传异常！",e);
			return Response.getError("请求失败！");
		}
	}

	/**
	 * 视频认证
	 */
	@RequestMapping(value = "videoAuthentication", method = RequestMethod.POST)
	public Response videoAuthentication() {
		try {
			return Response.getSuccess("视频认证");
		} catch (Exception e) {
			logger.error("视频认证异常！",e);
			return Response.getError("视频认证异常");
		}
	}
}
