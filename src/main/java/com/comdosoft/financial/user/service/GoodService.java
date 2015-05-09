package com.comdosoft.financial.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.comdosoft.financial.user.domain.query.PosReq;
import com.comdosoft.financial.user.mapper.trades.record.TradeRecordMapper;
import com.comdosoft.financial.user.mapper.zhangfu.CommentMapper;
import com.comdosoft.financial.user.mapper.zhangfu.GoodMapper;
import com.comdosoft.financial.user.mapper.zhangfu.StockMapper;
import com.comdosoft.financial.user.mapper.zhangfu.SysconfigMapper;
import com.comdosoft.financial.user.utils.SysUtils;

@Service
public class GoodService {

    @Autowired
    private GoodMapper goodMapper;
    
    @Autowired
    private CommentMapper cMapper;
    
    @Autowired
    private PayChannelService pcService;
    
    @Autowired
    private SysconfigMapper sysconfigMapper;
    
    @Autowired
    private StockMapper stockMapper;
    
    @Autowired
    private TradeRecordMapper tradeRecordMapper;

    @Value("${filePath}")
    private String filePath;
    
    public Map<String,Object> getGoodsList(PosReq posreq) {
        if(posreq.getCityId()!=0){
            posreq.setShengId(goodMapper.getShengId(posreq.getCityId()));
        }
        Map<String,Object> result=new HashMap<String, Object>();
        List<Map<String, Object>> list = goodMapper.getGoodsList(posreq);
        int total=goodMapper.getGoodsTotal(posreq);
        for (Map<String, Object> map : list) {
            int id = SysUtils.Object2int(map.get("id"));
            // 支付通道
            posreq.setGoodId(id);
            List<Map<String, Object>> payChannelList = goodMapper.getPayChannelListByGoodId(posreq);
            int openprice=0;
            if (null != payChannelList && payChannelList.size() > 0) {
                map.put("pay_channe", payChannelList.get(0).get("name"));
                openprice=SysUtils.Object2int(payChannelList.get(0).get("opening_cost")); 
            }
            if(1==posreq.getType()){
                map.put("purchase_price", SysUtils.Object2int(map.get("purchase_price"))+openprice);
            }
            map.put("retail_price", SysUtils.Object2int(map.get("retail_price"))+openprice);
            // 图片
            List<String> goodPics = goodMapper.getgoodPics(id);
            if (null != goodPics && goodPics.size() > 0) {
                map.put("url_path",filePath+ goodPics.get(0));
            }
        }
        result.put("list", list);
        result.put("total",total);
        return result;
    }

    public Map<String, Object> getGoodInfo(PosReq posreq) {
        if(posreq.getCityId()!=0){
            posreq.setShengId(goodMapper.getShengId(posreq.getCityId()));
        }
        Map<String, Object> goodInfoMap = null;
        // 商品信息
        Map<String, Object> goodinfo = goodMapper.getGoodById(posreq.getGoodId());
        int id = SysUtils.Object2int(goodinfo.get("id"));
        if(1==posreq.getType()){
            goodinfo.put("purchase_price", setPurchasePrice(
                    posreq.getAgentId(),id,SysUtils.Object2int(goodinfo.get("purchase_price")),
                    SysUtils.Object2int(goodinfo.get("floor_price"))));
        }
        if (id > 0) {
            goodInfoMap = new HashMap<String, Object>();
            goodInfoMap.put("goodinfo", goodinfo);
            // 支付通道
            List<Map<String, Object>> payChannelList = goodMapper.getPayChannelListByGoodId(posreq);
            if (null != payChannelList && payChannelList.size() > 0) {
                goodInfoMap.put("payChannelList", payChannelList);
                int pcid=SysUtils.Object2int(payChannelList.get(0).get("id"));
                goodInfoMap.put("paychannelinfo",pcService.payChannelInfo(pcid));
            }
            // 图片
            List<String> goodPics = goodMapper.getgoodPics(posreq.getGoodId());
            if(goodPics!=null&&goodPics.size()>0){
                for (int i = 0; i < goodPics.size(); i++) {
                    goodPics.set(i, filePath+ goodPics.get(i));
                }
            }
            goodInfoMap.put("goodPics", goodPics);
            // 评论数
            int commentsCount = cMapper.getCommentCount(posreq.getGoodId());
            goodInfoMap.put("commentsCount", commentsCount);
            // 生产厂家
            int factoryId = SysUtils.Object2int(goodinfo.get("factory_id"));
            if (factoryId > 0) {
                Map<String, Object> factoryMap = goodMapper.getFactoryById(factoryId);
                if(factoryMap!=null){
                    factoryMap.put("logo_file_path", filePath+factoryMap.get("logo_file_path"));
                    goodInfoMap.put("factory", factoryMap);
                }
                
            }
            List<Map<String, Object>> relativeShopList  = goodMapper.getRelativeShopListByGoodId(posreq);
            if (null != relativeShopList && relativeShopList.size() > 0) {
               for (Map<String, Object> map : relativeShopList) {
                   // 图片
                   List<String> goodPics2 = goodMapper.getgoodPics( SysUtils.Object2int(map.get("id")));
                   if (null != goodPics2 && goodPics2.size() > 0) {
                       map.put("url_path", filePath+goodPics2.get(0));
                   }
                   int openprice=0;
                   int googid=SysUtils.Object2int(map.get("id"));
                   posreq.setGoodId(googid);
                   List<Map<String, Object>> pclist = goodMapper.getPayChannelListByGoodId(posreq);
                   if (null != pclist && pclist.size() > 0) {
                       openprice=SysUtils.Object2int(pclist.get(0).get("opening_cost")); 
                   }
                   if(1==posreq.getType()){
                       map.put("retail_price",setPurchasePrice(posreq.getAgentId(),googid,SysUtils.Object2int(map.get("purchase_price")),SysUtils.Object2int(map.get("floor_price")))+openprice);
                   }else{
                       map.put("retail_price", SysUtils.Object2int(map.get("retail_price"))+openprice);
                   }
                   
               }
            }
            goodInfoMap.put("relativeShopList",relativeShopList);
        }
        return goodInfoMap;
    }
    
    

