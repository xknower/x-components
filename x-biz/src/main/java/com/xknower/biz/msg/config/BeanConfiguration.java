package com.xknower.biz.msg.config;

import com.xknower.biz.msg.module.queue.T808MsgAdapter;
import com.xknower.biz.msg.module.queue.T808MsgConsumeFactory;
import com.xknower.biz.msg.service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 消息队列服务配置
 */
@Configuration
public class BeanConfiguration {

    @Bean
    T808MsgAdapter t808MsgAdapter() {
        return new T808MsgAdapter();
    }

    @Bean
    T808MsgConsumeFactory t808MsgConsumeFactory(T808MsgAdapter msgHandler) {
        //
        int messageQueueCount = 2;
        //
        int messageQueueSize = 2;
        //
        return new T808MsgConsumeFactory(msgHandler, messageQueueCount, messageQueueSize);
    }

    @Bean
    T808MsgService t808MsgService(T808MsgConsumeFactory t808MsgConsumeFactory) {
        return new T808MsgService(t808MsgConsumeFactory);
    }

    @Bean
    T808ConnectionService t808ConnectionService() {
        return new T808ConnectionService();
    }

    @Bean
    T808ConnectionManager t808ConnectionManager(T808ConnectionService t808ConnectionService, T808MsgService t808MsgService) {
        return new T808ConnectionManager(t808ConnectionService, t808MsgService);
    }

    @Bean
    T808TCPService t808TCPService(T808ConnectionManager t808ConnectionService, T808MsgService t808MsgService) {
        return new T808TCPService(t808ConnectionService, t808MsgService);
    }
}
