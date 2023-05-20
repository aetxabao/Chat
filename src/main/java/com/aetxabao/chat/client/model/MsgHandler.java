package com.aetxabao.chat.client.model;

import com.aetxabao.chat.client.model.msg.MsgBase;
import com.aetxabao.chat.client.model.msg.MsgLogin;
import com.aetxabao.chat.client.model.msg.MsgPayload;

import java.io.IOException;

import static com.aetxabao.chat.server.consts.AppConsts.*;

public class MsgHandler {

    public static MsgBase fromJson(String json) throws IOException {
        int i = json.indexOf(":") + 1;
        int j = json.indexOf(",");
        if (j==-1) j = json.length() - 1;
        String type = json.substring(i,j).replaceAll("\"","").replaceAll("\'","");
        MsgBase msg = switch (type) {
            case SIGNUP, LOGIN -> MsgLogin.fromJson(json, MsgLogin.class);
            case SIGRES, LOGRES, MSGTXT -> MsgPayload.fromJson(json, MsgPayload.class);
            default -> new MsgBase(type);
        };
        return msg;
    }

    public static void main(String[] args) throws IOException {
        MsgBase msg;
        msg = MsgHandler.fromJson("{\"type\":\"OTRO\"}");
        System.out.println(msg);
        msg = MsgHandler.fromJson("{\"type\":\"SIGNUP\"}");
        System.out.println(msg);
        msg = MsgHandler.fromJson("{\"type\":\"LOGIN\",\"authentication\":{\"username\":\"aitor\",\"password\":\"1234\"}}");
        System.out.println(msg);
        msg = MsgHandler.fromJson("{\"type\":\"MSGTXT\",\"payload\":\"Hello!\"}");
        System.out.println(msg);
        msg = MsgHandler.fromJson("{\"type\":\"LOGRES\",\"payload\":\"OK\"}");
        System.out.println(msg);
        msg = MsgHandler.fromJson("{\"type\":\"LOGRES\",\"payload\":\"Incorrect username-password.\"}");
        System.out.println(msg);
        msg = MsgHandler.fromJson("{\"type\":\"SIGRES\",\"payload\":\"OK\"}");
        System.out.println(msg);
        msg = MsgHandler.fromJson("{\"type\":\"SIGRES\",\"payload\":\"Username already exists.\"}");
        System.out.println(msg);
    }

}
