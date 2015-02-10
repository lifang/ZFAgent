package com.comdosoft.financial.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comdosoft.financial.user.mapper.zhangfu.PaychannelMapper;

@Service
public class PayChannelService {

    @Autowired
    private PaychannelMapper pcMapper;

    public Map<String, Object> payChannelInfo(int pcid) {
        Map<String, Object> map = new HashMap<String, Object>();
        // 支付通道交易费率
        List<Map<String, Object>> tDates = pcMapper.getTDatesByPayChannel(pcid);
        map.put("tDates", tDates);
        // 支付通道开通所需材料 对公
        List<Map<String, Object>> requireMaterial_pub = pcMapper.getRequireMaterial_pub(pcid);
        map.put("requireMaterial_pub", requireMaterial_pub);
        // 支付通道开通所需材料 对私
        List<Map<String, Object>> requireMaterial_pra = pcMapper.getRequireMaterial_pra(pcid);
        map.put("requireMaterial_pra", requireMaterial_pra);
        // 支持区域
        List<String> supportArea = pcMapper.getSupportArea(pcid);
        map.put("supportArea", supportArea);
        // 刷卡交易标准手续费
        List<Map<String, Object>> standard_rates = pcMapper.getStandard_rates(pcid);
        map.put("standard_rates", standard_rates);
        // 其他交易费率
        List<Map<String, Object>> other_rate = pcMapper.getOther_rate(pcid);
        map.put("other_rate", other_rate);
        return map;
    }
}
