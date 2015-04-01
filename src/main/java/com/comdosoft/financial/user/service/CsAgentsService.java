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
import com.comdosoft.financial.user.domain.zhangfu.ServiceStatus;
import com.comdosoft.financial.user.mapper.zhangfu.CsAgentsMapper;
import com.comdosoft.financial.user.utils.OrderUtils;
import com.comdosoft.financial.user.utils.page.Page;
import com.comdosoft.financial.user.utils.page.PageRequest;
@Service
public class CsAgentsService {

    @Resource
    private CsAgentsMapper csAgentsMapper;
    public Page<List<Object>>  findAll(MyOrderReq myOrderReq) throws ParseException {
        PageRequest request = new PageRequest(myOrderReq.getPage(), myOrderReq.getRows());
        int count = csAgentsMapper.count(myOrderReq);
        List<Map<String, Object>> o = csAgentsMapper.findAll(myOrderReq);
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        Map<String,Object> map = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
        for(Map<String,Object> m: o){
            map = new HashMap<String,Object>();
            String d = (m.get("created_at")+"");
            Date date = sdf.parse(d);
            String c_date = sdf.format(date);
            String status = (m.get("status")+"");
            String t = m.get("terminals_list")+"";
            String[] arrt = t.split(",");
//            String status_name = RepairStatus.getName(Integer.parseInt(status));
            map.put("id",m.get("id"));
            map.put("status", status);
            map.put("create_time", c_date);
            if(arrt.length>0){
                map.put("terminal_num", arrt[0]);//终端号
            }else{
                map.put("terminal_num", "");
            }
            map.put("apply_num", m.get("apply_num"));//维修编号
            map.put("terminals_quantity", m.get("terminals_quantity"));//终端数量
            list.add(map);
        }
        return new Page<List<Object>>(request, list,count);
    }

    /**
     * 取消申请
     * @param myOrderReq
     */
    public Integer cancelApply(MyOrderReq myOrderReq) {
    	myOrderReq.setServiceStatus(ServiceStatus.CANCEL);
        int i = csAgentsMapper.changeStatus(myOrderReq);
        return i;
    }

    public Map<String,Object> findById(MyOrderReq myOrderReq) throws ParseException {
        Map<String, Object> o = csAgentsMapper.findById(myOrderReq);
        Map<String,Object> map = new HashMap<String,Object>();
        if(null ==o ){
        	return map;
        }
        String id = o.get("id").toString();
        map.put("id", id);
//        String status_name = RepairStatus.getName(Integer.parseInt(o.get("apply_status")+""));
        map.put("status", o.get("apply_status"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String apply_time =   o.get("apply_time")+"";
        map.put("apply_time", sdf.format(sdf.parse(apply_time)));
        map.put("terminals_quantity", o.get("terminals_quantity")+"");
        map.put("terminals_list", o.get("terminals_list")+"");
        map.put("address", o.get("address")+"");
        map.put("apply_num", o.get("apply_num")+"");
        map.put("reason", o.get("reason")+"");
        myOrderReq.setId(Integer.parseInt(id));
        List<Map<String,Object>> list = csAgentsMapper.findTraceById(myOrderReq);
        map.put("comments", OrderUtils.getTraceByVoId(myOrderReq, list));
        return map;
    }

	public Page<List<Object>> Search(MyOrderReq myOrderReq) {
		  PageRequest request = new PageRequest(myOrderReq.getPage(), myOrderReq.getRows());
	        int count = csAgentsMapper.countSearch(myOrderReq);
	        List<Map<String, Object>> o = csAgentsMapper.search(myOrderReq);
	        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
	        Map<String,Object> map = null;
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	        for(Map<String,Object> m: o){
	            map = new HashMap<String,Object>();
	            String d = (m.get("created_at")+"");
	            Date date;
				try {
					date = sdf.parse(d);
					String c_date = sdf.format(date);
					map.put("create_time", c_date);
				} catch (ParseException e) {
					e.printStackTrace();
				}
	            String status = (m.get("status")+"");
	            String t = m.get("terminals_list")+"";
	            String[] arrt = t.split(",");
//	            String status_name = RepairStatus.getName(Integer.parseInt(status));
	            map.put("id",m.get("id"));
	            map.put("status", status);
	            if(arrt.length>0){
	                map.put("terminal_num", arrt[0]);//终端号
	            }else{
	                map.put("terminal_num", "");
	            }
	            map.put("apply_num", m.get("apply_num"));//维修编号
	            map.put("terminals_quantity", m.get("terminals_quantity"));//终端数量
	            list.add(map);
	        }
	        return new Page<List<Object>>(request, list,count);
	}

}
