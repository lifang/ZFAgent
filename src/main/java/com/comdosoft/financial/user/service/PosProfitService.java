package com.comdosoft.financial.user.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.comdosoft.financial.user.domain.query.PosProfitReq;
import com.comdosoft.financial.user.domain.zhangfu.SysConfig;
import com.comdosoft.financial.user.mapper.zhangfu.PosProfitMapper;
import com.comdosoft.financial.user.mapper.zhangfu.SysconfigMapper;

@Service
public class PosProfitService {

	@Resource
	private PosProfitMapper posProfitMapper;

	@Resource
	private SysconfigMapper sysconfigMapper;

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

			// startTime.format("", args)
			// System.out.println(format.parse(startTime));
			result.put("startTime", startTime);
			result.put("endTime", endTime);
		}

		result.put("totalProfit", totalProfit);
		result.put("total", total);
		result.put("list", list1);
		return result;
	}
}
