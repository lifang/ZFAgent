package com.comdosoft.financial.user.domain.query;

public class PrepareGoodReq {

    private int id;
    
    private int customer_id;
    
    private int agents_id;

    private int son_agents_id;
    
    private int good_id;

    private int paychannel_id;
    
    private String[] serial_nums;

    private String startTime;
    
    private String endTime;
    
    //--------
    private int quantity;
    
    private String serial_num;
    
    private String terminal_list;
    
    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getGood_id() {
        return good_id;
    }

    public void setGood_id(int good_id) {
        this.good_id = good_id;
    }

    public int getPaychannel_id() {
        return paychannel_id;
    }

    public void setPaychannel_id(int paychannel_id) {
        this.paychannel_id = paychannel_id;
    }

 

    public String getTerminal_list() {
        return terminal_list;
    }

    public void setTerminal_list(String terminal_list) {
        this.terminal_list = terminal_list;
    }

    public String[] getSerial_nums() {
        return serial_nums;
    }

    public void setSerial_nums(String[] serial_nums) {
        this.serial_nums = serial_nums;
    }

    public String getSerial_num() {
        return serial_num;
    }

    public void setSerial_num(String serial_num) {
        this.serial_num = serial_num;
    }

    public int getSon_agents_id() {
        return son_agents_id;
    }

    public void setSon_agents_id(int son_agents_id) {
        this.son_agents_id = son_agents_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getAgents_id() {
        return agents_id;
    }

    public void setAgents_id(int agents_id) {
        this.agents_id = agents_id;
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
