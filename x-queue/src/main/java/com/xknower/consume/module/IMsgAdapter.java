package com.xknower.consume.module;

import com.xknower.queue.module.IMsg;

/**
 * 消息处理适配器
 *
 * @author xknower
 */
public interface IMsgAdapter {

    /**
     * 消息适配执行器
     *
     * @param msg
     */
    void execute(IMsg msg);
}
