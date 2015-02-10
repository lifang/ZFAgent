package com.comdosoft.financial.user.domain.query;

import com.comdosoft.financial.user.domain.Paging;

public class PosReq {

    private int goodId;

    private int city_id;
    /**
     * 0.按照上架时间倒序排列商品 1.按照销售优先倒序排列商品 2.按照价格倒序排列商品 3.按照价格正序排列商品 4.按照评分最高倒序排列商品
     */
    private int orderType;

    /**
     * POS品牌 brands_id ------>good_brands
     * 
     * POS机类型 pos_category_id ---->pos_categories
     * 
     * 支付通道 goods_pay_channels---> pay_channels
     * 
     * 支付卡类型 good_card_types --->dictionary_card_types
     * 
     * 
     * 支付交易类型 goods_pay_channels ---> pay_channels---> support_trade_types --->dictionary_trade_types
     * 
     * 签购单打印方式 sign_order_way_id --->dictionary_sign_order_ways
     * 
     * 对账日期 goods_pay_channels---> pay_channels---> pay_channel_billing_cycles--->dictionary_billing_cycles
     */
    private int[] brands_id;// POS品牌
    private String brands_ids;
    private int[] category;// POS机类型
    private String categorys;
    private int[] pay_channel_id;// 支付通道
    private String pay_channel_ids;
    private int[] pay_card_id;// 支付卡类型
    private String pay_card_ids;
    private int[] trade_type_id;// 支持交易类型
    private String trade_type_ids;
    private int[] sale_slip_id;// 签单方式
    private String sale_slip_ids;
    private int[] tDate;// 对账日期
    private String tDates;

    private int has_purchase;// 只包含租贷 1是
    private double minPrice;
    private double maxPrice;

    private String keys;
    
    private Paging paging;

    public int getGoodId() {
        return goodId;
    }

    public void setGoodId(int goodId) {
        this.goodId = goodId;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public int[] getBrands_id() {
        return brands_id;
    }

    public void setBrands_id(int[] brands_id) {
        this.brands_id = brands_id;
    }

    

    public String getBrands_ids() {
        return brands_ids;
    }

    public void setBrands_ids(String brands_ids) {
        this.brands_ids = brands_ids;
    }
    public int[] getCategory() {
        return category;
    }

    public void setCategory(int[] category) {
        this.category = category;
    }

    public String getCategorys() {
        return categorys;
    }

    public void setCategorys(String categorys) {
        this.categorys = categorys;
    }

    public int[] getPay_channel_id() {
        return pay_channel_id;
    }

    public void setPay_channel_id(int[] pay_channel_id) {
        this.pay_channel_id = pay_channel_id;
    }

    public String getPay_channel_ids() {
        return pay_channel_ids;
    }

    public void setPay_channel_ids(String pay_channel_ids) {
        this.pay_channel_ids = pay_channel_ids;
    }

    public int[] getPay_card_id() {
        return pay_card_id;
    }

    public void setPay_card_id(int[] pay_card_id) {
        this.pay_card_id = pay_card_id;
    }

    public String getPay_card_ids() {
        return pay_card_ids;
    }

    public void setPay_card_ids(String pay_card_ids) {
        this.pay_card_ids = pay_card_ids;
    }

    public int[] getTrade_type_id() {
        return trade_type_id;
    }

    public void setTrade_type_id(int[] trade_type_id) {
        this.trade_type_id = trade_type_id;
    }

    public String getTrade_type_ids() {
        return trade_type_ids;
    }

    public void setTrade_type_ids(String trade_type_ids) {
        this.trade_type_ids = trade_type_ids;
    }

    public int[] getSale_slip_id() {
        return sale_slip_id;
    }

    public void setSale_slip_id(int[] sale_slip_id) {
        this.sale_slip_id = sale_slip_id;
    }

    public String getSale_slip_ids() {
        return sale_slip_ids;
    }

    public void setSale_slip_ids(String sale_slip_ids) {
        this.sale_slip_ids = sale_slip_ids;
    }

    public int[] gettDate() {
        return tDate;
    }

    public void settDate(int[] tDate) {
        this.tDate = tDate;
    }

    public String gettDates() {
        return tDates;
    }

    public void settDates(String tDates) {
        this.tDates = tDates;
    }

    public int getHas_purchase() {
        return has_purchase;
    }

    public void setHas_purchase(int has_purchase) {
        this.has_purchase = has_purchase;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Paging getPaging() {
        return paging;
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }

    public String getKeys() {
        return keys;
    }

    public void setKeys(String keys) {
        this.keys = keys;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public int getMinPricei() {
        return (int)minPrice*100;
    }

    public int getMaxPricei() {
        return (int)maxPrice*100;
    }

}
