package com.aetxabao.chat.client.controllers;

import javafx.stage.Stage;

public abstract class AController {

    public void setStage(Stage stage) {
    }

    public void loginResponse(boolean result, String response) {
    }

    public void textReception(String text) {
    }

    public void disconnected(String reason) {
    }

}
