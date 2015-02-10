package com.comdosoft.financial.user.domain.query;

public class CartReq {
    
    
     private int id;
     private int goodId;
     private int customerId;
     private int quantity;
     private int paychannelId;
     
     //{"id":1,"goodId":2,"customerId":2,"quantity":2,"paychannelId":2}
     
    public int getId() {
        return id;
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
    public int getCustomerId() {
        return customerId;
    }
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    public int getQuantity() {
        return quantity;
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
     
     

}
