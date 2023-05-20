package com.aetxabao.chat.server.net;

import com.aetxabao.chat.server.controllers.ServerController;
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

public final class ChatServer {

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private ChannelFuture f;
    private ChatServerHandler chatServerHandler;

    private final  ServerController serverController;

    public ChatServer(ServerController serverController) {
        this.serverController = serverController;
    }

    public void listen(int port) throws InterruptedException {
        bossGroup = new NioEventLoopGroup(1);
        workerGroup = new NioEventLoopGroup();
        try {
            chatServerHandler = new ChatServerHandler(this);
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            p.addLast(new StringDecoder());
                            p.addLast(new StringEncoder());
                            p.addLast(chatServerHandler);
                        }
                    });
            f = b.bind(port).sync();
        } catch (InterruptedException e) {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            throw e;
        }
    }

    public String signup(String username, String password) {
        return serverController.signup(username, password);
    }

    public boolean authenticate(String username, String password) {
        return serverController.authenticate(username, password);
    }

    public void loggedIn(boolean b, String host, int port, String username, String result) {
        serverController.loggedIn(b, host, port, username, result);
    }

    public void msgTxtReceived(String host, int port, String username, String txt) {
        serverController.msgTxtReceived(host, port, username, txt);
    }

    public void disconnected(String host, int port, String username) {
        serverController.disconnected(host, port, username);
    }

    public void close() {
        f.channel().closeFuture();
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

}