package com.aetxabao.chat.server.consts;

public final class AppConsts {

    //region App
    public static final String CHAT_SERVER = "Chat server";
    public static final String LISTENING_ON_PORT = " listening on port ";
    public static final int WIDTH = 900;
    public static final int HEIGHT = 450;
    //endregion

    //region Net
    public static final String LISTENING_ADDRESS = "127.0.0.1";
    public static final int LISTENING_PORT = 4242;
    //endregion

    //region Sec
    public static final String PRIVATE_KEY = "1234";
    //endregion

    //region Msg
    public static final String SIGNUP = "SIGNUP";
    public static final String SIGRES = "SIGRES";
    public static final String LOGIN = "LOGIN";
    public static final String LOGOUT = "LOGOUT";
    public static final String LOGRES = "LOGRES";
    public static final String CHGPWD = "CHGPWD";
    public static final String CHGRES = "CHGRES";
    public static final String DELUSR = "DELUSR";
    public static final String DELRES = "DELRES";
    public static final String OK = "OK";
    public static final String ERROR_USERNAME_PASSWORD = "Incorrect username-password.";
    public static final String ERROR_USERNAME_EXISTS = "Username already exists.";
    public static final String MSGTXT = "MSGTXT";
    //endregion

    //region db
    public static final String SERVER = "SERVER";
    public static final String START = "START";
    public static final String DB_SERVER = "localhost";
    public static final String DB_NAME = "chat";
    public static final String DB_USERNAME = "pepe";
    public static final String DB_PASSWORD = "pepa";
    public static final String DB_PST_INSERT_CHAT = "INSERT INTO chat() VALUES (?, ?, ?, ?, ?)";
    public static final String DB_PST_INSERT_LOGIN = "INSERT INTO login() VALUES (?, ?, ?, ?, ?)";
    //endregion

    private AppConsts() { }
}
