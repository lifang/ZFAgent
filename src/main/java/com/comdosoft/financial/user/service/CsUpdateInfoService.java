package com.comdosoft.financial.user.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.comdosoft.financial.user.domain.zhangfu.MyOrderReq;
import com.comdosoft.financial.user.domain.zhangfu.RepairStatus;
import com.comdosoft.financial.user.domain.zhangfu.UpdateStatus;
import com.comdosoft.financial.user.mapper.zhangfu.CsUpdateInfoMapper;
import com.comdosoft.financial.user.utils.OrderUtils;
import com.comdosoft.financial.user.utils.page.Page;
import com.comdosoft.financial.user.utils.page.PageRequest;
@Service
public class CsUpdateInfoService {

    @Resource
    private CsUpdateInfoMapper csUpdateInfoMapper;
    @Resource
    private CsCencelsService csCencelsService;
    
    public Page<List<Object>> findAll(MyOrderReq myOrderReq) throws ParseException {
        PageRequest request = new PageRequest(myOrderReq.getPage(), myOrderReq.getRows());
        List<Map<String, Object>> o = csUpdateInfoMapper.findAll(myOrderReq);
        int count = csUpdateInfoMapper.count(myOrderReq);
        List<Map<String, Object>> list = putDate(o);
        return new Page<List<Object>>(request, list,count);
    }

    public List<Map<String, Object>> putDate(List<Map<String, Object>> o) {
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        Map<String,Object> map = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
        for(Map<String,Object> m: o){
            map = new HashMap<String,Object>();
            String d = (m.get("created_at")==null?"":m.get("created_at").toString());
            Date date;
            try {
                date = sdf.parse(d);
                String c_date = sdf.format(date);
                map.put("create_time", c_date);
            } catch (ParseException e) {
                map.put("create_time", "");
                e.printStackTrace();
            }
            map.put("id",m.get("id"));
            map.put("status", m.get("status")==null?"":m.get("status"));
            map.put("terminal_num", m.get("serial_num")==null?"":m.get("serial_num"));//终端号
            map.put("apply_num", m.get("apply_num")==null?"":m.get("apply_num"));//维修编号
            map.put("brand_name", m.get("brand_name")==null?"":m.get("brand_name"));
            map.put("brand_number", m.get("brand_number")==null?"":m.get("brand_number"));
            map.put("zhifu_pingtai", m.get("zhifu_pt")==null?"":m.get("zhifu_pt"));
            map.put("merchant_name", m.get("merchant_name")==null?"":m.get("merchant_name"));
            map.put("merchant_phone", m.get("mer_phone")==null?"":m.get("mer_phone"));
            list.add(map);
        }
        return list;
    }

    public void cancelApply(MyOrderReq myOrderReq) {
        myOrderReq.setRepairStatus(RepairStatus.CANCEL);
        csUpdateInfoMapper.cancelApply(myOrderReq);
    }

    public Map<String,Object>  findById(MyOrderReq myOrderReq) throws ParseException {
        Map<String, Object> o = csUpdateInfoMapper.findById(myOrderReq);
        Map<String,Object> map = new HashMap<String,Object>();
        String id = o.get("id").toString();
        map.put("id", id);
        map.put("status", o.get("apply_status")==null?"":o.get("apply_status"));
        map.put("apply_num", o.get("apply_num")==null?"":o.get("apply_num"));//维修编号
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String apply_time =   o.get("apply_time")==null?"":o.get("apply_time").toString();
        if(apply_time==""){
            map.put("apply_time", "");
        }else{
            map.put("apply_time", sdf.format(sdf.parse(apply_time)));
        }
        map.put("terminal_num", o.get("serial_num")==null?"":o.get("serial_num"));
        map.put("brand_name", o.get("brand_name")==null?"":o.get("brand_name"));
        map.put("good_name", o.get("good_name")==null?"":o.get("good_name"));
        map.put("brand_number", o.get("brand_number")==null?"":o.get("brand_number"));
        map.put("zhifu_pingtai", o.get("zhifu_pt")==null?"":o.get("zhifu_pt"));
        map.put("merchant_name", o.get("merchant_name")==null?"":o.get("merchant_name"));
        map.put("merchant_phone", o.get("mer_phone")==null?"":o.get("mer_phone"));
        myOrderReq.setId(Integer.parseInt(id));
        String json = o.get("templete_info_xml")==null?"":o.get("templete_info_xml").toString();
        map = csCencelsService.getTemplePaths(map, json);
        List<Map<String,Object>> list = csUpdateInfoMapper.findTraceById(myOrderReq);
        map.put("comments", OrderUtils.getTraceByVoId(myOrderReq, list));
        return map;
    }

    public Page<List<Object>> orderSearch(MyOrderReq myOrderReq) throws ParseException {
        PageRequest request = new PageRequest(myOrderReq.getPage(), myOrderReq.getRows());
        List<Map<String, Object>> o = csUpdateInfoMapper.search(myOrderReq);
        int count = csUpdateInfoMapper.countSearch(myOrderReq);
        List<Map<String, Object>> list = putDate(o);
        return new Page<List<Object>>(request, list,count);
    }
    
    public void resubmitCancel(MyOrderReq myOrderReq) {
        myOrderReq.setUpdateStatus(UpdateStatus.PENDING);
        csUpdateInfoMapper.changeStatus(myOrderReq);
    }
}
