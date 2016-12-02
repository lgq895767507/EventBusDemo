package com.flw.eventbusdemo.event;

/**
 * Created by lgq on 2016/12/2.
 */

public class IdEvent {
    private int id;

    public IdEvent(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
