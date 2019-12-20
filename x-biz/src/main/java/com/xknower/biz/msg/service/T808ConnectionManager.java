package com.xknower.biz.msg.service;

import com.xknower.biz.msg.module.T808Msg;
import com.xknower.thrid.netty.service.AbsConnectionManager;

/**
 * @author xknower
 */
public class T808ConnectionManager extends AbsConnectionManager<T808Msg> {

    private T808MsgService msgService;

    public T808ConnectionManager(T808ConnectionService connectionService, T808MsgService msgService) {
        super(connectionService);
        this.msgService = msgService;
    }

    @Override
    public Class<T808Msg> support() {
        return T808Msg.class;
    }

    @Override
    public void handle(T808Msg msg) {
        //
        msgService.send(msg);
    }
}
