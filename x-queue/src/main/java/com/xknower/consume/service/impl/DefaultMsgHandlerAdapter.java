package com.xknower.consume.service.impl;

import com.xknower.consume.module.IMsgHandlerAdapter;
import com.xknower.consume.service.impl.module.DefaultMsgHandler;
import com.xknower.queue.service.impl.module.Msg;

import java.util.HashMap;

/**
 * 默认消息处理适配器
 *
 * @author xknower
 */
public class DefaultMsgHandlerAdapter extends IMsgHandlerAdapter {

    public DefaultMsgHandlerAdapter() {
        //
        init(Msg.class, new HashMap(), new DefaultMsgHandler());
    }

//    private void init(Class msgClass, Class<? extends IMsgHandler> msgHandlerClass, Class<? extends IMsgHandler> defaultMsgHandlerClass) {
//        Map<Serializable, IMsgHandler> map = new HashMap<>();
//        applicationContext
//                .getBeansOfType(msgHandlerClass)
//                .forEach((k, v) -> map.put(v.support(), v));
//        init(msgClass, map, applicationContext.getBean(defaultMsgHandlerClass));
//    }

//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        this.applicationContext = applicationContext;
//
//        //
//        init(Msg.class, MsgHandler.class, DefaultMsgHandler.class);
//    }
}
