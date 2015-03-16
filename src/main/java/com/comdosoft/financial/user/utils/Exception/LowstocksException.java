package com.comdosoft.financial.user.utils.Exception;

@SuppressWarnings("serial")
public class LowstocksException extends RuntimeException{

    public LowstocksException(String msg)  
    {  
        super(msg);  
    }  
}
