package com.comdosoft.financial.user.domain.query;


public class PosReq {

    private int goodId;

    
    private int cityId;
    private int shengId;
    
    private int agentId;
    
    private int type; //1 批购
    /**
     * 0.按照上架时间倒序排列商品 1.按照销售优先倒序排列商品 2.按照价格倒序排列商品 3.按照价格正序排列商品 4.按照评分最高倒序排列商品
     */
    private int orderType;

    /**
     * POS品牌 brandsId ------>good_brands
     * 
     * POS机类型 posCategoryId ---->posCategories
     * 
     * 支付通道 goods_payChannels---> payChannels
     * 
     * 支付卡类型 goodCardTypes --->dictionaryCardTypes
     * 
     * 
     * 支付交易类型 goods_payChannels ---> payChannels---> supportTradeTypes --->dictionaryTradeTypes
     * 
     * 签购单打印方式 sign_order_wayId --->dictionarySign_order_ways
     * 
     * 对账日期 goods_payChannels---> payChannels---> payChannel_billingCycles--->dictionary_billingCycles
     */
    private int[] brandsId;// POS品牌
    private String brandsIds;
    private int[] category;// POS机类型
    private String categorys;
    private int[] payChannelId;// 支付通道
    private String payChannelIds;
    private int[] payCardId;// 支付卡类型
    private String payCardIds;
    private int[] tradeTypeId;// 支持交易类型
    private String tradeTypeIds;
    private int[] saleSlipId;// 签单方式
    private String saleSlipIds;
    private int[] tDate;// 对账日期
    private String tDates;

    private int hasLease;// 只包含租贷 1是
    private double minPrice;
    private double maxPrice;

    private String keys;
    

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

    public int[] getBrandsId() {
        return brandsId;
    }

    public void setBrandsId(int[] brandsId) {
        this.brandsId = brandsId;
    }

    public String getBrandsIds() {
        return brandsIds;
    }

    public void setBrandsIds(String brandsIds) {
        this.brandsIds = brandsIds;
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

    public int[] getPayChannelId() {
        return payChannelId;
    }

    public void setPayChannelId(int[] payChannelId) {
        this.payChannelId = payChannelId;
    }

    public String getPayChannelIds() {
        return payChannelIds;
    }

    public void setPayChannelIds(String payChannelIds) {
        this.payChannelIds = payChannelIds;
    }

    public int[] getPayCardId() {
        return payCardId;
    }

    public void setPayCardId(int[] payCardId) {
        this.payCardId = payCardId;
    }

    public String getPayCardIds() {
        return payCardIds;
    }

    public void setPayCardIds(String payCardIds) {
        this.payCardIds = payCardIds;
    }

    public int[] getTradeTypeId() {
        return tradeTypeId;
    }

    public void setTradeTypeId(int[] tradeTypeId) {
        this.tradeTypeId = tradeTypeId;
    }

    public String getTradeTypeIds() {
        return tradeTypeIds;
    }

    public void setTradeTypeIds(String tradeTypeIds) {
        this.tradeTypeIds = tradeTypeIds;
    }

    public int[] getSaleSlipId() {
        return saleSlipId;
    }

    public void setSaleSlipId(int[] saleSlipId) {
        this.saleSlipId = saleSlipId;
    }

    public String getSaleSlipIds() {
        return saleSlipIds;
    }

    public void setSaleSlipIds(String saleSlipIds) {
        this.saleSlipIds = saleSlipIds;
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

   

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getHasLease() {
        return hasLease;
    }

    public void setHasLease(int hasLease) {
        this.hasLease = hasLease;
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


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getKeys() {
        return keys;
    }

    public void setKeys(String keys) {
        this.keys = keys;
    }

 
  

    public int getShengId() {
        return shengId;
    }

    public void setShengId(int shengId) {
        this.shengId = shengId;
    }

    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    public int getMinPricei() {
        return (int)minPrice*100;
    }

    public int getMaxPricei() {
        return (int)maxPrice*100;
    }
    
    private int page;
    private int rows;
    private int offset;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        if (rows <= 0) {
            rows = 10;
        }
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getOffset() {
        if (page > 0 && rows > 0) {
            offset = (page - 1) * rows;
        } else {
            offset = 0;
        }
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }


}
