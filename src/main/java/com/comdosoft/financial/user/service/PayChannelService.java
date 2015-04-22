package com.comdosoft.financial.user.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.comdosoft.financial.user.mapper.zhangfu.PaychannelMapper;
import com.comdosoft.financial.user.utils.SysUtils;

@Service
public class PayChannelService {

    @Autowired
    private PaychannelMapper pcMapper;
    
    @Value("${filePath}")
    private String filePath;

    public Map<String, Object> payChannelInfo(int pcid) {
        Map<String, Object> map =pcMapper.getPcinfo(pcid);// new HashMap<String, Object>();
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
      //收单机构
        int factoryId = SysUtils.Object2int(map.get("factory_id"));
        if (factoryId > 0) {
            Map<String, Object> factoryMap = pcMapper.getFactoryById(pcid);
            if(factoryMap!=null){
                factoryMap.put("logo_file_path", filePath+factoryMap.get("logo_file_path"));
                map.put("pcfactory", factoryMap);
            }
        }
        return map;
    }
    
    public static void main(String[] args) {
        
    }
}
