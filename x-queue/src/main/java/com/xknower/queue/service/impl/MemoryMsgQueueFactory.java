package com.xknower.queue.service.impl;

import com.xknower.queue.module.IMsg;
import com.xknower.queue.module.IMsgHash;
import com.xknower.queue.module.IMsgQueue;
import com.xknower.queue.service.AbsMsgQueueFactory;

import java.io.Serializable;

/**
 * 消息队列工厂, 内存实现
 *
 * @author xknower
 */
public class MemoryMsgQueueFactory extends AbsMsgQueueFactory {

    /**
     * 队列个数
     */
    private int messageQueueCount;

    /**
     * 每个队列的最大元素个数 (默认创建使用)
     */
    private int messageQueueSize;

    /**
     * 消息Hash计算, 并根据Hash及计算规则, 计算该消息发往那个 槽
     */
    protected IMsgHash msgHash;

    public MemoryMsgQueueFactory(int messageQueueCount, int messageQueueSize) {
        this.messageQueueCount = messageQueueCount == 0 ? 1 : messageQueueCount;
        this.messageQueueSize = messageQueueSize;
        //
        this.msgHash = new DefaultMsgHash(this.messageQueueCount);
    }

    /**
     * 发送消息
     *
     * @param msg 消息
     * @return
     */
    @Override
    public IMsgQueue send(IMsg msg) {
        // msgHash 根据消息Hash及计算规则, 计算该消息发往那个 槽
        return sendSpecified(msg, msgHash.hash(msg.hash()));
    }

    /**
     * 创建数字标识的, 消费队列
     *
     * @param hash
     * @param <T>
     * @return
     */
    @Override
    public <T> IMsgQueue<T> build(Serializable hash) {
        IMsgQueue msgQueue =
                groupQueueList
                        .computeIfAbsent(hash, k -> new MsgMemoryQueue(String.format("消息队列-[%s]", k), messageQueueSize));

        // 创建消息队列 ==> [ %s ]
        return msgQueue;
    }

    /**
     * 创建带名称的消费队列
     *
     * @param name 队列名称
     * @param size 消费队列中, 最大消息数
     * @param <T>
     * @return
     */
    @Override
    public <T> IMsgQueue<T> build(String name, int size) {
        return new MsgMemoryQueue(name, size);
    }

    @Override
    public int queueCount() {
        return this.messageQueueCount;
    }
}
