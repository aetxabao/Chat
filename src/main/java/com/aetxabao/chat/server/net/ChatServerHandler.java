package com.aetxabao.chat.server.net;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class ChatServerHandler extends SimpleChannelInboundHandler<String> {

    private final ChatServer chatServer;

    public ChatServerHandler(ChatServer chatServer) {
        this.chatServer = chatServer;
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, String json) throws Exception {
    }

}