module com.masanz.chatnettyfx {

    requires javafx.controls;
    requires javafx.fxml;

    requires io.netty.transport;
    requires io.netty.codec;

    requires java.sql;

    requires java.persistence;
    requires org.hibernate.orm.core;
    requires org.hibernate.commons.annotations;
    requires java.naming;

    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    exports com.aetxabao.chat.client.model.msg to com.fasterxml.jackson.databind;
    exports com.aetxabao.chat.server.model.msg to com.fasterxml.jackson.databind;

    opens com.aetxabao.chat.client to javafx.fxml;
    exports com.aetxabao.chat.client;
    opens com.aetxabao.chat.client.controllers to javafx.fxml;
    exports com.aetxabao.chat.client.controllers;

    opens com.aetxabao.chat.server to javafx.fxml;
    exports com.aetxabao.chat.server;
    opens com.aetxabao.chat.server.controllers to javafx.fxml;
    exports com.aetxabao.chat.server.controllers;

    opens com.aetxabao.chat.server.model to javafx.base;
    opens com.aetxabao.chat.server.model.msg to javafx.base;
    exports com.aetxabao.chat.server.app;
    opens com.aetxabao.chat.server.app to javafx.fxml;
    exports com.aetxabao.chat.client.app;
    opens com.aetxabao.chat.client.app to javafx.fxml;

}