    public Map<String, Object> getSearchCondition(PosReq posreq) {
        if(posreq.getCityId()!=0){
            posreq.setShengId(goodMapper.getShengId(posreq.getCityId()));
        }
        Map<String, Object> map = new HashMap<String, Object>();
        List<Map<String, Object>> list1 = goodMapper.getBrands_ids();
        List<Map<String, Object>> list2 = goodMapper.getFartherCategorys();
        if (null != list2 && list2.size() > 0) {
            List<Map<String, Object>> list2son = null;
            for (Map<String, Object> map2 : list2) {
                list2son = goodMapper.getSonCategorys(SysUtils.Object2int(map2.get("id")));
                if(null != list2son && list2son.size() > 0){
                    map2.put("son", list2son);
                }
            }
        }
        List<Map<String, Object>> list3 = goodMapper.getPay_channel_ids(posreq);
        List<Map<String, Object>> list4 = goodMapper.getPay_card_ids();
        List<Map<String, Object>> list5 = goodMapper.getTrade_type_ids(posreq);
        List<Map<String, Object>> list6 = goodMapper.getSale_slip_ids();
        List<Map<String, Object>> list7 = goodMapper.getTDatesByCityId(posreq);
      //  List<Map<String, Object>> list8 = goodMapper.getWebCategorys();
        map.put("brands", list1);
        map.put("category", list2);
      //  map.put("webcategory", list8);
        map.put("pay_channel", list3);
        map.put("pay_card", list4);
        map.put("trade_type", list5);
        map.put("sale_slip", list6);
        map.put("tDate", list7);
        return map;
    }
    
    public int setPurchasePrice(int agentid,int goodId,int purchasePrice,int leasePrice){
        int hasBuyCount=goodMapper.getHasBuyCount(agentid,goodId);
        int totalMoney=0;//总交易流水金额
        String code=stockMapper.getAgentCode(agentid);
        if(code!=null&&code.length()>2){
            List<Integer> agentids=stockMapper.getAgents(code);
            if(agentids!=null&&agentids.size()>0){
                for (int id : agentids) {
                    totalMoney+=tradeRecordMapper.getTradeRecordTotalByAgentId(id);
                }
            }
        }
        Map<String,Object> map1=sysconfigMapper.getValue("shopcount");
        Map<String,Object> map2=sysconfigMapper.getValue("totalmoney");
        if(hasBuyCount>=SysUtils.Object2int(map1.get("value"))){
            purchasePrice=purchasePrice*(1000-SysUtils.Object2int(map1.get("remark")))/1000;
        }
        if(totalMoney>=SysUtils.Object2int(map2.get("value"))){
            purchasePrice=purchasePrice*(1000-SysUtils.Object2int(map2.get("remark")))/1000;
        }
        return purchasePrice>leasePrice?purchasePrice:leasePrice;
    }

    public String categorys(int category) {
        List<Integer> son = goodMapper.getSonCategoryIds(category);
        StringBuilder sb = new StringBuilder();
        sb.append("("+category+ ",");
        if (son != null && son.size() > 0) {
            for (Integer ii : son) {
                sb.append(ii + ",");
            }
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(")");
        return sb.toString();
    }
}
