package com.comdosoft.financial.user.domain.query;

/**
 * 交易流水请求参数实体<br>
 * <功能描述>
 *
 * @author Java-007 2015年2月10日
 *
 */
public class TradeRecordQuery {

    private Integer customerId;

    private Integer tradeTypeId;

    private String terminalNumber;

    private String startTime;

    private String endTime;

    private Integer page;

    private Integer rows;

    private Integer tradeRecordId;

    /**
     * @return the customerId
     */
    public Integer getCustomerId() {
        return customerId;
    }

    /**
     * @param customerId
     *            the customerId to set
     */
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    /**
     * @return the tradeTypeId
     */
    public Integer getTradeTypeId() {
        return tradeTypeId;
    }

    /**
     * @param tradeTypeId
     *            the tradeTypeId to set
     */
    public void setTradeTypeId(Integer tradeTypeId) {
        this.tradeTypeId = tradeTypeId;
    }

    /**
     * @return the terminalNumber
     */
    public String getTerminalNumber() {
        return terminalNumber;
    }

    /**
     * @param terminalNumber
     *            the terminalNumber to set
     */
    public void setTerminalNumber(String terminalNumber) {
        this.terminalNumber = terminalNumber;
    }

    /**
     * @return the startTime
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * @param startTime
     *            the startTime to set
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the endTime
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * @param endTime
     *            the endTime to set
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     * @return the page
     */
    public Integer getPage() {
        return page;
    }

    /**
     * @param page
     *            the page to set
     */
    public void setPage(Integer page) {
        this.page = page;
    }

    /**
     * @return the rows
     */
    public Integer getRows() {
        return rows;
    }

    /**
     * @param rows
     *            the rows to set
     */
    public void setRows(Integer rows) {
        this.rows = rows;
    }

    /**
     * @return the tradeRecordId
     */
    public Integer getTradeRecordId() {
        return tradeRecordId;
    }

    /**
     * @param tradeRecordId
     *            the tradeRecordId to set
     */
    public void setTradeRecordId(Integer tradeRecordId) {
        this.tradeRecordId = tradeRecordId;
    }

}