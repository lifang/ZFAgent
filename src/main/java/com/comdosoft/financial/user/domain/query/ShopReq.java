package com.comdosoft.financial.user.domain.query;

public class ShopReq {
    
    
     private int id;
     private int goodId;
     private int quantity;
     private int paychannelId;
     private int orderType;
     private int agentId;
     
     //{"id":1,"goodId":2,"customerId":2,"quantity":2,"paychannelId":2}
     
    public int getId() {
        return id;
    }
    public int getAgentId() {
        return agentId;
    }
    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getGoodId() {
        return goodId;
    }
    public void setGoodId(int goodId) {
        this.goodId = goodId;
    }

    public int getQuantity() {
        return quantity==0?1:quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public int getPaychannelId() {
        return paychannelId;
    }
    public void setPaychannelId(int paychannelId) {
        this.paychannelId = paychannelId;
    }
    public int getOrderType() {
        return orderType;
    }
    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }
    
     
     

}
