package com.comdosoft.financial.user.domain.query;

public class StockReq {
    
    
    private int agents_id;
    
    private int good_id;
    
    private int paychannel_id;
    
    private String goodname;

    public int getAgents_id() {
        return agents_id;
    }

    public void setAgents_id(int agents_id) {
        this.agents_id = agents_id;
    }

    public int getGood_id() {
        return good_id;
    }

    public void setGood_id(int good_id) {
        this.good_id = good_id;
    }

    public String getGoodname() {
        return goodname;
    }

    public void setGoodname(String goodname) {
        this.goodname = goodname;
    }

    public int getPaychannel_id() {
        return paychannel_id;
    }

    public void setPaychannel_id(int paychannel_id) {
        this.paychannel_id = paychannel_id;
    }
    
    

   
    
     

}
