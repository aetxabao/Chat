package com.aetxabao.chat.client.app;

import com.aetxabao.chat.client.controllers.LoginController;
import com.aetxabao.chat.client.net.ChatClient;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

import static com.aetxabao.chat.client.consts.AppConsts.*;

public class ClientApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        ChatClient chatClient = new ChatClient();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/aetxabao/chat/client/app/Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);
        scene.getStylesheets().add(getClass().getResource("/com/aetxabao/chat/client/app/client.css").toExternalForm());

        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.setResizable(false);

        Image icon16 = new Image(ClientApplication.class.getResourceAsStream("client16.png"));
        Image icon32 = new Image(ClientApplication.class.getResourceAsStream("client32.png"));
        Image icon64 = new Image(ClientApplication.class.getResourceAsStream("client64.png"));
        Image icon128 = new Image(ClientApplication.class.getResourceAsStream("client128.png"));
        stage.getIcons().addAll(icon16, icon32, icon64, icon128);

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