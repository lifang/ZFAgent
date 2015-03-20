package com.comdosoft.financial.user.domain.query;


public class TradeReq {

    private int id;
    private int agentId;
    private int tradeTypeId;
    private int is_have_profit;
    
    private String terminalNumber="";
    private String startTime="";
    private String endTime="";
    private int sonagentId=0;

    
    
    private String code;
    private String agentIds;
    
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

    public int getTradeTypeId() {
        return tradeTypeId;
    }

    public void setTradeTypeId(int tradeTypeId) {
        this.tradeTypeId = tradeTypeId;
    }

    public String getTerminalNumber() {
        return terminalNumber;
    }

    public void setTerminalNumber(String terminalNumber) {
        this.terminalNumber = terminalNumber;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

 

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAgentIds() {
        return agentIds;
    }

    public void setAgentIds(String agentIds) {
        this.agentIds = agentIds;
    }

    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    public int getIs_have_profit() {
        return is_have_profit;
    }

    public void setIs_have_profit(int is_have_profit) {
        this.is_have_profit = is_have_profit;
    }

    public int getSonagentId() {
        return sonagentId;
    }

    public void setSonagentId(int sonagentId) {
        this.sonagentId = sonagentId;
    }
    
    

}
