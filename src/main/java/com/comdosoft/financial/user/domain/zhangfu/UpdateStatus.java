package com.comdosoft.financial.user.domain.zhangfu;

/**
 * 
 * 更新状态<br>
 * <功能描述>
 *
 * @author gch 2015年2月9日
 *
 */
public enum UpdateStatus {
    /**
     * 待处理
     */
    PENDING(1,"待处理"),
    /**
     * 处理中
     */
    PROCESS(2,"处理中"),
    /**
     * 处理完成
     */
    EVALUATED(4,"处理完成"),
    /**
     * 已取消
     */
    CANCEL(5,"已取消");
 

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
    private UpdateStatus(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
    
    public static String getName(int code){
        String status_name = null;
        for (UpdateStatus s : UpdateStatus.values()){  
            if(s.getCode() == code){
                status_name = s.getName();
            }
        }
        return status_name;
    }
}
