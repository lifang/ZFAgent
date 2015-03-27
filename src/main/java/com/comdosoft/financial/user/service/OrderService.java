package com.comdosoft.financial.user.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comdosoft.financial.user.domain.query.OrderReq;
import com.comdosoft.financial.user.domain.query.PosReq;
import com.comdosoft.financial.user.domain.zhangfu.CsOutStorage;
import com.comdosoft.financial.user.domain.zhangfu.Customer;
import com.comdosoft.financial.user.domain.zhangfu.Good;
import com.comdosoft.financial.user.domain.zhangfu.GoodsPicture;
import com.comdosoft.financial.user.domain.zhangfu.MyOrderReq;
import com.comdosoft.financial.user.domain.zhangfu.Order;
import com.comdosoft.financial.user.domain.zhangfu.OrderGood;
import com.comdosoft.financial.user.domain.zhangfu.OrderStatus;
import com.comdosoft.financial.user.domain.zhangfu.Terminal;
import com.comdosoft.financial.user.mapper.zhangfu.GoodMapper;
import com.comdosoft.financial.user.mapper.zhangfu.OrderMapper;
import com.comdosoft.financial.user.utils.OrderUtils;
import com.comdosoft.financial.user.utils.SysUtils;
import com.comdosoft.financial.user.utils.Exception.LowstocksException;
import com.comdosoft.financial.user.utils.page.Page;
import com.comdosoft.financial.user.utils.page.PageRequest;

@Service
public class OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private  GoodService goodService;
    
    @Autowired
    private  GoodMapper goodMapper;

    
    public int createOrderFromAgent(OrderReq orderreq) throws LowstocksException {
            Map<String, Object> goodMap = orderMapper.getGoodInfo(orderreq);
            int quantity = orderreq.getQuantity();
            int opening_cost = SysUtils.Object2int(goodMap.get("opening_cost"));
            int payprice=0;
            int price=0;
            int count = SysUtils.Object2int(goodMap.get("count"));
            if (count < quantity) {
                throw new LowstocksException("库存不足");
            } else {
                int goodId = SysUtils.Object2int(goodMap.get("goodid"));
                PosReq posreq = new PosReq();
                posreq.setGoodId(goodId);
                posreq.setCity_id(count-quantity);
                //goodMapper.upQuantity(posreq);
            }
            //3 代理商代购 4 代理商代租赁 5 代理商批购
            if(3==orderreq.getOrderType()){
                int retail_price = SysUtils.Object2int(goodMap.get("retail_price"));
                payprice=retail_price+opening_cost;
                price=SysUtils.Object2int(goodMap.get("price"))+opening_cost;
            }else if(4==orderreq.getOrderType()){
                int lease_deposit = SysUtils.Object2int(goodMap.get("lease_deposit"));
                payprice=lease_deposit+opening_cost;
                price=payprice;
            }else if(5==orderreq.getOrderType()){
                int purchase_price = SysUtils.Object2int(goodMap.get("purchase_price"));
                int floor_price = SysUtils.Object2int(goodMap.get("floor_price"));
                int floor_purchase_quantity = SysUtils.Object2int(goodMap.get("floor_purchase_quantity"));
                if(quantity<floor_purchase_quantity){
                    return 0; 
                }
                int factprice=goodService.setPurchasePrice(orderreq.getAgent_id(), purchase_price, floor_price);
                payprice=factprice+opening_cost;
                price=SysUtils.Object2int(goodMap.get("price"))+opening_cost;
            }else{
                return 0;
            }
            orderreq.setTotalprice(payprice*quantity);
            orderreq.setTotalcount(quantity);
            orderreq.setOrdernumber(SysUtils.getOrderNum(orderreq.getOrderType()));
            orderMapper.addOrder(orderreq);
            
            orderreq.setPrice(price);
            orderreq.setRetail_price(payprice);
            orderMapper.addOrderGood(orderreq);
            return orderreq.getId();
        
    }
    
    
    
    /**
     * 上jwb
     * -------------------------------------------------------------
     * 下gch
     */
