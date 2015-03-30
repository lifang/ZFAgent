package com.comdosoft.financial.user.mapper.zhangfu;

import java.util.List;
import java.util.Map;

import com.comdosoft.financial.user.domain.zhangfu.CustomerAddress;

/***
 * 收货地址
 * 
 * @author huashuo
 * 
 */

public interface CustomeraddressMapper {

	int insertadderss(CustomerAddress req);

	int deleteadderss(int param);

	Map<String, Object> queryaddress(int id);

	void upadderss(CustomerAddress req, int id);

	List<Map<String, Object>> queryadderss(int id);

	int setisDefault(CustomerAddress cus);

	void updateDefault(int oidDefault, int Default);

}
