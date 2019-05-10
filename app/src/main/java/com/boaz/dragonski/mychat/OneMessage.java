package com.boaz.dragonski.mychat;


import java.util.Date;

public class OneMessage implements Comparable<OneMessage>{

    private String content;
    private String id;
    private Date timestamp;

    public OneMessage() {
        init();
    }

    public OneMessage(String text) {
        this.content = text;
        init();
    }

    private void init() {
        timestamp = new Date(System.currentTimeMillis());
        id = String.valueOf(this.hashCode());
    }

    public String getContent() {
        return content;
    }

    public String getMsgId() {
        return id;
    }

    public Date getTimestamp() {
        return timestamp;
    }


//    public void setContent(String text) {
//        this.content = text;
//    }
//
//    public void setMsgId(String id) {
//        this.id = id;
//    }

//    public void setTimestamp(Date timestamp) {
//        this.timestamp = timestamp;
//    }

    @Override
    public int compareTo(OneMessage o) {
        return timestamp.compareTo(o.getTimestamp());
    }
}
