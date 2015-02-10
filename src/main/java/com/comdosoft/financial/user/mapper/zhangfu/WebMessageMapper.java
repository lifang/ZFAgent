package com.comdosoft.financial.user.mapper.zhangfu;

import java.util.List;

import com.comdosoft.financial.user.domain.zhangfu.WebMessage;
import com.comdosoft.financial.user.utils.page.PageRequest;

public interface WebMessageMapper {

    int count();

    List<Object> findAll(PageRequest request);

    WebMessage findById(Integer id);

}
