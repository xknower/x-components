package com.xknower.consume.module;

import com.xknower.queue.module.IMsg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 消息(监控)处理器适配器 (一个消息被多个处理器处理)
 *
 * @author xknower
 */
public abstract class IMsgMonitorAdapter implements IMsgAdapter {

    /**
     * Class : 消息类型定义
     * <p>
     * <消息类型 , 消息类型相应的处理 Monitor>
     */
    private Map<Class, List<? extends IMsgMonitor>> msgMonitorMap = new HashMap<>();

    /**
     * 消息处理适配 (根据消息类, 获取消息监控处理映射器; 并调用所有监控处理映射器执行)
     *
     * @param msg 消息
     */
    @Override
    public void execute(IMsg msg) {
        List<? extends IMsgMonitor> iMsgMonitors = this.msgMonitorMap.get(msg.getClass());
        if (iMsgMonitors == null || iMsgMonitors.size() == 0) {
            // 没有获取到相应的消息监控处理映射
            return;
        }

        // 执行
        iMsgMonitors.forEach(m -> {
            try {
                (m).execute(msg);
            } catch (Exception e) {
                // 处理错误, 单一处理映射器处理错误不影响其他处理器
            }
        });
    }


    /**
     * @param msgClass
     * @param msgMonitorClass
     */
    protected void init(Class msgClass, List<? extends IMsgMonitor> msgMonitorClass) {
        msgMonitorMap.put(msgClass, new ArrayList<IMsgMonitor>(msgMonitorClass));
    }
}
