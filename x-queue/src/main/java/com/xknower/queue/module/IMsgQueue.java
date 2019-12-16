package com.xknower.queue.module;

import java.util.List;

/**
 * 消息队列定义
 *
 * @author xknower
 */
public interface IMsgQueue<T> {

    /**
     * 获取队列名
     *
     * @return 消息队列名称
     */
    String name();

    /**
     * 压入消息
     *
     * @param msg
     */
    void put(T msg);

    /**
     * 弹出消息 (一个)
     *
     * @return
     * @throws Exception
     */
    T pull() throws Exception;

    /**
     * 弹出消息 (一组)
     *
     * @return
     * @throws Exception
     */
    List<T> pullList() throws Exception;
}
