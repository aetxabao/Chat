package com.aetxabao.chat.client.net;

import com.aetxabao.chat.client.controllers.AController;

public class ChatClient {

    private AController controller = null;

    public void setController(AController controller) {
        this.controller = controller;
    }

    public void close() {
    }

}