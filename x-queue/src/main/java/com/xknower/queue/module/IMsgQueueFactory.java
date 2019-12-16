package com.xknower.queue.module;

import java.io.Serializable;

/**
 * 消息队列工厂 [生产消费者队列]
 *
 * @author yuan
 */
public interface IMsgQueueFactory {

    /**
     * 将消息, 发往指定槽 (槽由消息唯一标识符, 进Hash计算, 并按照规则计算), 指定类型消息 (消息Hash类型)
     *
     * @param msg
     * @return
     */
    IMsgQueue send(IMsg msg);

    /**
     * 发往指定槽
     *
     * @param msg
     * @param specified
     * @return
     */
    <T> IMsgQueue<T> sendSpecified(T msg, Serializable specified);

    /**
     * 创建消息队列 (生成的队列名称, 以消息 Hash值命名)
     *
     * @param hash 队列名称
     * @param <T>
     * @return 创建的队列
     */
    <T> IMsgQueue<T> build(Serializable hash);

    /**
     * 通过队列名称和消息Hash, 创建消息队列
     *
     * @param name 队列名称
     * @param size
     * @param <T>
     * @return 创建的队列
     */
    <T> IMsgQueue<T> build(String name, int size);

    /**
     * @return 创建队列数
     */
    int queueCount();
}
