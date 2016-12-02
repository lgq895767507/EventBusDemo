package com.flw.eventbusdemo.event;

/**
 * Created by lgq on 2016/12/2.
 */

/**
 * 自定义类
 */
public class MessageEvent {

    private String msg;

    public String getMsg() {
        return msg;
    }

    public MessageEvent(String msg) {
        this.msg = msg;
    }
}
