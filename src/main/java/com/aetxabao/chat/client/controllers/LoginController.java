package com.aetxabao.chat.client.controllers;

import com.aetxabao.chat.client.app.ClientApplication;
import com.aetxabao.chat.client.exceptions.ControllerException;
import com.aetxabao.chat.client.net.ChatClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.ConnectException;

import static com.aetxabao.chat.client.consts.AppConsts.*;

public class LoginController extends AController {

    @FXML
    private Button btnLogin;

    @FXML
    private Label lblNew;

    @FXML
    private Label lblNombreError;

    @FXML
    private Label lblPasswordError;

    @FXML
    private Label lblServerError;

    @FXML
    private Label lblReset;

    @FXML
    private GridPane panelGrid;

    @FXML
    private PasswordField pwdPassword;

    @FXML
    private Label titulo;

    @FXML
    private TextField txtServer;
    @FXML
    private TextField txtPort;
    @FXML
    private TextField txtNombre;

    private ChatClient chatClient;

    private Stage stage;

    private int errorCnt = 0;

    private String username = "";

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setChatClient(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @FXML
    public void initialize() {
        chatClient = new ChatClient();
        txtServer.setText(CONNECTION_SERVER);
        txtPort.setText(String.valueOf(CONNECTION_PORT));
        txtNombre.requestFocus();
    }

    @FXML
    void highLight(MouseEvent event) {
        if (event.getEventType() == MouseEvent.MOUSE_ENTERED){
            lblNew.setTextFill(Color.GREEN);
        }else if (event.getEventType() == MouseEvent.MOUSE_EXITED){
            lblNew.setTextFill(Color.BLACK);
        }
    }

    @FXML
    void login(ActionEvent event) {
        int port;
        resetErrors();
        String server = txtServer.getText();
        try{
            port = Integer.parseInt(txtPort.getText());
        } catch (NumberFormatException e) {
            lblServerError.setText(ERROR_PORT);
            return;
        }
        username = txtNombre.getText();
        String password = pwdPassword.getText();
        try {
            chatClient.connect(server, port);
        } catch (ControllerException e) {
            e.printStackTrace();
            return;
        } catch (InterruptedException e) {
            lblServerError.setText(ERROR_CONNECTION + ", " + TRIAL + " " + ++errorCnt);
            return;
        } catch (ConnectException e) {
            lblServerError.setText(ERROR_CONNECTION + ", " + TRIAL + " " + ++errorCnt);
            return;
        }

        try {
            chatClient.login(username, password);
        } catch (JsonProcessingException e) {
        } catch (InterruptedException e) {
            lblServerError.setText(ERROR_SEND);
        }

    }

    @Override
    public void loginResponse(boolean result, String response) {
        if (result){
            BorderPane root = null;
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
                lblNombreError.setText(response);
            });
        }
    }

    private void resetErrors() {
        lblServerError.setText("");
        lblNombreError.setText("");
        lblPasswordError.setText("");
    }

    @FXML
    void resetOrNewAccount(MouseEvent event) {

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
