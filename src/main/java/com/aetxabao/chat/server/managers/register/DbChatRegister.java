package com.aetxabao.chat.server.managers.register;

import com.aetxabao.chat.server.model.Record;
import com.aetxabao.chat.server.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.aetxabao.chat.server.consts.AppConsts.DB_PST_INSERT_CHAT;

public class DbChatRegister implements IRegister<Record>{

    public void save(Record record) throws SQLException {
        String sql = DB_PST_INSERT_CHAT;
        Connection con = DbUtil.getConnection();
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1,record.getTimestamp());
            pst.setString(2,record.getIp());
            pst.setInt(3,record.getPort());
            pst.setString(4,record.getUsername());
            pst.setString(5,record.getValue());
            int result = pst.executeUpdate();
        }
    }

}
