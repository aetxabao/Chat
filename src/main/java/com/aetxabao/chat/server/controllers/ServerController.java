package com.aetxabao.chat.server.controllers;

import javafx.application.Platform;
import com.aetxabao.chat.server.net.ChatServer;

public class ServerController {

    private ChatServer chatServer;
    public ServerController() {
        chatServer = new ChatServer(this);
    }

    public void shutdown() {
        try{
            chatServer.close();
        }catch(Exception e){
        }finally {
            Platform.exit();
            System.exit(0);
        }
    }

}
