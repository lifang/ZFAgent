package com.comdosoft.financial.user.domain.zhangfu;

public class CommentsJson {
    private Integer customer_id;
    private Integer good_id;
    private Integer score;
    private String content;
    /**  
     * 获取 customer_id  
     * @return customer_id
     */
    public Integer getCustomer_id() {
        return customer_id;
    }
    /**  
     * 设置 customer_id  
     * @param customer_id
     */
    public void setCustomer_id(Integer customer_id) {
        this.customer_id = customer_id;
    }
    /**  
     * 获取 good_id  
     * @return good_id
     */
    public Integer getGood_id() {
        return good_id;
    }
    /**  
     * 设置 good_id  
     * @param good_id
     */
    public void setGood_id(Integer good_id) {
        this.good_id = good_id;
    }
    /**  
     * 获取 score  
     * @return score
     */
    public Integer getScore() {
        return score;
    }
    /**  
     * 设置 score  
     * @param score
     */
    public void setScore(Integer score) {
        this.score = score;
    }
    /**  
     * 获取 content  
     * @return content
     */
    public String getContent() {
        return content;
    }
    /**  
     * 设置 content  
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "CommentsJson [customer_id=" + customer_id + ", good_id=" + good_id + ", score=" + score + ", content=" + content + "]";
    }
    public CommentsJson(Integer customer_id, Integer good_id, Integer score, String content) {
        super();
        this.customer_id = customer_id;
        this.good_id = good_id;
        this.score = score;
        this.content = content;
    }
    public CommentsJson() {
        super();
        // TODO Auto-generated constructor stub
    }
   
    
}
