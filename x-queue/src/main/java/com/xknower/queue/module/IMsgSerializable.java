package com.xknower.queue.module;

/**
 * 消息序列化返回序列化
 *
 * @author xknower
 */
public interface IMsgSerializable {
    /**
     * 序列化, 编码对象为字节流
     *
     * @return 对象字节流
     */
    byte[] serialization();

    /**
     * 反序列化, 解码字节流为对象
     *
     * @param bytes 对象字节流
     */
    void deserialization(byte[] bytes);
}
