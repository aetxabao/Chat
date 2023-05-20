package com.aetxabao.chat.client.model.msg;

import java.io.IOException;

public class MsgPayload extends MsgBase {

    private String payload;

    public MsgPayload() {
        super();
        payload = "";
    }

    public MsgPayload(String type, String payload) {
        super(type);
        this.payload = payload;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append("payload: ").append(payload).append("\n");
        return sb.toString();
    }

    public static void main(String[] args) throws IOException {
        MsgPayload msgPayload = new MsgPayload("MSGTXT", "Hello world!");
        System.out.println(msgPayload.toJson());
        msgPayload = (MsgPayload) MsgPayload.fromJson("{\"type\":\"MSGTXT\",\"payload\":\"Hello!\"}",MsgPayload.class);
        System.out.println(msgPayload.toJson());
    }

}
