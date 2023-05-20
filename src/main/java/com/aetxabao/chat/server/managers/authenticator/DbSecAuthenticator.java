package com.aetxabao.chat.server.managers.authenticator;

import com.aetxabao.chat.server.utils.DbUtil;
import com.aetxabao.chat.server.utils.SecUtil;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbSecAuthenticator implements IAuthenticator {

    public boolean validate(String username, String password) throws SQLException, NoSuchAlgorithmException {
        boolean exito = false;
        String sql1 = "SELECT salt FROM users WHERE LOWER(name) = LOWER(?)";
        Connection con = DbUtil.getConnection();
        try (PreparedStatement pst1 = con.prepareStatement(sql1)) {
            pst1.setString(1,username);
            try (ResultSet rs1 = pst1.executeQuery()) {
                if (rs1.next()){
                    String salt = rs1.getString(1);
                    String hash = SecUtil.sha256(password + salt);
                    String sql2 = "SELECT name FROM users WHERE LOWER(name) = LOWER(?) AND hash = ?";
                    try (PreparedStatement pst2 = con.prepareStatement(sql2)) {
                        pst2.setString(1,username);
                        pst2.setString(2,hash);
                        try (ResultSet rs2 = pst2.executeQuery()) {
                            if (rs2.next()){
                                exito = true;
                            }
                        }
                    }
                }
            }
        }
        return exito;
    }

}
