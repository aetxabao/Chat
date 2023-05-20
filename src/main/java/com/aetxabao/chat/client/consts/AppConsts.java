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
    public static final String SIGNUP = "SIGNUP";
    public static final String SIGRES = "SIGRES";
    public static final String LOGIN = "LOGIN";
    public static final String LOGRES = "LOGRES";
    public static final String CHGPWD = "CHGPWD";
    public static final String CHGRES = "CHGRES";
    public static final String DELUSR = "DELUSR";
    public static final String DELRES = "DELRES";
    public static final String OK = "OK";
    public static final String SERVER = "SERVER";
    public static final String MSGTXT = "MSGTXT";
    //endregion

    //region Errores
    public static final String ERROR_USERNAME_PASSWORD = "Error usuario-password incorrectos";
    public static final String ERROR_USERNAME_EXISTS = "El nombre de usuario está ocupado";
    public static final String ERROR_PORT = "Puerto incorrecto.";
    public static final String ERROR_CONNECTION = "Error de conexión con el servidor";
    public static final String ERROR_SEND = "Error al comunicarse con el servidor";
    public static final String CONTROLLER_MISSING = "Falta definir el controlador";
    public static final String TRIAL = "intento";
    //endregion

    //region io
    public static String IO_FOLDER = "data";
    public static String IO_FILE_PREFIX = "chat";
    public static String IO_FILE_SEPARATOR = "_";
    public static String IO_FILE_EXTENSION = ".txt";
    //endregion

    private AppConsts() { }

}
