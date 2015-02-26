package com.comdosoft.financial.user.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.comdosoft.financial.user.domain.zhangfu.MyOrderReq;
import com.comdosoft.financial.user.domain.zhangfu.RepairStatus;
import com.comdosoft.financial.user.domain.zhangfu.UpdateStatus;
import com.comdosoft.financial.user.mapper.zhangfu.CsCencelsMapper;
import com.comdosoft.financial.user.utils.OrderUtils;
import com.comdosoft.financial.user.utils.page.Page;
import com.comdosoft.financial.user.utils.page.PageRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
@Service
public class CsCencelsService {

    @Resource
    private CsCencelsMapper csCencelsMapper;
    public Page<List<Object>>  findAll(MyOrderReq myOrderReq) throws ParseException {
        PageRequest request = new PageRequest(myOrderReq.getPage(), myOrderReq.getPageSize());
        int count = csCencelsMapper.count(myOrderReq);
        List<Map<String, Object>> o = csCencelsMapper.findAll(myOrderReq);
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        Map<String,Object> map = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
        for(Map<String,Object> m: o){
            map = new HashMap<String,Object>();
            String d = (m.get("created_at")+"");
            Date date = sdf.parse(d);
            String c_date = sdf.format(date);
            String status = (m.get("status")+"");
            map.put("id",m.get("id"));
            map.put("status", status);
            map.put("create_time", c_date);
            map.put("terminal_num", m.get("serial_num"));//终端号
            map.put("apply_num", m.get("apply_num"));//维修编号
            list.add(map);
        }
        return new Page<List<Object>>(request, list,count);
    }

    /**
     * 取消申请
     * @param myOrderReq
     */
    public void cancelApply(MyOrderReq myOrderReq) {
        myOrderReq.setRepairStatus(RepairStatus.CANCEL);
        csCencelsMapper.changeStatus(myOrderReq);
    }

    @SuppressWarnings("unchecked")
    public Map<String,Object> findById(MyOrderReq myOrderReq) throws ParseException {
        Map<String, Object> o = csCencelsMapper.findById(myOrderReq);
        Map<String,Object> map = new HashMap<String,Object>();
        String id = o.get("id").toString();
        map.put("id", id);
        map.put("status", o.get("apply_status"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String apply_time =   o.get("apply_time")+"";
        map.put("apply_time", sdf.format(sdf.parse(apply_time)));
        map.put("terminal_num", o.get("serial_num")+"");
        map.put("brand_name", o.get("brand_name")+"");
        map.put("brand_number", o.get("brand_number")+"");
        map.put("zhifu_pingtai", o.get("zhifu_pt")+"");
        map.put("merchant_name", o.get("merchant_name")+"");
        map.put("merchant_phone", o.get("mer_phone")+"");
        map.put("receiver_addr", o.get("address")+"");
        String json = o.get("templete_info_xml")+"";
        ObjectMapper mapper = new ObjectMapper();
        if(!json.equals("")){
            List<LinkedHashMap<String, Object>> list_json;
            try {
                list_json = mapper.readValue(json, List.class);
                map.put("resource_info", list_json);
            } catch (IOException e) {
                e.printStackTrace();
                map.put("resource_info", "");
            }
        }else{
            map.put("resource_info", "");
        }
        myOrderReq.setId(Integer.parseInt(id));
        List<Map<String,Object>> list = csCencelsMapper.findTraceById(myOrderReq);
        map.put("comments", OrderUtils.getTraceByVoId(myOrderReq, list));
        return map;
    }

    /**
     * 重新提交注销
     * @param myOrderReq
     */
    public void resubmitCancel(MyOrderReq myOrderReq) {
        myOrderReq.setUpdateStatus(UpdateStatus.PENDING);
        csCencelsMapper.changeStatus(myOrderReq);
    }

}
