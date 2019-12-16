package com.xknower.consume.module;

import com.xknower.queue.module.IMsg;

import java.io.Serializable;

/**
 * 消息消费Handler定义 (根据消息类型不同, 选择不同的Handler消费)
 * <p>
 * 一个消息被一个 Handler消息
 *
 * @author xknower
 */
public interface IMsgHandler<M extends IMsg> {

    /**
     * 消息类型标识符
     *
     * @return
     */
    Serializable support();

    /**
     * 消息适配执行器
     *
     * @param msg
     */
    void execute(M msg);
}
