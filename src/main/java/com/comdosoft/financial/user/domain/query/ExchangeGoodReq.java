package com.comdosoft.financial.user.domain.query;

public class ExchangeGoodReq {
    
    private int id;
    private String[] serial_nums;
    private int agents_id;
    private int son_agents_id;
    private int customer_id;
    private int from_agent_id;
    private int to_agent_id;
    private String startTime;
    private String endTime;
    
    //--------
    
    private String serial_num;
    
    private String terminal_list;
    
    private int quantity;
    
    
    
    

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getFrom_agent_id() {
        return from_agent_id;
    }

    public void setFrom_agent_id(int from_agent_id) {
        this.from_agent_id = from_agent_id;
    }

    public int getTo_agent_id() {
        return to_agent_id;
    }

    public void setTo_agent_id(int to_agent_id) {
        this.to_agent_id = to_agent_id;
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

    public int getAgents_id() {
        return agents_id;
    }

    public void setAgents_id(int agents_id) {
        this.agents_id = agents_id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    
}
