package com.aetxabao.chat.server.consts;

public final class AppConsts {

    //region App
    public static final String TITLE = "Chat server";
    public static final int WIDTH = 900;
    public static final int HEIGHT = 450;
    //endregion

    //region Net
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
    public static final String OK = "OK";
    public static final String SERVER = "SERVER";
    public static final String ERROR_USERNAME_PASSWORD = "Incorrect username-password.";
    public static final String MSGTXT = "MSGTXT";
    //endregion

    private AppConsts() { }
}
