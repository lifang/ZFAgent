package com.comdosoft.financial.user.mapper.zhangfu;


import java.util.List;
import java.util.Map;


public interface PaychannelMapper {

    List<Map<String, Object>> getTDatesByPayChannel(int pcid);

    List<String> getSupportArea(int pcid);

    List<Map<String, Object>> getRequireMaterial_pra(int pcid);

    List<Map<String, Object>> getRequireMaterial_pub(int pcid);

    List<Map<String, Object>> getStandard_rates(int pcid);

    List<Map<String, Object>> getOther_rate(int pcid);


    

}