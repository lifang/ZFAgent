package com.comdosoft.financial.user.utils.unionpay;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.unionpay.acp.sdk.SDKConfig;

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
    @Override
    public void onApplicationEvent(ContextRefreshedEvent evt) {
        if (evt.getApplicationContext().getParent() == null) {
        	String path = StartupListener.class.getResource("/config/").getPath();
        	SDKConfig.getConfig().loadPropertiesFromPath(path);
        	logger.info("-----------------初始化银联支付配置信息-------------------");
        }
    }
}