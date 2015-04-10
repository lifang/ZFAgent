package com.comdosoft.financial.user.mapper.zhangfu;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface SysconfigMapper {

    Map<String, Object> getValue(String key);

    Map<String, Object> getSysConfig(String param_name);
    
    int operateRecord(Map<String,Object> map);
    
    String value(@Param("key") String key);

}