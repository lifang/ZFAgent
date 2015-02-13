package com.comdosoft.financial.user.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.comdosoft.financial.user.domain.zhangfu.MyOrderReq;
import com.comdosoft.financial.user.utils.page.Page;
import com.comdosoft.financial.user.utils.page.PageRequest;

public class OrderUtils {

    /**
     * 获取追踪记录
     * @param myOrderReq
     * @param list
     * @return
     * @throws ParseException
     */
    public static Page<List<Object>> getTraceByVoId(MyOrderReq myOrderReq, List<Map<String,Object>> list) throws ParseException {
        PageRequest request = new PageRequest(myOrderReq.getPage(), myOrderReq.getPageSize());
        SimpleDateFormat sdf = null;
        Map<String,Object> childrenMap = null;
        List<Map<String,Object>> childrenList = new LinkedList<Map<String,Object>>();
        for(Map<String,Object> m:list){
            childrenMap = new HashMap<String,Object>();
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String created_at =   m.get("created_at")+"";
            String content = m.get("marks_content")+"";
            String person = m.get("marks_person")+"";
            childrenMap.put("marks_time", sdf.format(sdf.parse(created_at)));
            childrenMap.put("marks_content", content);
            childrenMap.put("marks_person", person);
            childrenList.add(childrenMap);
        }
        return new Page<List<Object>>(request, childrenList,childrenList.size());
    }
    
    /**
     * 两个时间相差多少天  date2-date1
     * @param date1
     * @param date2
     * @return
     */
    public static int compareDate(String date1, String date2) {
        int n = 0;
        String formatStyle = "yyyy-MM-dd";
        DateFormat df = new SimpleDateFormat(formatStyle);
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        try {
            c1.setTime(df.parse(date1));
            c2.setTime(df.parse(date2));
        } catch (Exception e3) {
            System.out.println("wrong occured");
        }
        // List list = new ArrayList();
        while (!c1.after(c2)) { // 循环对比，直到相等，n 就是所要的结果
            // list.add(df.format(c1.getTime())); // 这里可以把间隔的日期存到数组中 打印出来
            n++;
            c1.add(Calendar.DATE, 1); // 比较天数，日期+1
            
        }
        n = n - 1;
        return n;
    }
}
