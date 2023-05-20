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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-29s", timestamp));
        sb.append(String.format("%15s", ip));
        sb.append(" ".repeat(2));
        sb.append(String.format("%-7d", port));
        sb.append(" ".repeat(2));
        sb.append(String.format("%-8s", username));
        sb.append(" ".repeat(2));
        sb.append(value);
        return sb.toString();
    }
}
