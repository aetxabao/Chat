package com.aetxabao.chat.server.managers.authenticator;

import com.aetxabao.chat.server.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbBadAuthenticator implements IAuthenticator {

    public boolean validate(String username, String password) throws SQLException {
        boolean exito = false;
        String sql = "SELECT username, password FROM users WHERE username LIKE BINARY ? AND password LIKE BINARY ?";
        Connection con = DbUtil.getConnection();
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1,username);
            pst.setString(2,password);
            try (ResultSet rs = pst.executeQuery()) {
                exito = rs.isBeforeFirst();
            }
        }
        return exito;
    }

}
