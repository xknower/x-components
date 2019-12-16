package com.xknower.queue.service.impl;

import com.xknower.queue.module.IMsgQueue;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 消息队列, 内存实现
 *
 * @param <T> 消息
 * @author knower
 */
public class MsgMemoryQueue<T> implements IMsgQueue<T> {

    /**
     * 队列名称
     */
    private final String name;

    /**
     * 队列 : BlockingQueue - LinkedBlockingDeque
     */
    private final BlockingQueue<T> queue;

    public MsgMemoryQueue(String name, int size) {
        this.name = name;
        //
        this.queue = new LinkedBlockingDeque<T>(size);
    }

    @Override
    public String name() {
        return this.name;
    }

    /**
     * 压入消息
     *
     * @param message
     */
    @Override
    public void put(T message) {
        try {
            this.queue.put(message);
        } catch (InterruptedException e) {
            // 线程中断, 无法插入新数据
        }
    }

    /**
     * 弹出消息
     *
     * @return 一个消息
     */
    @Override
    public T pull() throws Exception {
        return this.queue.take();
    }

    /**
     * 弹出消息
     *
     * @return 一组消息
     */
    @Override
    public List<T> pullList() {
        try {
            return Collections.singletonList(this.queue.take());
        } catch (InterruptedException e) {
            //
            return null;
        }
    }
}