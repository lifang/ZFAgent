package com.comdosoft.financial.user.domain.query;


public class OrderReq {
    
    
    private int id;
    private String ordernumber;
    private int totalprice;
    private int totalcount;
    private int price;
    private int retailPrice;
    
    private int agentId;
    private int creatid;
    private int belongId;
    private int goodId;
    private int paychannelId;
    private int quantity;
    private int orderType;
    private Integer customerId;
    private int addressId;
    private String comment;
    private int isNeedInvoice;
    private int invoiceType;
    private String invoiceInfo;
    
    private int front_money=0;
    /**
    {"quantity":2,"customerId":1,"addressId":1,"comment":"nihao",
    "is_need_invoice":0,"cartid":[1,2,3],"goodId":2,"paychannelId":2,"":}
    
    **/
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
   
    

    public Integer getCustomerId() {
        return customerId;
    }
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
    public int getBelongId() {
        return belongId;
    }
    public void setBelongId(int belongId) {
        this.belongId = belongId;
    }
    public int getAddressId() {
        return addressId;
    }
    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public int getGoodId() {
        return goodId;
    }
    public void setGoodId(int goodId) {
        this.goodId = goodId;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public String getOrdernumber() {
        return ordernumber;
    }
    public void setOrdernumber(String ordernumber) {
        this.ordernumber = ordernumber;
    }
    public int getTotalprice() {
        return totalprice;
    }
    public void setTotalprice(int totalprice) {
        this.totalprice = totalprice;
    }

   
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    
    public int getPaychannelId() {
        return paychannelId;
    }
    public void setPaychannelId(int paychannelId) {
        this.paychannelId = paychannelId;
    }
    public int getTotalcount() {
        return totalcount;
    }
    public void setTotalcount(int totalcount) {
        this.totalcount = totalcount;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public int getRetailPrice() {
        return retailPrice;
    }
    public void setRetailPrice(int retailPrice) {
        this.retailPrice = retailPrice;
    }
    public int getOrderType() {
        return orderType;
    }
    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }
    public int getAgentId() {
        return agentId;
    }
    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }
    public int getIsNeedInvoice() {
        return isNeedInvoice;
    }
    public void setIsNeedInvoice(int isNeedInvoice) {
        this.isNeedInvoice = isNeedInvoice;
    }
    public int getInvoiceType() {
        return invoiceType;
    }
    public void setInvoiceType(int invoiceType) {
        this.invoiceType = invoiceType;
    }
    public String getInvoiceInfo() {
        return invoiceInfo;
    }
    public void setInvoiceInfo(String invoiceInfo) {
        this.invoiceInfo = invoiceInfo;
    }
    public int getFront_money() {
        return front_money;
    }
    public void setFront_money(int front_money) {
        this.front_money = front_money;
    }
    public int getCreatid() {
        return creatid;
    }
    public void setCreatid(int creatid) {
        this.creatid = creatid;
    }
   
    
    
}
