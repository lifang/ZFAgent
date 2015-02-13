package com.comdosoft.financial.user.domain.query;


public class OrderReq {
    
    
    private int id;
    private String ordernumber;
    private int totalprice;
    private int totalcount;
    private int price;
    private int retail_price;
    
    private int agent_id;
    private int goodId;
    private int paychannelId;
    private int quantity;
    private int orderType;
    private int customerId;
    private int addressId;
    private String comment;
    private int is_need_invoice;
    private int invoice_type;
    private String invoice_info;
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
    public int getCustomerId() {
        return customerId;
    }
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
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
    public int getIs_need_invoice() {
        return is_need_invoice;
    }
    public void setIs_need_invoice(int is_need_invoice) {
        this.is_need_invoice = is_need_invoice;
    }
    public int getInvoice_type() {
        return invoice_type;
    }
    public void setInvoice_type(int invoice_type) {
        this.invoice_type = invoice_type;
    }
    public String getInvoice_info() {
        return invoice_info;
    }
    public void setInvoice_info(String invoice_info) {
        this.invoice_info = invoice_info;
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
    public int getRetail_price() {
        return retail_price;
    }
    public void setRetail_price(int retail_price) {
        this.retail_price = retail_price;
    }
    public int getOrderType() {
        return orderType;
    }
    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }
    public int getAgent_id() {
        return agent_id;
    }
    public void setAgent_id(int agent_id) {
        this.agent_id = agent_id;
    }
    
    
}
