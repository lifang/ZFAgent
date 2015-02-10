package com.comdosoft.financial.user.mapper.zhangfu;

import java.util.List;
import java.util.Map;

/**
 * 用户 - 数据层
 * 
 * @author
 *
 */
public interface CustomerMapper {

    Map<Object, Object> getSysConfig(String paramName);

    Map<Object, Object> getOne(int id);

    void update(Map<Object, Object> param);

    void updatePassword(Map<Object, Object> param);

    List<Map<Object, Object>> getIntegralList(Map<Object, Object> param);

    Map<Object, Object> getIntegralTotal(int customerId);

    void insertIntegralConvert(Map<Object, Object> param);

    void insertIntegralRecord(Map<Object, Object> param);

    List<Map<Object, Object>> getAddressList(int customerId);

    Map<Object, Object> getOneAddress(int id);

    void insertAddress(Map<Object, Object> param);

    void deleteAddress(int id);

}