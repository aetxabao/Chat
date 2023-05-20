package com.aetxabao.chat.client.model.msg;

import java.io.IOException;

public class MsgLogin extends MsgBase {

    private Authentication authentication;

    public MsgLogin() {
        super();
        authentication = new Authentication();
    }

    public MsgLogin(String type, String username, String password) {
        super(type);
        authentication = new Authentication(username, password);
    }

    public Authentication getAuthentication() {
        return authentication;
    }

    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append("username: ").append(authentication.getUsername()).append("\n");
        sb.append("password: ").append(authentication.getPassword()).append("\n");
        return sb.toString();
    }

    public static void main(String[] args) throws IOException {
        MsgLogin msgLogin = new MsgLogin("SIGNUP","aitor","1234");
        System.out.println(msgLogin.toJson());

        msgLogin = (MsgLogin)MsgLogin.fromJson("{\"type\":\"LOGIN\",\"authentication\":{\"username\":\"aitor\",\"password\":\"1234\"}}", MsgLogin.class);
        System.out.println(msgLogin.toJson());

        MsgBase msg = MsgLogin.fromJson("{\"type\":\"LOGIN\",\"authentication\":{\"username\":\"aitor\",\"password\":\"1234\"}}", MsgLogin.class);
        System.out.println(msg.toJson());
        System.out.println(msg.getType());
        System.out.println(((MsgLogin)msg).getAuthentication().getUsername());
    }

}
