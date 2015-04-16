package com.comdosoft.financial.user.domain.zhangfu;

public enum PayType {
    ALIPAY(1,"支付宝"),CHINAPAY(2,"银联"),CASH(3,"现金");
    private Integer code;
    private String name;
    /**  
     * 获取 name  
     * @return name
     */
    public String getName() {
        return name;
    }
    /**  
     * 设置 name  
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }
    /**  
     * 获取 code  
     * @return code
     */
    public Integer getCode() {
        return code;
    }
    /**  
     * 设置 code  
     * @param code
     */
    public void setCode(Integer code) {
        this.code = code;
    }
    private PayType(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
   

}
