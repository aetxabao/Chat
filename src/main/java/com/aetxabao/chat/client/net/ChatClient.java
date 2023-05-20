package com.aetxabao.chat.client.net;

import com.aetxabao.chat.client.controllers.AController;
import com.aetxabao.chat.client.exceptions.ControllerException;
import com.aetxabao.chat.client.model.msg.MsgLogin;
import com.aetxabao.chat.client.model.msg.MsgPayload;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.net.ConnectException;

import static com.aetxabao.chat.client.consts.AppConsts.CONTROLLER_MISSING;
import static com.aetxabao.chat.client.consts.AppConsts.MSGTXT;

public class ChatClient {
    private String username;
    private EventLoopGroup group;
    private ChannelFuture f;
    private ChatClientHandler chatClientHandler;
    private boolean authorized = false;
    private boolean connected = false;

    private AController controller = null;

    public ChatClient() {
    }

    public void setController(AController controller) {
        this.controller = controller;
    }

    public void connect(String host, int port) throws ControllerException, InterruptedException, ConnectException {
        if (controller == null) {
            throw new ControllerException(CONTROLLER_MISSING);
        }
        group = new NioEventLoopGroup();
        try {
            chatClientHandler = new ChatClientHandler(this);
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            p.addLast(new StringDecoder());
                            p.addLast(new StringEncoder());
                            p.addLast(chatClientHandler);
                        }
                    });
            f = b.connect(host, port).sync();
            connected = true;
        } catch (InterruptedException e) {
            close();
            throw e;
        } catch (Exception e) {
            close();
            throw new ConnectException();
        }
    }

    private void send(String text) throws InterruptedException {
        Channel channel = f.sync().channel();
        channel.writeAndFlush(text);
        channel.flush();
    }

    public void login(String username, String password) throws JsonProcessingException, InterruptedException {
        this.username = username;
        MsgLogin msgLogin = new MsgLogin("LOGIN", username, password);
        String json = msgLogin.toJson();
        send(json);
    }

    public void sendTxt(String txt) throws InterruptedException, JsonProcessingException {
        MsgPayload msgPayload = new MsgPayload(MSGTXT, username + " -> " + txt);
        String json = msgPayload.toJson();
        send(json);
    }

    public boolean isAuthorized() {
        return authorized;
    }

    public void setAuthorized(boolean value, String txt){
        authorized = value;
        controller.loginResponse(authorized, txt);
    }

    public void receivedText(String txt){
        controller.textReception(txt);
    }

    public void disconnected() {
        controller.disconnected("Conexión con el servidor interrumpida.");
        close();
    }

    public void close() {
        connected = false;
        try{
            f.channel().closeFuture();
        }catch (Exception e){
        }finally {
            group.shutdownGracefully();
        }
    }

}