package com.xknower.biz.msg.config;

import com.xknower.biz.msg.module.queue.T808MsgAdapter;
import com.xknower.biz.msg.module.queue.T808MsgConsumeFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 消息队列服务配置
 */
@Configuration
public class BeanConfiguration {

    @Bean
    T808MsgConsumeFactory t808MsgConsumeFactory(T808MsgAdapter msgHandler) {
        //
        int messageQueueCount = 2;
        //
        int messageQueueSize = 2;
        //
        return new T808MsgConsumeFactory(msgHandler, messageQueueCount, messageQueueSize);
    }
}
