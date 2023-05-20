package com.aetxabao.chat.server.managers.authenticator;

import static com.aetxabao.chat.server.consts.AppConsts.PRIVATE_KEY;

public class PrivateKeyAuthenticator implements IAuthenticator {

    @Override
    public boolean validate(String username, String password) throws Exception {
        return password.equals(PRIVATE_KEY);
    }

}
