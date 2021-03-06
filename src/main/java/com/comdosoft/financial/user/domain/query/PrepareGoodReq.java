package com.comdosoft.financial.user.domain.query;

public class PrepareGoodReq {

    private int id;
    
    private int customerId;
    
    private int agentId;

    private int sonAgentId;
    
    private int goodId;

    private int paychannelId;
    
    private String[] serialNums;

    private String startTime;
    
    private String endTime;
    
    //--------
    private int quantity;
    
    private String serialNum;
    
    private String terminalList;
    
    private int web;
    
    private String keys="";
    
    
    public String getKeys() {
        return keys;
    }

    public void setKeys(String keys) {
        this.keys = keys;
    }

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

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
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

    public int getGoodId() {
        return goodId;
    }

    public void setGoodId(int goodId) {
        this.goodId = goodId;
    }

    public int getPaychannelId() {
        return paychannelId;
    }

    public void setPaychannelId(int paychannelId) {
        this.paychannelId = paychannelId;
    }

    public String[] getSerialNums() {
        return serialNums;
    }

    public void setSerialNums(String[] serialNums) {
        this.serialNums = serialNums;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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
