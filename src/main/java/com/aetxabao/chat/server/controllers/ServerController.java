package com.aetxabao.chat.server.controllers;

import com.aetxabao.chat.server.managers.authenticator.DbSecAuthenticator;
import com.aetxabao.chat.server.managers.authenticator.IAuthenticator;
import com.aetxabao.chat.server.managers.authenticator.PrivateKeyAuthenticator;
import com.aetxabao.chat.server.managers.register.DbChatRegister;
import com.aetxabao.chat.server.managers.register.DbLoginRegister;
import com.aetxabao.chat.server.managers.register.IRegister;
import com.aetxabao.chat.server.managers.register.SysOutRegister;
import com.aetxabao.chat.server.model.Record;
import com.aetxabao.chat.server.net.ChatServer;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Optional;

import static com.aetxabao.chat.server.consts.AppConsts.*;

public class ServerController {

    @FXML
    private TableColumn<Record, String> timestampColumn;
    @FXML
    private TableColumn<Record, Integer> portColumn;
    @FXML
    private TableColumn<Record, String> ipColumn;
    @FXML
    private TableColumn<Record, String> usernameColumn;
    @FXML
    private TableColumn<Record, String> messageColumn;

    @FXML
    private TableView<Record> tableView;

    private ChatServer chatServer;

    private IRegister<Record> loginRegister;
    private IRegister<Record> chatRegister;
    private IAuthenticator authenticator;

    public ServerController() {

        chatServer = new ChatServer(this);

        try {
            chatServer.listen(LISTENING_PORT);
        } catch (Exception e) {
            errorDialog("El servidor no puede escuchar",
                        "Error de configuración",
                        "Compruebe la configuración del servidor y \n" +
                        "que no haya ningún otro servicio funcionando " +
                        "en el puerto " + LISTENING_PORT);
            shutdown();
        }

        loginRegister = new DbLoginRegister();
        chatRegister = new DbChatRegister();
        authenticator = new DbSecAuthenticator();

        Record record = new Record(LISTENING_ADDRESS, LISTENING_PORT, SERVER, START);
        try {
            loginRegister.save(record);
        } catch (Exception e) {
            boolean b = confirmationDialog("El servidor no puede registrar datos",
                        "Error de acceso a datos",
                        "Desea continuar con la ejecución sin registrar ningún dato?\n" +
                                "Todos los usuarios se deberán autenticar con la clave " + PRIVATE_KEY);
            if (b) {
                loginRegister = new SysOutRegister();
                chatRegister = new SysOutRegister();
                authenticator = new PrivateKeyAuthenticator();
            } else {
                shutdown();
            }
        }
    }

    private void registerError() {
        errorDialog("El servidor no puede registrar datos", "Error de acceso a datos",
                "Compruebe que la base de datos está funcionando.\n" +
                        "Verifique también que el usuario de conexión exite.");
        shutdown();
    }

    private void errorDialog(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private boolean confirmationDialog(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        Optional<ButtonType> respuesta = alert.showAndWait();
        if (respuesta.get() == ButtonType.OK) {
            return true;
        }
        return false;
    }

    @FXML
    public void initialize() {
        timestampColumn.setCellValueFactory(new PropertyValueFactory<Record, String>("timestamp"));
        ipColumn.setCellValueFactory(new PropertyValueFactory<Record, String>("ip"));
        portColumn.setCellValueFactory(new PropertyValueFactory<Record, Integer>("port"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<Record, String>("username"));
        messageColumn.setCellValueFactory(new PropertyValueFactory<Record, String>("value"));
    }

    private void addRecordToTable(Record record){
        ObservableList<Record> records = tableView.getItems();
        records.add(record);
        tableView.setItems(records);
    }


    public String signup(String username, String password) {
        //TODO: signup
        return OK;
    }

    public boolean authenticate(String username, String password)  {
        boolean b = false;
        try {
            b = authenticator.validate(username,password);
        } catch (Exception e) {}
        return b;
    }

    public void loggedIn(boolean b, String host, int port, String username, String result) {
        Record record = new Record(host, port, username, result);
        if (b) {
            addRecordToTable(record);
        }
        try {
            loginRegister.save(record);
        } catch (Exception e) {
            registerError();
        }
    }

    public void msgTxtReceived(String host, int port, String username, String txt) {
        Record record = new Record(host, port, username, txt);
        addRecordToTable(record);
        try {
            chatRegister.save(record);
        } catch (Exception e) {
            registerError();
        }
    }

    public void disconnected(String host, int port, String username) {
        Record record = new Record(host, port, username, "LOGOUT");
        addRecordToTable(record);
        try {
            loginRegister.save(record);
        } catch (Exception e) {
            registerError();
        }
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
