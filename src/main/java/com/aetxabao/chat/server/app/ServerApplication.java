package com.aetxabao.chat.server.app;

import com.aetxabao.chat.server.controllers.ServerController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

import static com.aetxabao.chat.server.consts.AppConsts.*;

public class ServerApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GuiServer.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);
        scene.getStylesheets().add(getClass().getResource("server.css").toExternalForm());
        stage.setTitle(CHAT_SERVER + LISTENING_ON_PORT + LISTENING_PORT);
        stage.setScene(scene);

        Image icon16 = new Image(ServerApplication.class.getResourceAsStream("server16.png"));
        Image icon32 = new Image(ServerApplication.class.getResourceAsStream("server32.png"));
        Image icon64 = new Image(ServerApplication.class.getResourceAsStream("server64.png"));
        Image icon128 = new Image(ServerApplication.class.getResourceAsStream("server128.png"));
        stage.getIcons().addAll(icon16, icon32, icon64, icon128);

        ServerController controller = fxmlLoader.getController();
        stage.setOnCloseRequest(event -> {
            controller.shutdown();//chat server
            Platform.exit();//gui thread
            System.exit(0);//kill JVM
        });

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}