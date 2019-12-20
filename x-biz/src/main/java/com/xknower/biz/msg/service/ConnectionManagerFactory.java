package com.xknower.biz.msg.service;

import com.xknower.thrid.netty.service.AbsConnectionManager;
import com.xknower.thrid.netty.service.AbsConnectionManagerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 连接管理工厂类 - 根据消息类型提供对应的连接管理器
 *
 * @author xknower
 */
public class ConnectionManagerFactory extends AbsConnectionManagerFactory implements ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        applicationContext
                .getBeansOfType(AbsConnectionManager.class)
                .forEach((k, v) -> init(v.support(), v));
    }
}
