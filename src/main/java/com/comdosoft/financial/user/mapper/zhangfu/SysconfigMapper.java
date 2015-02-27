package com.comdosoft.financial.user.mapper.zhangfu;

import java.util.Map;

public interface SysconfigMapper {

    Map<String, Object> getValue(String key);

    Map<String, Object> getSysConfig(String param_name);

}