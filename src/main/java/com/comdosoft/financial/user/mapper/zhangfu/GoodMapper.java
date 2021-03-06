package com.comdosoft.financial.user.mapper.zhangfu;

import com.comdosoft.financial.user.domain.query.PosReq;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;


public interface GoodMapper {



    List<Map<String, Object>> getGoodsList(PosReq posreq);

    List<Map<String, Object>> getPayChannelListByGoodId(PosReq posreq);

    List<String> getgoodPics(int id);

    Map<String, Object> getGoodById(int goodId);
    
    Map<String, Object> getFactoryById(int factoryId);
    
    List<Map<String, Object>> getBrands_ids();

    List<Map<String, Object>> getFartherCategorys();
    
    List<Map<String, Object>> getSonCategorys(int id);

    List<Map<String, Object>> getPay_channel_ids(PosReq posreq);

    List<Map<String, Object>> getPay_card_ids();

    List<Map<String, Object>> getTrade_type_ids(PosReq posreq);

    List<Map<String, Object>> getSale_slip_ids();

    List<Map<String, Object>> getTDatesByCityId(PosReq posreq);

    List<Map<String, Object>> getRequireMaterial_pub(int pcid);

    List<Map<String, Object>> getRequireMaterial_pra(int pcid);

    List<Map<String, Object>> getTDatesByPayChannel(int pcid);

    List<String> getSupportArea(int pcid);

    List<Map<String, Object>> getOther_rate(int pcid);

    List<Map<String, Object>> getStandard_rates(int pcid);

    int getHasBuyCount(@Param("agentId")int agentId,@Param("goodId")int goodId);

    int getGoodsTotal(PosReq posreq);

    void upQuantity(PosReq posreq);

    List<Map<String, Object>> getWebCategorys();

    int getAdId(int customerId);

    int getShengId(int cityId);

    List<Map<String, Object>> getRelativeShopListByGoodId(PosReq posreq);

    List<Integer> getSonCategoryIds(int category);

    List<Map<String, Object>> getPicList(int goodId);
    
    List<Map<String, Object>> getPhonePicList(int goodId);
}