//获取批购订单
    public Page<Object> getWholesaleOrder(MyOrderReq myOrderReq) {
        PageRequest request = new PageRequest(myOrderReq.getPage(), myOrderReq.getRows());
        int count = orderMapper.countWholesaleOrder(myOrderReq.getCustomer_id());
        List<Order> centers = orderMapper.getWholesaleOrder(myOrderReq);
        List<Object> obj_list = new ArrayList<Object>();
        Map<String,Object> map = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
        for(Order o : centers){
            map = new LinkedHashMap<String, Object>();
            map.put("order_id", o.getId()+"");
            map.put("order_number", o.getOrderNumber()==null?"":o.getOrderNumber());
            String d = sdf.format(o.getCreatedAt());
            map.put("order_createTime", d);
            map.put("order_status", o.getStatus()==null?"":o.getStatus()+"");
            
            Integer actual_price = o.getActualPrice()==null?0:o.getActualPrice();//这个单子的总额
            int pay_status = o.getFrontPayStatus()==null?0:o.getFrontPayStatus(); //1 已支付  0 未支付
            Integer zhifu_dingjin = 0;
            Integer dj_price = o.getFrontMoney()==null?0:o.getFrontMoney();
            if(pay_status==1){
                zhifu_dingjin = dj_price;
            }
            BigDecimal bd_act = new BigDecimal(actual_price);
            BigDecimal bd_dj = new BigDecimal(zhifu_dingjin);
            BigDecimal shengyu_price =     bd_act.subtract(bd_dj) ;    //actual_price-zhifu_dingjin;
            List<CsOutStorage> csOutList = o.getCsOutStorageList();
            Integer quantity = 0;
            for(CsOutStorage cs_out:csOutList){
                if(null !=cs_out.getStatus() && cs_out.getStatus()==1){
                    Integer q = cs_out.getQuantity();
                    quantity = quantity+q;
                }
            }
            map.put("zhifu_dingjin", zhifu_dingjin+"");//已付定金
            map.put("shengyu_price", shengyu_price+"");//
            map.put("actual_price", bd_act+"");//
            map.put("quantity", quantity+"");//已发货数量
            
            List<OrderGood> olist = o.getOrderGoodsList();
            List<Object> newObjList = new ArrayList<Object>();
            Map<String, Object> omap = null;
            if (olist.size() > 0) {
                for (OrderGood od : olist) {
                    omap = new HashMap<String, Object>();
                    omap.put("good_id",  od.getGood() == null ? "" : od.getGood().getId()==null?"":od.getGood().getId().toString());
                    omap.put("good_price", od.getGood() == null ? "" : od.getGood().getPrice()==null?"": od.getGood().getPrice());//原价
                    omap.put("good_batch_price",od.getGood() == null ? "" : od.getGood().getPurchasePrice()==null?"":od.getGood().getPurchasePrice());
                    omap.put("good_num", od.getQuantity() == null ? "" : od.getQuantity().toString());
                    omap.put("good_name", od.getGood() == null ? "" : od.getGood().getTitle()==null?"":od.getGood().getTitle());
                    omap.put("good_brand", od.getGood() == null ? "" : od.getGood().getGoodsBrand() == null ? "" : od.getGood().getGoodsBrand().getName()==null?"" :od.getGood().getGoodsBrand().getName());
                    omap.put("good_channel", od.getPayChannel() == null ? "" : od.getPayChannel().getName()==null?"":od.getPayChannel().getName());
                    String good_logo = "";
                    if(null !=od.getGood()){
                        Good g = od.getGood();
                        Integer gid = g.getId();
                        List<GoodsPicture> list = orderMapper.findPicByGoodId(gid);
                        if(list.size()>0){
                            GoodsPicture gp  = list.get(0);
                            good_logo = gp.getUrlPath();
                        }
                    }
                    omap.put("good_logo", good_logo);
                    newObjList.add(omap);
                }
                map.put("order_goodsList", newObjList);
            }
            obj_list.add(map);
        }
        return new Page<Object>(request, obj_list, count);
    }
