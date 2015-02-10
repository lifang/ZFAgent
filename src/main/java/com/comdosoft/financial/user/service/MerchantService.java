package com.comdosoft.financial.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.comdosoft.financial.user.domain.Paging;
import com.comdosoft.financial.user.domain.zhangfu.Merchant;
import com.comdosoft.financial.user.mapper.zhangfu.MerchantMapper;

/**
 * 商户 - 业务层<br>
 *
 * @author Java-007 2015年2月7日
 *
 */
@Service
public class MerchantService {

    @Resource
    private MerchantMapper merchantMapper;

    public List<Map<Object, Object>> getList(int customerId, int page, int rows) {
        Map<Object, Object> query = new HashMap<Object, Object>();
        query.put("customerId", customerId);
        Paging paging = new Paging(page, rows);
        query.put("offset", paging.getOffset());
        query.put("rows", paging.getRows());
        return merchantMapper.getList(query);
    }

    public Map<Object, Object> getOne(int id) {
        return merchantMapper.getOne(id);
    }

    public void insert(Merchant param) {
        merchantMapper.insert(param);
    }

    public void update(Merchant param) {
        merchantMapper.update(param);
    }

    public void delete(int id) {
        merchantMapper.delete(id);
    }

}