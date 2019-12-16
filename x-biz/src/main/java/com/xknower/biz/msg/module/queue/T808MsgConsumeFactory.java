package com.xknower.biz.msg.module.queue;

import com.xknower.consume.module.IMsgHandlerAdapter;
import com.xknower.consume.service.AbsMsgConsumeFactory;
import com.xknower.queue.service.impl.MemoryMsgQueueFactory;

/**
 * @author xknower
 */
public class T808MsgConsumeFactory extends AbsMsgConsumeFactory {

    /**
     * 初始化消费工厂, 使用内存队列工厂
     *
     * @param msgHandler        消费适配器
     * @param messageQueueCount 消费队列数据
     * @param messageQueueSize  队列最大消息数
     */
    public T808MsgConsumeFactory(IMsgHandlerAdapter msgHandler, int messageQueueCount, int messageQueueSize) {
        super(new MemoryMsgQueueFactory(messageQueueCount, messageQueueSize), msgHandler);
        //
        this.init();
    }
}