//    获取代购订单
    public Page<Object> getProxyOrder(MyOrderReq myOrderReq) {
        PageRequest request = new PageRequest(myOrderReq.getPage(), myOrderReq.getRows());
        int count = orderMapper.countProxyOrder(myOrderReq.getCustomer_id());
        List<Order> centers = orderMapper.getProxyOrder(myOrderReq);
        List<Object> obj_list = new ArrayList<Object>();
        Map<String,Object> map = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
        for(Order o : centers){
            map = new LinkedHashMap<String, Object>();
            map.put("order_id", o.getId()+"");
            map.put("order_number", o.getOrderNumber()==null?"":o.getOrderNumber());
            String d = sdf.format(o.getCreatedAt());
            map.put("order_createTime", d);
            map.put("order_status", o.getStatus()==null?"":o.getStatus()+"");
            map.put("order_totalNum", o.getTotalQuantity() == null ? "" : o.getTotalQuantity().toString());// 订单总件数
            map.put("order_totalPrice", o.getActualPrice()==null?"":o.getActualPrice()+"");
            map.put("order_psf", "0");//配送费
            Integer guishu_user = o.getBelongsUserId();
            Customer customer = new Customer();
            customer.setId(guishu_user);
            customer = orderMapper.findCustomerById(customer);
            if(customer ==null){
                map.put("guishu_user", ""); 
            }else{
            	map.put("guishu_user", customer.getName()==null?"": customer.getName()); 
            }
            List<OrderGood> olist = o.getOrderGoodsList();
            List<Object> newObjList = new ArrayList<Object>();
            Map<String, Object> omap = null;
            if (olist.size() > 0) {
                for (OrderGood od : olist) {
                    omap = new HashMap<String, Object>();
                    omap.put("good_id",  od.getGood() == null ? "" : od.getGood().getId().toString());
                    omap.put("good_price", od.getGood() == null ? "" : od.getGood().getRetailPrice().toString());
                    omap.put("good_num", od.getQuantity() == null ? "" : od.getQuantity().toString());
                    omap.put("good_name", od.getGood() == null ? "" : od.getGood().getTitle()==null?"":od.getGood().getTitle());
                    omap.put("good_brand", od.getGood() == null ? "" : od.getGood().getGoodsBrand() == null ? "" : od.getGood().getGoodsBrand().getName()== null ? "" : od.getGood().getGoodsBrand().getName());
                    omap.put("good_channel", od.getPayChannel() == null ? "" : od.getPayChannel().getName()==null?"":od.getPayChannel().getName());
                    String good_logo = "";
                    if(null !=od.getGood()){
                        Good g = od.getGood();
                        Integer gid = g.getId();
                        List<GoodsPicture> list = orderMapper.findPicByGoodId(gid);
                        if(list.size()>0){
                            GoodsPicture gp  = list.get(0);
                            good_logo = gp.getUrlPath();
                        }
                    }
                    omap.put("good_logo", good_logo);
                    newObjList.add(omap);
                }
                map.put("order_goodsList", newObjList);
            }
            obj_list.add(map);
        }
        return new Page<Object>(request, obj_list, count);
    }

    /**
     * 批购订单详情
     * @param id
     * @return
     * @throws ParseException 
     */
    public Map<String,Object> getWholesaleById(Integer id)  {
        Order o = orderMapper.getWholesaleById(id);
        Map<String,Object> map = new LinkedHashMap<String, Object>();
        if(null == o){
        	return map;
        }
        map.put("order_id", id+"");
//        Integer actual_price = o.getActualPrice();//这个单子的总额
        String pay_status = o.getFrontPayStatus()==null?"":o.getFrontPayStatus().toString(); //1 已支付  0 未支付
        Integer zhifu_dingjin = 0;
        Integer dj_price = o.getFrontMoney()==null?0:o.getFrontMoney();
        if(pay_status.equals("1")){
            zhifu_dingjin = dj_price;
        }
//        Integer shengyu_price = actual_price-zhifu_dingjin;
        List<CsOutStorage> csOutList = o.getCsOutStorageList();
        Integer quantity = 0;
        for(CsOutStorage cs_out:csOutList){
            if(null !=cs_out.getStatus() && cs_out.getStatus()==1){
                Integer q = cs_out.getQuantity();
                quantity = quantity+q;
            }
        }
        map.put("pay_status", pay_status+"");
        map.put("order_totalPrice", o.getActualPrice()==null?"":o.getActualPrice()+"");//总共金额
        map.put("total_dingjin", o.getFrontMoney()==null?"":o.getFrontMoney()+"");//定金总额
        map.put("zhifu_dingjin", zhifu_dingjin+"");//已付定金
//        map.put("shengyu_price", shengyu_price);//剩余金额
        map.put("shipped_quantity", quantity+"");//已发货数量
        map.put("total_quantity", o.getTotalQuantity() == null ? "" : o.getTotalQuantity().toString());// 订单总件数  
        
        map.put("order_receiver", o.getCustomerAddress()==null ?"":o.getCustomerAddress().getReceiver()==null ?"":o.getCustomerAddress().getReceiver());
        map.put("order_receiver_phone", o.getCustomerAddress()==null ?"":o.getCustomerAddress().getMoblephone()==null ?"":o.getCustomerAddress().getMoblephone());
        map.put("order_address", o.getCustomerAddress()==null ?"":o.getCustomerAddress().getAddress()==null ?"":o.getCustomerAddress().getAddress());
        
        map.put("order_comment", o.getComment()==null?"":o.getComment());//留言
        Integer invoce_type = o.getInvoiceType();
        String invoce_name ="";
        if(null != invoce_type && invoce_type==1){//个人
            invoce_name = "个人";
        }else if(null != invoce_type && invoce_type==0){//公司
            invoce_name = "公司";
        }
        map.put("order_invoce_type", invoce_name);//发票类型
        map.put("order_invoce_info", o.getInvoiceInfo()==null?"":o.getInvoiceInfo());//发票抬头
        
        map.put("order_number", o.getOrderNumber());//订单编号
        map.put("order_payment_type", o.getOrderPayment()==null ?"":o.getOrderPayment().getPayType()==null ?"":o.getOrderPayment().getPayType().getName()==null ?"":o.getOrderPayment().getPayType().getName());//支付方式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
        String d = sdf.format(o.getCreatedAt());
        map.put("order_createTime", d);//订单日期
        map.put("order_status", o.getStatus()==null?"":o.getStatus()+"");
        
        List<OrderGood> olist = o.getOrderGoodsList();
        List<Object> newObjList = new ArrayList<Object>();
        Map<String, Object> omap = null;
        if (olist.size() > 0) {
            for (OrderGood od : olist) {
                omap = new HashMap<String, Object>();
                omap.put("good_id", od.getGood() == null ? "" : od.getGood().getId()==null?"":od.getGood().getId().toString());
                omap.put("good_price", od.getPrice() == null ? "" : od.getPrice().toString());
                omap.put("good_batch_price",od.getGood() == null ? "" : od.getGood().getPurchasePrice() == null ? "" : od.getGood().getPurchasePrice()+"");
                omap.put("good_num", od.getQuantity() == null ? "" : od.getQuantity().toString());
                omap.put("good_name", od.getGood() == null ? "" : od.getGood().getTitle()== null ? "" : od.getGood().getTitle());
                omap.put("good_brand", od.getGood() == null ? "" : od.getGood().getGoodsBrand() == null ? "" : od.getGood().getGoodsBrand().getName());
                omap.put("good_channel", od.getPayChannel() == null ? "" : od.getPayChannel().getName()==null?"":od.getPayChannel().getName());
                String good_logo = "";
                if(null !=od.getGood()){
                    Good g = od.getGood();
                    if(g.getPicsList().size()>0){
                        GoodsPicture gp  = g.getPicsList().get(0);
                        good_logo = gp.getUrlPath()==null?"":gp.getUrlPath();
                    }
                }
                omap.put("good_logo", good_logo);
                newObjList.add(omap);
            }
        }
        List<Terminal> terminals = orderMapper.getTerminsla(id);
        StringBuffer sb = new StringBuffer();
        for(Terminal t:terminals){
            sb.append(t.getSerialNum()+",");
        }
        map.put("terminals", sb.toString());
        map.put("order_goodsList", newObjList);
        MyOrderReq myOrderReq = new MyOrderReq();
        myOrderReq.setId(id);
        List<Map<String,Object>> list = orderMapper.findTraceById(myOrderReq);
        map.put("comments", OrderUtils.getTraceByVoId(myOrderReq, list));
//        obj_list.add(map);
        return map;
    }
    
    /**
     * 代购详情
     * @param id
     * @return
     * @throws ParseException
     */
    public Object getProxyById(Integer id) throws ParseException {
        Order o = orderMapper.getProxyById(id);
        if(null == o){
            return "-1";
        }
        List<Object> obj_list = new ArrayList<Object>();
        Map<String,Object> map = new LinkedHashMap<String, Object>();
        map.put("order_id", o.getId()==null ?"":o.getId().toString());
        map.put("order_number", o.getOrderNumber());//订单编号
        map.put("order_payment_type", o.getOrderPayment()==null ?"":o.getOrderPayment().getPayType().getName());//支付方式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
        String d = sdf.format(o.getCreatedAt());
        map.put("order_createTime", d);//订单日期
        map.put("order_status", o.getStatus()==null?"":o.getStatus()+"");
        map.put("order_totalNum", o.getTotalQuantity() == null ? "" : o.getTotalQuantity().toString());// 订单总件数
        map.put("order_totalPrice", o.getActualPrice()==null?"":o.getActualPrice()+"");
        map.put("order_psf", "0");//配送费
        map.put("order_receiver", o.getCustomerAddress()==null ?"":o.getCustomerAddress().getReceiver()==null ?"":o.getCustomerAddress().getReceiver());
        map.put("order_address", o.getCustomerAddress()==null ?"":o.getCustomerAddress().getAddress()==null ?"":o.getCustomerAddress().getAddress());
        map.put("order_receiver_phone", o.getCustomerAddress()==null ?"":o.getCustomerAddress().getMoblephone()==null ?"":o.getCustomerAddress().getMoblephone());
        map.put("order_comment", o.getComment()==null?"":o.getComment());//留言
        Integer invoce_type = o.getInvoiceType();
        String invoce_name ="";
        if(null != invoce_type && invoce_type==1){//个人
            invoce_name = "个人";
        }else if(null != invoce_type && invoce_type==0){//公司
            invoce_name = "公司";
        }
        map.put("order_invoce_type", invoce_name);//发票类型
        map.put("order_invoce_info", o.getInvoiceInfo()==null?"":o.getInvoiceInfo());//发票抬头
        List<OrderGood> olist = o.getOrderGoodsList();
        List<Object> newObjList = new ArrayList<Object>();
        Map<String, Object> omap = null;
        if (olist.size() > 0) {
            for (OrderGood od : olist) {
                omap = new HashMap<String, Object>();
                omap.put("good_id", od.getGood() == null ? "" : od.getGood().getId().toString());
                omap.put("good_price", od.getGood() == null ? "" : od.getGood().getRetailPrice()+"");
                omap.put("good_num", od.getQuantity() == null ? "" : od.getQuantity().toString());
                omap.put("good_name", od.getGood() == null ? "" : od.getGood().getTitle());
                omap.put("good_brand", od.getGood() == null ? "" : od.getGood().getGoodsBrand() == null ? "" : od.getGood().getGoodsBrand().getName());
                omap.put("good_channel", od.getPayChannel() == null ? "" : od.getPayChannel().getName()== null ? "" : od.getPayChannel().getName());
                String good_logo = "";
                if(null !=od.getGood()){
                    Good g = od.getGood();
                    if(g.getPicsList().size()>0){
                        GoodsPicture gp  = g.getPicsList().get(0);
                        good_logo = gp.getUrlPath();
                    }
                }
                omap.put("good_logo", good_logo);
                newObjList.add(omap);
            }
        }
        map.put("order_goodsList", newObjList);
        List<Terminal> terminals = orderMapper.getTerminsla(id);
        StringBuffer sb = new StringBuffer();
        for(Terminal t:terminals){
            sb.append(t.getSerialNum()+" ");
        }
        map.put("terminals", sb.toString());
        MyOrderReq myOrderReq = new MyOrderReq();
        myOrderReq.setId(id);
        List<Map<String,Object>> list = orderMapper.findTraceById(myOrderReq);
        map.put("comments", OrderUtils.getTraceByVoId(myOrderReq, list));
        obj_list.add(map);
        return obj_list;
    }

    public int cancelMyOrder(MyOrderReq myOrderReq) {
        myOrderReq.setOrderStatus(OrderStatus.CANCEL);
        int i = orderMapper.cancelMyOrder(myOrderReq);
        return i;
    }

    public void comment(MyOrderReq myOrderReq) {
        myOrderReq.setOrderStatus(OrderStatus.EVALUATED);
        orderMapper.comment(myOrderReq);
    }

}
