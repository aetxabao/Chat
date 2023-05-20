package com.aetxabao.chat.server.app;

import com.aetxabao.chat.server.controllers.ServerController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static com.aetxabao.chat.server.consts.AppConsts.*;

public class ServerApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ServerApplication.class.getResource("GuiServer.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);
        scene.getStylesheets().add(ServerApplication.class.getResource("server.css").toExternalForm());
        stage.setTitle(TITLE);
        stage.setScene(scene);

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