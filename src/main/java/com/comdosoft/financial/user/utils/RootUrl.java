package com.comdosoft.financial.user.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RootUrl {

    public static String localpath;

    public static String urlpath;

    public static String filepath;

    
    public  String getLocalpath() {
        return localpath;
    }

    @Autowired
    public  void setLocalpath(@Value("${localpath}")String localpath) {
        RootUrl.localpath = localpath;
    }

    public  String getUrlpath() {
        return urlpath;
    }

    @Autowired
    public  void setUrlpath(@Value("${urlpath}")String urlpath) {
        RootUrl.urlpath = urlpath;
    }

    public  String getFilepath() {
        return filepath;
    }

    @Autowired
    public  void setFilepath(@Value("${filePath}")String filepath) {
        RootUrl.filepath = filepath;
    }
    
}
