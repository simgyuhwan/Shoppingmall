package com.growing.sgh.event;

public abstract class Event {
    private long timeStamp;

    public Event(){
        this.timeStamp = System.currentTimeMillis();
    }

    public long getTimeStamp(){
        return timeStamp;
    }
}
