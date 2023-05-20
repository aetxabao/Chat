package com.aetxabao.chat.client.controllers;

import com.aetxabao.chat.client.managers.register.FileChatRegister;
import com.aetxabao.chat.client.managers.register.IRegister;
import com.aetxabao.chat.client.net.ChatClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ChatController extends AController {

    @FXML
    private Button btnSend;

    @FXML
    private TextArea txaChat;

    @FXML
    private TextField txtMsg;

    private ChatClient chatClient;

    private Stage stage;

    private IRegister<String> register;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setChatClient(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public ChatController() {
        register = new FileChatRegister();
    }

    @FXML
    public void initialize() {
        txtMsg.requestFocus();
        txtMsg.selectAll();
    }

    @Override
    public void textReception(String text) {
        txaChat.appendText(text);
        txaChat.appendText("\n");
        try {
            register.save(text);
        } catch (Exception e) {
            errorDialog("Error de registro",
                    "Imposible salvar el texto recibido.",
                    "Compruebe la documentación.");
        }
    }

    @Override
    public void disconnected(String header){
        txtMsg.setDisable(true);
        btnSend.setDisable(true);
        errorDialog("Error de comunicación",
                    header,
                    "Reinicie e intente conectarse de nuevo");
    }

    private void errorDialog(String title, String header, String content) {
        Platform.runLater(()->{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText(header);
            alert.setContentText(content);
            alert.showAndWait();
        });
    }

    @FXML
    public void send(ActionEvent event) {
        try {
            chatClient.sendTxt(txtMsg.getText());
        } catch (InterruptedException e) {
            disconnected("Servidor inaccesible.");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
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
