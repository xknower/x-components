package com.xknower.biz.msg.module.queue;

import com.xknower.biz.msg.module.T808Msg;
import com.xknower.biz.msg.module.queue.handler.DefaultT808MsgHandler;
import com.xknower.consume.module.IMsgHandler;
import com.xknower.consume.module.IMsgHandlerAdapter;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 808 消息处理适配器, 及该消息协议处理器初始化
 *
 * @author xknower
 */
@Component
public class T808MsgAdapter extends IMsgHandlerAdapter implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    private void init(Class msgClass, Class<? extends IMsgHandler> msgHandlerClass, Class<? extends IMsgHandler> defaultMsgHandlerClass) {
        Map<Serializable, IMsgHandler> map = new HashMap<>();

        //
        applicationContext
                .getBeansOfType(msgHandlerClass)
                .forEach((k, v) -> map.put(v.support(), v));

        //
        init(msgClass, map, applicationContext.getBean(defaultMsgHandlerClass));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;

        // 808协议, 消息处理器, 初始化
        init(T808Msg.class, T808MsgHandler.class, DefaultT808MsgHandler.class);
    }
}
