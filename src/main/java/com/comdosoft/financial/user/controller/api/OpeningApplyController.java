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

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.comdosoft.financial.user.domain.Response;
import com.comdosoft.financial.user.domain.zhangfu.Merchant;
import com.comdosoft.financial.user.domain.zhangfu.OpeningApplie;
import com.comdosoft.financial.user.service.OpeningApplyService;
import com.comdosoft.financial.user.utils.page.PageRequest;

/**
 * 
 * 开通申请<br>
 * <功能描述>
 *
 * @author xfh 2015年2月5日
 *
 */
@RestController
@RequestMapping(value = "/api/apply")
public class OpeningApplyController {

	@Resource
	private OpeningApplyService openingApplyService;

	/**
	 * 根据用户ID获得开通申请列表
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "getApplyList/{id}/{indexPage}/{pageNum}", method = RequestMethod.GET)
	public Response getApplyList(@PathVariable("id") Integer id,
			@PathVariable("indexPage") Integer page,
			@PathVariable("pageNum") Integer pageNum) {
		try {
			// PageRequest PageRequest = new PageRequest(page,
			// Constants.PAGE_SIZE);
			PageRequest PageRequest = new PageRequest(page, pageNum);

			int offSetPage = PageRequest.getOffset();
			return Response.getSuccess(openingApplyService.getApplyList(id,
					offSetPage, pageNum));
		} catch (Exception e) {
			return Response.getError("获取列表失败！");
		}
	}

	/**
	 * 进入申请开通
	 * 
	 * @param id
	 */
	@RequestMapping(value = "getApplyDetails/{terminalsId}/{status}", method = RequestMethod.GET)
	public Response getApplyDetails(
			@PathVariable("terminalsId") Integer terminalsId,
			@PathVariable("status") Integer status) {
		try {
			Map<Object, Object> map = new HashMap<Object, Object>();
			// 获得终端详情
			map.put("applyDetails",
					openingApplyService.getApplyDetails(terminalsId));
			// 获得所有商户
			map.put("merchants", openingApplyService.getMerchants());
			// 数据回显(针对重新开通申请)
			map.put("applyFor", openingApplyService.ReApplyFor(terminalsId));
			// 材料名称
			map.put("materialName",
					openingApplyService.getMaterialName(terminalsId, status));
			return Response.getSuccess(map);
		} catch (Exception e) {
			return Response.getError("请求失败！");
		}
	}

	/**
	 * 根据商户id获得商户详细信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "getMerchant/{merchantId}", method = RequestMethod.GET)
	public Response getMerchant(@PathVariable("merchantId") Integer merchantId) {
		try {
			Merchant merchant = new Merchant();
			merchant = openingApplyService.getMerchant(merchantId);
			return Response.getSuccess(merchant);
		} catch (Exception e) {
			return Response.getError("请求失败！");
		}

	}

	/**
	 * 获得所有通道
	 */
	@RequestMapping(value = "getChannels", method = RequestMethod.GET)
	public Response getChannels() {
		try {
			return Response.getSuccess(openingApplyService.getChannels());
		} catch (Exception e) {
			return Response.getError("请求失败！");
		}
	}

	/**
	 * 从第三方接口获得银行
	 */
	@RequestMapping(value = "ChooseBank", method = RequestMethod.GET)
	public Response ChooseBank() {
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("code1", "中国农业银行");
			map.put("code2", "中国工商银行");
			return Response.getSuccess(map);
		} catch (Exception e) {
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
	@RequestMapping(value = "getMaterialName/{terminalId}/{status}", method = RequestMethod.GET)
	public Response getMaterialName(
			@PathVariable("terminalId") Integer terminalId,
			@PathVariable("status") Integer status) {
		try {
			return Response.getSuccess(openingApplyService.getMaterialName(
					terminalId, status));
		} catch (Exception e) {
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
			Integer status = null;
			String openingAppliesId = null;
			Integer terminalId = null;
			String key = null;
			String value = null;
			int i = 0;
			int y = 0;
			for (Map<String, Object> map : paramMap) {
				Set<String> keys = map.keySet();
				if (y == 0) {
					status = (Integer) map.get("status");
					terminalId = (Integer) map.get("terminalId");
					if (status == 2) {
						openingAppliesId = String.valueOf(openingApplyService
								.getApplyesId(terminalId));
						// 删除旧数据
						openingApplyService.deleteOpeningInfos(Integer
								.valueOf(openingAppliesId));
					} else {
						openingApplie.setTerminalId((Integer) map
								.get("terminalId"));
						openingApplie.setApplyCustomerId((Integer) map
								.get("applyCustomerId"));
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
						i++;
					}
					openingApplyService.addApply(key, value, openingAppliesId);
					i = 0;
				}
				y++;
			}
			return Response.getSuccess("添加成功！");
		} catch (Exception e) {
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
			return Response.getError("请求失败！");
		}
	}

	/**
	 * 视频认证
	 */
	@RequestMapping(value = "videoAuthentication", method = RequestMethod.GET)
	public Response videoAuthentication() {
		try {
			return Response.getSuccess("视频认证");
		} catch (Exception e) {
			return Response.getError("视频认证异常");
		}
	}
}
