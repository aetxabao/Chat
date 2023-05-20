package com.aetxabao.chat.server.managers.register;

public interface IRegister<T> {

    void save(T t) throws Exception;

}
