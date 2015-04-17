package com.comdosoft.financial.user.service;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.comdosoft.financial.user.domain.query.PosProfitReq;
import com.comdosoft.financial.user.domain.zhangfu.SysConfig;
import com.comdosoft.financial.user.mapper.trades.record.GoodsProfitMapper;
import com.comdosoft.financial.user.mapper.zhangfu.PosProfitMapper;
import com.comdosoft.financial.user.mapper.zhangfu.SysconfigMapper;

@Service
public class PosProfitService {

	private static final Logger logger = Logger.getLogger(PosProfitService.class);
	@Resource
	private PosProfitMapper posProfitMapper;

	@Resource
	private SysconfigMapper sysconfigMapper;

	@Resource
	private GoodsProfitMapper goodsProfitMapper;

	public Map<String, Object> getPosProfitList(PosProfitReq req) throws ParseException {
		Map<String, Object> result = new HashMap<String, Object>();
		int total = posProfitMapper.getPosProfitCount(req);
		List<Map<String, Object>> list = posProfitMapper.getPosProfitList(req);

		Map<String, Object> config = sysconfigMapper.getSysConfig(SysConfig.PARAMNAME_HIREDEFAULTPROFIT);// 租金默认分润比例
		double rate = config.get("param_value") == null ? 0 : Double.parseDouble(config.get("param_value").toString());
		double profit = 0;// 分润
		double totalProfit = 0;// 总分润
		Map<String, Object> newMap = null;
		List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();
		// SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");

		String startTime = null, endTime = null;

		if (list != null && !list.isEmpty()) {
			Map<String, Object> map = null;
			int j = list.size();
			for (int i = 0; i < j; i++) {
				map = list.get(i);
				newMap = new HashMap<String, Object>();
				String types = map.get("types") == null ? null : map.get("types").toString();
				// 销售
				if (null != types && "3".equals(types) || null != types && "5".equals(types) || null != types && "1".equals(types)) {
					double purchasePrice = map.get("purchase_price") == null ? 0 : Integer.parseInt(map.get("purchase_price").toString());// 批购价格
					double salesPrice = map.get("actual_price") == null ? 0 : Integer.parseInt(map.get("actual_price").toString());// 销售价格
					double quantity = map.get("quantity") == null ? 0 : Integer.parseInt(map.get("quantity").toString());// 销售数量
					profit = salesPrice - purchasePrice * quantity;// 分润金额=销售价–批购价

					newMap.put("purchasePrice", purchasePrice);
					newMap.put("salesPrice", salesPrice);
				} else if (null != types && "2".equals(types) || null != types && "4".equals(types)) {// 租赁
					int returnTime = map.get("return_time") == null ? 0 : Integer.parseInt(map.get("return_time").toString());// 租赁时长
					double leasePrice = map.get("lease_price") == null ? 0 : Integer.parseInt(map.get("lease_price").toString());// 租赁价格
					profit = returnTime * leasePrice * rate / 100;// 分润金额=租赁时长*租赁价格*默认的分润比例
					newMap.put("leasePrice", returnTime * leasePrice);
				}

				totalProfit += profit;// 总分润
				newMap.put("profit", profit);
				newMap.put("types", types);
				newMap.put("title", map.get("title"));
				newMap.put("modelNumber", map.get("model_number"));
				newMap.put("createdAt", map.get("created_at"));
				list1.add(newMap);
			}

			startTime = list.get(j - 1).get("created_at") == null ? "" : list.get(j - 1).get("created_at").toString();
			endTime = list.get(0).get("created_at") == null ? "" : list.get(0).get("created_at").toString();

			result.put("startTime", startTime);
			result.put("endTime", endTime);
		}

		result.put("totalProfit", totalProfit);
		result.put("total", total);
		result.put("list", list1);
		return result;
	}

	/**
	 * 获取POS销售分润
	 * 
	 * @param req
	 * @return
	 */
	public Map<String, Object> getProfitResult(PosProfitReq req) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();

		double sum = 0;// 总分润
		int total = goodsProfitMapper.getGoodsProfitCountByAgentId(req);
		List<Map<String, Object>> list = goodsProfitMapper.getGoodsProfitListByAgentId(req);
		if (list != null && !list.isEmpty()) {

			String[] ids = new String[list.size()];
			for (int i = 0, j = list.size(); i < j; i++) {
				Map<String, Object> map = list.get(i);
				ids[i] = map.get("order_id") == null ? null : map.get("order_id").toString();
			}
			req.setIds(ids);

			List<Map<String, Object>> results = posProfitMapper.getOrderListByOrderIds(req);
			if (results != null && !results.isEmpty()) {
				Map<String, Object> innerMap = null;
				for (int i = 0; i < list.size(); i++) {
					innerMap = new HashMap<String, Object>();
					Map<String, Object> map = list.get(i);

					double profit = map.get("profit") == null ? 0 : Double.parseDouble(map.get("profit").toString());
					sum += profit / 100;// 总分润

					innerMap.put("profit", getFormatDouble(profit / 100));// 分润
					innerMap.put("orderid", map.get("order_id") == null ? 0 : Integer.parseInt(map.get("order_id").toString()));
					innerMap.put("goodid", map.get("good_id") == null ? 0 : Integer.parseInt(map.get("good_id").toString()));
					innerMap.put("createdAt", map.get("created_at"));
					for (int j = 0; j < results.size(); j++) {
						if (map.get("good_id").equals(results.get(j).get("good_id"))) {
							logger.debug(results.get(j).get("title"));
							innerMap.put("modelNumber", results.get(j).get("model_number"));// 终端号
							innerMap.put("title", results.get(j).get("title"));// POS机名称
							innerMap.put("types", results.get(j).get("types"));// 类型
							double actual_price = results.get(j).get("actual_price") == null ? 0 : Double.parseDouble(results.get(j).get("actual_price").toString());// 销售价格
							innerMap.put("actual_price", getFormatDouble(actual_price));
							innerMap.put("lease_price", getFormatDouble(results.get(j).get("lease_price") == null ? 0 : Double.parseDouble(results.get(j).get("lease_price").toString())));
							innerMap.put("rental", getFormatDouble(actual_price - profit));// 租金
							break;
						}
					}
					list1.add(innerMap);
				}
			}

			resultMap.put("startTime", getDate(list.get(list.size() - 1).get("created_at").toString()));
			resultMap.put("endTime", getDate(list.get(0).get("created_at").toString()));
		}
		resultMap.put("totalProfit", getFormatDouble(sum));
		resultMap.put("list", list1);
		resultMap.put("total", total);
		return resultMap;
	}

	/**
	 * 保留两位小数
	 * 
	 * @param d
	 * @return
	 */
	private String getFormatDouble(double d) {
		return new DecimalFormat("######0.00").format(d);
	}

	/**
	 * 格式化时间
	 * 
	 * @param date
	 * @return
	 */
	private String getDate(String date) {
		String[] arr = date.split(" ");
		String[] b = arr[0].split("-");
		StringBuilder sb = new StringBuilder();
		sb.append(b[0]);
		sb.append("年");
		sb.append(b[1]);
		sb.append("月");
		sb.append(b[2]);
		sb.append("日");
		return sb.toString();
	}

}
