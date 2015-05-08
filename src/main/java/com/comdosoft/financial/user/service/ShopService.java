package com.comdosoft.financial.user.service;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.comdosoft.financial.user.domain.query.OrderReq;
import com.comdosoft.financial.user.domain.query.ShopReq;
import com.comdosoft.financial.user.mapper.zhangfu.GoodMapper;
import com.comdosoft.financial.user.mapper.zhangfu.ShopMapper;
import com.comdosoft.financial.user.utils.SysUtils;
import com.comdosoft.financial.user.utils.unionpay.UnionpayService;

@Service
public class ShopService {

    @Autowired
    private ShopMapper shopMapper;
    
    @Autowired
    private GoodMapper goodMapper;
    
    @Autowired
    private GoodService goodService ;
    
    @Autowired
    private OrderService orderService ;
    
    @Value("${filePath}")
    private String filePath;
    
    private static SimpleDateFormat sdf_simple = new SimpleDateFormat("yyyyMMddHHmmss"); 

    public Map<String, Object> getShop(ShopReq shopReq) {
        Map<String, Object> map=null;
        if(4==shopReq.getOrderType()){
            map=shopMapper.getLeaseOne(shopReq);
        }else if(3==shopReq.getOrderType()){
            map=shopMapper.getShopOne(shopReq);
        }else if(5==shopReq.getOrderType()){
            map=shopMapper.getPurchaseOne(shopReq);
            map.put("price", goodService.setPurchasePrice(
                    shopReq.getAgentId(),shopReq.getGoodId(),SysUtils.Object2int(map.get("price")),
                    SysUtils.Object2int(map.get("floor_price"))));
        }
        if(map==null){
            return map;
        }
        int goodId =Integer.valueOf(""+map.get("goodId")); 
        //图片
        List<String> goodPics=goodMapper.getgoodPics(goodId);
        if(null!=goodPics&&goodPics.size()>0){
            map.put("url_path",filePath+goodPics.get(0));
        }
        return map;
    }

    public Map<String, Object> payOrder(ShopReq shopReq) {
        Map<String, Object> map = shopMapper.getPayOrder(shopReq);
        String paytype = String.valueOf(map.get("paytype"));
        //如未完成支付,调用第三方支付交易状态查询接口更新订单状态
        if("0".equals(paytype)){
        	int _paytype = shopReq.getPayway();
        	if(2 == _paytype){
        		String orderId = (String) map.get("order_number");
        		Date created_at = (Date) map.get("created_at");
        		String txnTime = sdf_simple.format(created_at);
        		try{
	        		Map<String,String> queryResult =UnionpayService.query(orderId, txnTime);
	        		if(null != queryResult && "00".equals(queryResult.get("respCode"))){
	        			OrderReq orderreq =new OrderReq();
	        			//必须存在订单编号
	        			orderreq.setOrdernumber(orderId);
	        			orderreq.setType(_paytype);
	        			orderService.payFinish(orderreq);
	        			map = shopMapper.getPayOrder(shopReq);
	        		}
        		}catch(Exception e){
        			e.printStackTrace();
        		}
        	}
        }
        map.put("good", shopMapper.getPayOrderGood(shopReq));
        return map;
    }

  
}
