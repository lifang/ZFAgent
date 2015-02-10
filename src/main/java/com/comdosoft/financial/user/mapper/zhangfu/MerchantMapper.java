package com.comdosoft.financial.user.mapper.zhangfu;

import java.util.List;
import java.util.Map;

import com.comdosoft.financial.user.domain.zhangfu.Merchant;

/**
 * 商户 - 数据层
 * 
 * @author
 *
 */
public interface MerchantMapper {

    List<Map<Object, Object>> getList(Map<Object, Object> query);

    Map<Object, Object> getOne(int id);

    void insert(Merchant param);

    void update(Merchant param);

    void delete(int id);

}