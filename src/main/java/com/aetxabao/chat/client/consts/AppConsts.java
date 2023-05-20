package com.aetxabao.chat.client.consts;

public final class AppConsts {

    //region App
    public static final String TITLE = "Chat client";
    public static final int WIDTH = 450;
    public static final int HEIGHT = 300;
    public static final int TIMEOUT = 3;
    //endregion

    //region Net
    public static final String CONNECTION_SERVER = "127.0.0.1";
    public static final int CONNECTION_PORT = 4242;
    //endregion

    //region Sec
    public static final String PRIVATE_KEY = "1234";
    //endregion

    //region Msg
    public static final String LOGIN = "LOGIN";
    public static final String OK = "OK";
    public static final String MSGTXT = "MSGTXT";
    //endregion

    //region Errores
    public static final String CONTROLLER_MISSING = "Falta definir el controlador";
    //endregion

    private AppConsts() { }

}
