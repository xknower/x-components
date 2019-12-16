package com.xknower.queue.module;

import java.io.Serializable;

/**
 * 通过消息Hash计算相应消息槽(消息映射到消息队列)
 *
 * @author xknower
 */
public interface IMsgHash {
    /**
     * 消息Hash计算
     *
     * @param serializable
     * @return
     */
    Serializable hash(Serializable serializable);
}
