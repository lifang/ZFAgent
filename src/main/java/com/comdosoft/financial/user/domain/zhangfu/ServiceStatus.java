package com.comdosoft.financial.user.domain.zhangfu;

/**
 * 
 * 代理商售后状态<br>
 * <功能描述>
 *
 */
public enum ServiceStatus {
    /**
     * 待处理
     */
    UNPAID(1,"待处理"),
    /**
     * 待发回
     */
    PAID(2,"处理中"),
 
    /**
     * 处理完成
     */
    EVALUATED(3,"处理完成"),
    /**
     * 已取消
     */
    CANCEL(4,"已取消");

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
    private ServiceStatus(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
    
    public static String getName(int code){
        String status_name = null;
        for (ServiceStatus s : ServiceStatus.values()){  
            if(s.getCode() == code){
                status_name = s.getName();
            }
        }
        return status_name;
    }
}
