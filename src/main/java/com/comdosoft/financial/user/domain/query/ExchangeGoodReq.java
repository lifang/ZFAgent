package com.comdosoft.financial.user.domain.query;

public class ExchangeGoodReq {
    
    private int id;
    private String[] serialNums;
    private int agentId;
    private int sonAgentId;
    private int customerId;
    private int fromAgentId;
    private int toAgentId;
    private String startTime;
    private String endTime;
    
    //--------
    
    private String serialNum;
    
    private String terminalList;
    
    private int quantity;
    
    private int web;
    
    

    

    public int getWeb() {
        return web;
    }

    public void setWeb(int web) {
        this.web = web;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String[] getSerialNums() {
        return serialNums;
    }

    public void setSerialNums(String[] serialNums) {
        this.serialNums = serialNums;
    }

    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    public int getSonAgentId() {
        return sonAgentId;
    }

    public void setSonAgentId(int sonAgentId) {
        this.sonAgentId = sonAgentId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getFromAgentId() {
        return fromAgentId;
    }

    public void setFromAgentId(int fromAgentId) {
        this.fromAgentId = fromAgentId;
    }

    public int getToAgentId() {
        return toAgentId;
    }

    public void setToAgentId(int toAgentId) {
        this.toAgentId = toAgentId;
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

    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    public String getTerminalList() {
        return terminalList;
    }

    public void setTerminalList(String terminalList) {
        this.terminalList = terminalList;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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
