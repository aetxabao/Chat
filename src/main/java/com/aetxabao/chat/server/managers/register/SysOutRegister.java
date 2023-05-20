package com.aetxabao.chat.server.managers.register;

import com.aetxabao.chat.server.model.Record;

public class SysOutRegister implements IRegister<Record> {
    @Override
    public void save(Record record) throws Exception {
        System.out.println(record.toString());
    }
}
