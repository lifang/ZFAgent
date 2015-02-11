package com.comdosoft.financial.user.utils;





/**
 * 参数校验类<br>
 * <功能描述>
 *
 * @author jwb 2015年2月11日
 *
 */
public class Param {

    public static String setDay(String day){
        if(day!=null&&day.length()==10){
            return day; 
        }else{
            return null;
        }
        
    }
}