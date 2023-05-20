package com.aetxabao.chat.server.model.msg;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class MsgBase {
    private String type;

    public MsgBase() {
        type = "";
    }

    public MsgBase(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String toJson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }

    public static MsgBase fromJson(String json, Class theClassDotClass) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return (MsgBase) objectMapper.readValue(json, theClassDotClass);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("type: ").append(type).append("\n");
        return sb.toString();
    }

    public static void main(String[] args) throws IOException {
        MsgBase msgBase = new MsgBase("LOGIN");
        System.out.println(msgBase.toJson());

        msgBase = MsgBase.fromJson("{\"type\":\"SIGNUP\"}", MsgBase.class);
        System.out.println(msgBase.toJson());

//        msgBase = MsgBase.fromJson("{\"type\":\"LOGIN\",\"authentication\":{\"username\":\"aitor\",\"password\":\"1234\"}}", MsgBase.class);//ERROR
//        System.out.println(msgBase.toJson());
    }
}
