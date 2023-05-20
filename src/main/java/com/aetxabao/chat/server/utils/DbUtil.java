package com.aetxabao.chat.server.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.aetxabao.chat.server.consts.AppConsts.*;

public class DbUtil {

    private static Connection con;

    public static Connection getConnection() throws SQLException {
        if (con == null){
            con = DriverManager.getConnection("jdbc:mysql://"+ DB_SERVER +"/" + DB_NAME, DB_USERNAME, DB_PASSWORD);
        }
        return con;
    }

    protected static void closeConnections() throws SQLException {
        if (!con.isClosed()) {
            con.close();
        }
    }

    public static void main(String[] args) {
        try {
            DbUtil.getConnection();
            System.out.println("Conexión establecida");
        } catch (SQLException e) {
            System.out.println("Fallo al conectar con la bd.");
        }
    }

}
