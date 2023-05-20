package com.aetxabao.chat.client.terminal;

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
import java.util.Scanner;

import static com.aetxabao.chat.client.consts.AppConsts.*;


public class ChatClientTerm {

    private String host;
    private int port;
    private String username;
    private String password;
    private EventLoopGroup group;
    private ChannelFuture f;
    private ChatClientHandlerTerm chatClientHandlerTerm;
    private boolean authorized = false;
    private boolean connected = false;

    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        System.out.println("CHAT CLIENT");

        ChatClientTerm client = new ChatClientTerm();

        if (!client.getParameters(args)) return;

        client.connect(client.host, client.port);

        client.login(client.username, client.password);

        for (int i = 0; i < TIMEOUT && !client.isAuthorized() ; i++) {
            Thread.sleep(1000);
        }

        if (client.isAuthorized()){
            System.out.println("Write your message or exit|quit|bye|adios|agur to finish.");
            boolean b = true;
            while (client.connected && b ) {
                b = client.writeInTerm();
            }
        }

        client.close();
    }

    public boolean writeInTerm() throws InterruptedException, JsonProcessingException {
        String input = scanner.nextLine();
        sendTxt(input);
        if (input.equalsIgnoreCase("exit") ||
                input.equalsIgnoreCase("quit") ||
                input.equalsIgnoreCase("bye")  ||
                input.equalsIgnoreCase("adios")  ||
                input.equalsIgnoreCase("agur") )  {
            return false;
        }
        return true;
    }

    public boolean getParameters(String[] args) {
        if (args.length == 4) {
            host = args[0];
            port = Integer.parseInt(args[1]);
            username = args[2];
            password = args[3];
            return true;
        } else if (args.length == 0) {
            System.out.print("Please enter the server [" + CONNECTION_SERVER + "]: ");
            host = scanner.nextLine();
            if (host.trim().length()==0) {
                host = CONNECTION_SERVER;
            }
            System.out.print("Please enter the port [" + CONNECTION_PORT + "]: ");
            String s = scanner.nextLine();
            if (s.trim().length()==0) {
                port = CONNECTION_PORT;
            }else {
                port = Integer.parseInt(s);
            }
            username = "";
            while (username.length()==0) {
                System.out.print("Please enter your name: ");
                username = scanner.nextLine().replaceAll("[\\t\\n\\r]+","");
            }
            System.out.print("Please enter your password [" + PRIVATE_KEY + "]: ");
            password = scanner.nextLine();
            if (password.trim().length()==0) {
                password = PRIVATE_KEY;
            }
            return true;
        } else if (args.length == 2) {
            host = args[0];
            port = Integer.parseInt(args[1]);
            username = "";
            while (username.length()==0) {
                System.out.print("Please enter your name: ");
                username = scanner.nextLine().replaceAll("[\\t\\n\\r]+","");
            }
            System.out.print("Please enter your password: ");
            password = scanner.nextLine();
            return true;
        } else {
            System.out.println("Execution pattern: ");
            System.out.println("java ChatClientTerm host port username password");
            System.out.println("Execution example: ");
            System.out.println("java ChatClientTerm " + CONNECTION_SERVER + " " + CONNECTION_PORT + " aitor " + PRIVATE_KEY);
            return false;
        }
    }


    public void connect(String host, int port) throws InterruptedException, ConnectException {
        group = new NioEventLoopGroup();
        try {
            chatClientHandlerTerm = new ChatClientHandlerTerm(this);
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            p.addLast(new StringDecoder());
                            p.addLast(new StringEncoder());
                            p.addLast(chatClientHandlerTerm);
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
        System.out.println(txt);
    }

    public void receivedText(String txt){
        System.out.println(txt);
    }

    public void disconnected(){
        System.out.println("Disconnected client.\nIf necessary press ENTER to finish.");
        close();
    }

    public void close() {
        connected = false;
        try{
            f.channel().closeFuture();
        }finally {
            group.shutdownGracefully();
        }
    }

}