package com.xknower.consume.service.impl;

import com.xknower.consume.module.IMsgMonitor;
import com.xknower.consume.module.IMsgMonitorAdapter;
import com.xknower.consume.service.impl.module.DefaultMsgMonitor;
import com.xknower.queue.service.impl.module.Msg;

import java.util.ArrayList;
import java.util.List;

/**
 * 默认消息(监控)处理适配器
 *
 * @author xknower
 */
public class DefaultMsgMonitorAdapter extends IMsgMonitorAdapter {

    public DefaultMsgMonitorAdapter() {
        List<IMsgMonitor> list = new ArrayList();
        list.add(new DefaultMsgMonitor());
        init(Msg.class, list);
    }

//    /**
//     * 初始化相关处理
//     *
//     * @param msgClass        消息类 (Msg.class)
//     * @param msgMonitorClass 消息处理器, Monitor 实现基础类 (MsgMonitor.class)
//     */
//    private void init(Class msgClass, Class<? extends IMsgMonitor> msgMonitorClass) {
//        init(msgClass, new ArrayList<IMsgMonitor>(
//                applicationContext
//                        .getBeansOfType(msgMonitorClass)
//                        .values()
//        ));
//    }

//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        this.applicationContext = applicationContext;
//
//        // 初始化 Msg.class 消息处理器 IMsgMonitor.class
//        init(Msg.class, IMsgMonitor.class);
//    }
}
