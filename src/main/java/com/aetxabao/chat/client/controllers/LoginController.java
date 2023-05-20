package com.aetxabao.chat.client.controllers;

import com.aetxabao.chat.client.app.ClientApplication;
import com.aetxabao.chat.client.net.ChatClient;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;

import static com.aetxabao.chat.client.consts.AppConsts.*;

public class LoginController extends AController {


    private String username = "";
    private ChatClient chatClient;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setChatClient(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @Override
    public void loginResponse(boolean result, String response) {
        if (result){
            try {
                Platform.runLater(()->{
                    stage.setTitle(TITLE + " " + username);
                });

                FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("GuiClient.fxml"));

                Parent parent = fxmlLoader.load();

                ChatController controller = fxmlLoader.getController();
                controller.setStage(stage);
                controller.setChatClient(chatClient);
                chatClient.setController(controller);

                stage.getScene().setRoot(parent);

                stage.setOnCloseRequest(event -> {
                    controller.shutdown();
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            Platform.runLater(()->{

            });
        }
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
