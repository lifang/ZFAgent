package com.comdosoft.financial.user.utils.unionpay;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.PropertyResourceConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.support.PropertiesLoaderSupport;
import org.springframework.stereotype.Service;

import com.unionpay.acp.sdk.SDKConfig;
import com.unionpay.acp.sdk.SecureUtil;

/**
 * 启动监听器
 *
 * @author weisong.hu
 */
@Service
public class StartupListener implements ApplicationListener<ContextRefreshedEvent> {
	
	/**
     * 日志记录器
     */
    private static final Logger logger = Logger.getLogger(StartupListener.class);
    
    private static Properties properties=new Properties();  
    private static void loadPropertyPlaceholderProperties(ApplicationContext abstractContext) {  
    	if(!properties.isEmpty()){
    		return;
    	}
    	try{  
            // get the names of BeanFactoryPostProcessor  
            String[] postProcessorNames = abstractContext  
                    .getBeanNamesForType(BeanFactoryPostProcessor.class,true,true);  
              
            for (String ppName : postProcessorNames) {  
                // get the specified BeanFactoryPostProcessor  
                BeanFactoryPostProcessor beanProcessor=  
                abstractContext.getBean(ppName, BeanFactoryPostProcessor.class);  
                // check whether the beanFactoryPostProcessor is   
                // instance of the PropertyResourceConfigurer  
                // if it is yes then do the process otherwise continue  
                if(beanProcessor instanceof PropertyResourceConfigurer){  
                    PropertyResourceConfigurer propertyResourceConfigurer=  
                            (PropertyResourceConfigurer) beanProcessor;  
                      
                    // get the method mergeProperties   
                    // in class PropertiesLoaderSupport  
                    Method mergeProperties=PropertiesLoaderSupport.class.  
                        getDeclaredMethod("mergeProperties");  
                    // get the props  
                    mergeProperties.setAccessible(true);  
                    Properties props=(Properties) mergeProperties.  
                    invoke(propertyResourceConfigurer);  
                      
                    // get the method convertProperties   
                    // in class PropertyResourceConfigurer  
                    Method convertProperties=PropertyResourceConfigurer.class.  
                    getDeclaredMethod("convertProperties", Properties.class);  
                    // convert properties  
                    convertProperties.setAccessible(true);  
                    convertProperties.invoke(propertyResourceConfigurer, props);  
                      
                    properties.putAll(props);  
                }  
            }  
              
        }catch(Exception e){  
            throw new RuntimeException(e);  
        }  
    }  
      
    public static String getProperty(String propertyName){
        return properties.getProperty(propertyName);  
    }   
    @Override
    public void onApplicationEvent(ContextRefreshedEvent evt) {
        if (evt.getApplicationContext().getParent() == null) {
        	String path = StartupListener.class.getResource("/config/").getPath();
        	loadPropertyPlaceholderProperties(evt.getApplicationContext());
        	String environment = getProperty("environment");
        	environment= null == environment ? "" : environment;
        	logger.debug("-----------------------------" + environment);
        	if("production".equals(environment)){
        		InputStream inputStream = null;
        		InputStreamReader inputStreamReader = null;
        		Properties properties = new Properties();
				try {
					inputStream = new FileInputStream(path + File.separator + "acp_sdk-production.properties");
					inputStreamReader = new InputStreamReader(inputStream,"UTF-8");
	        		properties.load(inputStreamReader);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					logger.error("银联初始化文件未找到",e);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					logger.error("银联初始化文件编码不支持",e);
				} catch (IOException e) {
					e.printStackTrace();
					logger.error("银联初始化文件读取错误",e);
				}finally{
					if(null != inputStream){
						try {
							inputStream.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if(null != inputStreamReader){
						try {
							inputStreamReader.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				SDKConfig.getConfig().loadProperties(properties);
        	}else{
        		SDKConfig.getConfig().loadPropertiesFromPath(path);
        	}
        	String signCertPwd = SDKConfig.getConfig().getSignCertPwd();
        	try {
				signCertPwd = new String(SecureUtil.base64Decode(signCertPwd.getBytes()));
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("------waring------------银联证书密码解密失败------------------");
			}
        	SDKConfig.getConfig().setSignCertPwd(signCertPwd);
        	logger.info("-----------------初始化银联支付配置信息-------------------");
        }
    }
}