package com.aetxabao.chat.server.managers.authenticator;

public interface IAuthenticator {
    boolean validate(String username, String password) throws Exception;

}
