package com.aetxabao.chat.client.controllers;

import javafx.application.Platform;
import javafx.stage.Stage;
import com.aetxabao.chat.client.net.ChatClient;

public class ChatController extends AController {

    private ChatClient chatClient;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setChatClient(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @Override
    public void textReception(String text) {
    }

    @Override
    public void disconnected(String reason){
    }

    public void shutdown() {
        try{
            chatClient.close();
        }catch(Exception e){
        }finally {
            Platform.exit();
            System.exit(0);
        }
    }
}
