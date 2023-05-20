package com.aetxabao.chat.server.model;

import java.time.LocalDateTime;

public class Record {
    private String timestamp;
    private String ip;
    private int port;
    private String username;
    private String value;

    public Record() {
        this.timestamp = "";
        this.ip = "";
        this.port = 0;
        this.username = "";
        this.value = "";
    }

    public Record(String timestamp, String ip, int port, String username, String value) {
        this.timestamp = timestamp;
        this.ip = ip;
        this.port = port;
        this.username = username;
        this.value = value;
    }

    public Record(String ip, int port, String username, String value) {
        this.timestamp = LocalDateTime.now().toString().replace('T',' ');
        this.ip = ip;
        this.port = port;
        this.username = username;
        this.value = value;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
