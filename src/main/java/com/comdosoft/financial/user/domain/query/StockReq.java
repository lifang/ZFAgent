package com.comdosoft.financial.user.domain.query;

public class StockReq {
    
    
    private int agentId;
    
    private int goodId;
    
    private int paychannelId;
    
    private String goodname;
    
    private String agentname;
    
    //-----
    
    private String code;

    public int getAgentsId() {
        return agentId;
    }

    public void setAgentsId(int agentId) {
        this.agentId = agentId;
    }

    public int getGoodId() {
        return goodId;
    }

    public void setGoodId(int goodId) {
        this.goodId = goodId;
    }

    public String getGoodname() {
        return goodname;
    }

    public void setGoodname(String goodname) {
        this.goodname = goodname.trim();
    }

    public int getPaychannelId() {
        return paychannelId;
    }

    public void setPaychannelId(int paychannelId) {
        this.paychannelId = paychannelId;
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
