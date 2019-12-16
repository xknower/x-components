package com.xknower.consume.module;

import com.xknower.queue.module.IMsg;

/**
 * 消息消费 Monitor 定义 (消息被所有 Monitor 处理)
 * <p>
 * 一个消息类型被不同的 Monitor 消费
 *
 * @author xknower
 */
public interface IMsgMonitor<M extends IMsg> {

    /**
     * 消息适配执行器
     *
     * @param msg
     */
    void execute(M msg);
}
