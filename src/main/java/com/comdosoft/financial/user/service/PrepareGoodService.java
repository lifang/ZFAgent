package com.comdosoft.financial.user.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.comdosoft.financial.user.domain.query.PrepareGoodReq;
import com.comdosoft.financial.user.mapper.zhangfu.PrepareGoodMapper;
import com.comdosoft.financial.user.utils.Param;
import com.comdosoft.financial.user.utils.SysUtils;

@Service
public class PrepareGoodService {

    @Autowired
    private PrepareGoodMapper prepareGoodMapper;

    public Map<String, Object> getList(PrepareGoodReq req) {
        req.setStartTime(Param.setDay(req.getStartTime()));
        req.setEndTime(Param.setDay(req.getEndTime()));
        Map<String, Object> map = new HashMap<String, Object>();
        int total = prepareGoodMapper.getPrepareGoodTotal(req);
        map.put("total", total);
        List<Map<String, Object>> list = prepareGoodMapper.getPrepareGoodList(req);
        map.put("list", list);
        return map;
    }

    public Map<String, Object> getInfo(PrepareGoodReq req) {
        return prepareGoodMapper.getInfo(req);
    }

    @Transactional(value = "transactionManager-zhangfu")
    public int add(PrepareGoodReq req) {
        try {
            if (null != req.getSerialNums() && req.getSerialNums().length > 0) {
                StringBuilder sb = new StringBuilder();
                for (String serialNum : req.getSerialNums()) {
                    req.setSerialNum(serialNum);
                    prepareGoodMapper.upTerminal_AgentId(req);
                    sb.append(serialNum + ",");
                }
                sb.deleteCharAt(sb.length() - 1);
                req.setTerminalList(sb.toString());
                req.setQuantity(req.getSerialNums().length);
                prepareGoodMapper.add(req);
                return 1;
            } else {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List<Map<String, Object>> getSonAgent(PrepareGoodReq req) {
        return prepareGoodMapper.getSonAgent(req);
    }

    public List<Map<String, Object>> getGoodList(PrepareGoodReq req) {
        return prepareGoodMapper.getGoodList(req);
    }

    public List<Map<String, Object>> getPayChannelList(PrepareGoodReq req) {
        return prepareGoodMapper.getPayChannelList(req);
    }

    public Map<String, Object> getTerminalsList(PrepareGoodReq req) {
        if (null != req.getSerialNums() && req.getSerialNums().length > 0) {
            req.setTerminalList(SysUtils.Arry2Str(req.getSerialNums()));
        } else {
            req.setTerminalList(null);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        int total = prepareGoodMapper.getTerminalTotal(req);
        map.put("total", total);
        List<Map<String, Object>> list = prepareGoodMapper.getTerminalList(req);
        map.put("list", list);
        return map;
    }

    public Map<String, Object> checkTerminals(PrepareGoodReq req) {
        List<Map<String, Object>> errorTerminals = new ArrayList<Map<String, Object>>();
        String[] ss = req.getSerialNum().split("\\s+|,|;");
        List<String> list = new ArrayList<String>();
        for (String string : ss) {
            if (!"".equals(string.trim())) {
                list.add(string.trim());
            }
        }
        if (list.size() > 0) {
            Map<String, Object> map = null;
            for (String s : list) {
                map = new HashMap<String, Object>();
                map.put("serialNum", s);
                req.setSerialNum(s);
                int t1 = prepareGoodMapper.isExit(req);
                if (t1 == 0) {
                    map.put("error", "终端不存在");
                    errorTerminals.add(map);
                } else {
                    int t2 = prepareGoodMapper.isUsed(req);
                    if (t2 == 0) {
                        map.put("error", "终端已使用");
                        errorTerminals.add(map);
                    } else {
                        int t3 = prepareGoodMapper.isGoodId(req);
                        if (t3 == 0) {
                            map.put("error", "终端不属于该商品");
                            errorTerminals.add(map);
                        } else {
                            int t4 = prepareGoodMapper.isPcId(req);
                            if (t4 == 0) {
                                map.put("error", "终端不属于该支付通道");
                                errorTerminals.add(map);
                            }
                        }
                    }
                }

            }
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errorCount", errorTerminals.size());
        result.put("errorList", errorTerminals);
        return result;
    }

}
