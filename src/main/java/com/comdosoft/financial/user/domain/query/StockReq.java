package com.comdosoft.financial.user.domain.query;

public class StockReq {
    
    
    private int agents_id;
    
    private int good_id;
    
    private int paychannel_id;
    
    private String goodname;
    
    private String agentname;
    
    //-----
    
    private String code;

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
        this.goodname = goodname.trim();
    }

    public int getPaychannel_id() {
        return paychannel_id;
    }

    public void setPaychannel_id(int paychannel_id) {
        this.paychannel_id = paychannel_id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAgentname() {
        return agentname;
    }

    public void setAgentname(String agentname) {
        this.agentname = agentname.trim();
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
