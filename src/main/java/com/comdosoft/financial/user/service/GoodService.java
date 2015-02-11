package com.comdosoft.financial.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comdosoft.financial.user.domain.query.PosReq;
import com.comdosoft.financial.user.mapper.zhangfu.CommentMapper;
import com.comdosoft.financial.user.mapper.zhangfu.GoodMapper;
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

    public List<?> getGoodsList(PosReq posreq) {
        List<Map<String, Object>> list = goodMapper.getGoodsList(posreq);
        for (Map<String, Object> map : list) {
            if(1==posreq.getType()){
                map.put("purchase_price", setPurchasePrice(
                        posreq.getCustomer_id(),SysUtils.Object2int(map.get("purchase_price")),
                        SysUtils.Object2int(map.get("floor_price"))));
            }
            int id = Integer.valueOf("" + map.get("id"));
            // 支付通道
            posreq.setGoodId(id);
            List<Map<String, Object>> payChannelList = goodMapper.getPayChannelListByGoodId(posreq);
            if (null != payChannelList && payChannelList.size() > 0) {
                map.put("pay_channe", payChannelList.get(0).get("name"));
            }
            // 图片
            List<String> goodPics = goodMapper.getgoodPics(id);
            if (null != goodPics && goodPics.size() > 0) {
                map.put("url_path", goodPics.get(0));
            }
        }
        return list;
    }

    public Map<String, Object> getGoodInfo(PosReq posreq) {
        Map<String, Object> goodInfoMap = null;
        // 商品信息
        Map<String, Object> goodinfo = goodMapper.getGoodById(posreq.getGoodId());
        int id = SysUtils.Object2int("" + goodinfo.get("id"));
        if(1==posreq.getType()){
            goodinfo.put("purchase_price", setPurchasePrice(
                    posreq.getCustomer_id(),SysUtils.Object2int(goodinfo.get("purchase_price")),
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
            goodInfoMap.put("goodPics", goodPics);
            // 评论数
            int commentsCount = cMapper.getCommentCount(posreq.getGoodId());
            goodInfoMap.put("commentsCount", commentsCount);
            // 生产厂家
            int factoryId = SysUtils.Object2int(goodinfo.get("factory_id"));
            if (factoryId > 0) {
                Map<String, Object> factoryMap = goodMapper.getFactoryById(factoryId);
                goodInfoMap.put("factory", factoryMap);
            }
        }
        return goodInfoMap;
    }
    
    

    public Map<String, Object> getSearchCondition(PosReq posreq) {
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
        map.put("brands", list1);
        map.put("category", list2);
        map.put("pay_channel", list3);
        map.put("pay_card", list4);
        map.put("trade_type", list5);
        map.put("sale_slip", list6);
        map.put("tDate", list7);
        return map;
    }
    
    public int setPurchasePrice(int customerid,int purchasePrice,int leasePrice){
        int hasBuyCount=goodMapper.getHasBuyCount(customerid);
        int totalMoney=10000;//总交易流水金额
        Map<String,Object> map1=sysconfigMapper.getValue("shopcount");
        Map<String,Object> map2=sysconfigMapper.getValue("totalmoney");
        if(hasBuyCount>=SysUtils.Object2int(map1.get("value"))){
            purchasePrice=purchasePrice*(10000-SysUtils.Object2int(map1.get("remark")))/10000;
        }
        if(totalMoney>=SysUtils.Object2int(map2.get("value"))){
            purchasePrice=purchasePrice*(10000-SysUtils.Object2int(map2.get("remark")))/10000;
        }
        return purchasePrice>leasePrice?purchasePrice:leasePrice;
    }
}
