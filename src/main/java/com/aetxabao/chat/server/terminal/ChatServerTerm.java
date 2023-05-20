package com.aetxabao.chat.server.terminal;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

import static com.aetxabao.chat.server.consts.AppConsts.LISTENING_PORT;
import static com.aetxabao.chat.server.consts.AppConsts.PRIVATE_KEY;

public final class ChatServerTerm {

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private ChannelFuture f;

    private ChatServerHandlerTerm chatServerHandlerTerm;

    public void listen(int port) throws InterruptedException {
        bossGroup = new NioEventLoopGroup(1);
        workerGroup = new NioEventLoopGroup();
        try {
            chatServerHandlerTerm = new ChatServerHandlerTerm(this);
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            p.addLast(new StringDecoder());
                            p.addLast(new StringEncoder());
                            p.addLast(chatServerHandlerTerm);
                        }
                    });
            f = b.bind(port).sync();
        } catch (InterruptedException e) {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            throw e;
        }
    }

    public boolean authenticate(String username, String password) {
        //TODO: authenticate
        return password.equals(PRIVATE_KEY);
    }

    public void loggedIn(boolean b, String host, int port, String username, String result) {
        if (b) {
            System.out.println(host + ":" + port + " LOGIN OK " + username);
        }else {
            System.out.println(host + ":" + port + " LOGIN ERROR");
        }
    }

    public void msgTxtReceived(String host, int port, String username, String txt) {
        System.out.println(host + ":" + port + " " + txt);
    }

    public void disconnected(String host, int port, String username) {
        System.out.println(host + ":" + port + " LOGOUT " + username);
    }

    public void closeSync() throws InterruptedException {
        f.channel().closeFuture().sync();
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

    public static void main(String[] args) throws Exception {
        int port;
        Scanner scanner = new Scanner(System.in);

        ChatServerTerm server = new ChatServerTerm();

        if (args.length == 1) {
            port = Integer.parseInt(args[0]);
        } else {
            System.out.print("Please, type port number [" + LISTENING_PORT + "]: ");
            String s = scanner.nextLine();
            if (s.trim().length()==0) {
                port = LISTENING_PORT;
            }else {
                port = Integer.parseInt(s);
            }
        }

        server.listen(port);
        System.out.println("Listening on port " + port + " ...");

        server.closeSync();
    }

}