package com.aetxabao.chat.server.net;

import com.aetxabao.chat.server.controllers.ServerController;

public final class ChatServer {

    private final ServerController serverController;

    public ChatServer(ServerController serverController) {
        this.serverController = serverController;
    }

    public void close() {
    }

}