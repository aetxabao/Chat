package com.aetxabao.chat.client.app;

import com.aetxabao.chat.client.controllers.LoginController;
import com.aetxabao.chat.client.net.ChatClient;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static com.aetxabao.chat.client.consts.AppConsts.*;

public class ClientApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        ChatClient chatClient = new ChatClient();

        FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);

        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.setResizable(false);

        LoginController controller = fxmlLoader.getController();
        controller.setStage(stage);
        controller.setChatClient(chatClient);
        chatClient.setController(controller);
        stage.setOnCloseRequest(event -> {
            controller.shutdown();
        });

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}