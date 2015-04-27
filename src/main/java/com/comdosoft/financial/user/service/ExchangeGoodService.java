package com.comdosoft.financial.user.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.comdosoft.financial.user.domain.query.ExchangeGoodReq;
import com.comdosoft.financial.user.mapper.zhangfu.ExchangeGoodMapper;
import com.comdosoft.financial.user.utils.Param;

@Service
public class ExchangeGoodService {

    @Autowired
    private ExchangeGoodMapper exchangeGoodMapper;

    public Map<String, Object> getList(ExchangeGoodReq req) {
        req.setStartTime(Param.setDay(req.getStartTime()));
        req.setEndTime(Param.setDay(req.getEndTime()));
        Map<String, Object> map = new HashMap<String, Object>();
        int total = exchangeGoodMapper.getExchangeGoodTotal(req);
        map.put("total", total);
        List<Map<String, Object>> list = exchangeGoodMapper.getExchangeGoodList(req);
        map.put("list", list);
        return map;
    }

    public Map<String, Object> getInfo(ExchangeGoodReq req) {
        return exchangeGoodMapper.getInfo(req);
    }

    @Transactional(value = "transactionManager-zhangfu")
    public int add(ExchangeGoodReq req) {
        try {
            if (null != req.getSerialNums() && req.getSerialNums().length > 0) {
                StringBuilder sb = new StringBuilder();
                for (String serialNum : req.getSerialNums()) {
                    req.setSerialNum(serialNum);
                    exchangeGoodMapper.upTerminal_AgentId(req);
                    sb.append(serialNum + ",");
                }
                sb.deleteCharAt(sb.length() - 1);
                req.setQuantity(req.getSerialNums().length);
                req.setTerminalList(sb.toString());
                exchangeGoodMapper.add(req);
                return 1;
            } else {
                return -1;
            }
        } catch (Exception e) {
            return 0;
        }
    }

    public Map<String, Object> getTerminalsList(ExchangeGoodReq req) {
        Map<String, Object> map = new HashMap<String, Object>();
        int total = exchangeGoodMapper.getTerminalTotal(req);
        map.put("total", total);
        List<Map<String, Object>> list = exchangeGoodMapper.getTerminalList(req);
        map.put("list", list);
        return map;
    }

    public Map<String, Object> checkTerminals(ExchangeGoodReq req) {
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
                int t1 = exchangeGoodMapper.isExit(req);
                if (t1 == 0) {
                    map.put("error", "终端不存在");
                    errorTerminals.add(map);
                } else {
                    int t2 = exchangeGoodMapper.isUsed(req);
                    if (t2 == 0) {
                        map.put("error", "终端已使用");
                        errorTerminals.add(map);
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
