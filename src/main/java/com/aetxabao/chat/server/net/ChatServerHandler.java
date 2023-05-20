package com.aetxabao.chat.server.net;

import com.aetxabao.chat.server.model.MsgHandler;
import com.aetxabao.chat.server.model.msg.MsgBase;
import com.aetxabao.chat.server.model.msg.MsgLogin;
import com.aetxabao.chat.server.model.msg.MsgPayload;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.aetxabao.chat.server.consts.AppConsts.*;

@ChannelHandler.Sharable
public class ChatServerHandler extends SimpleChannelInboundHandler<String> {

    private final ChatServer chatServer;
    private static final List<Channel> conChannels = new ArrayList<>();
    private static final Map<Channel, String> mapChannelsUsernames = new HashMap<>();


    public ChatServerHandler(ChatServer chatServer) {
        this.chatServer = chatServer;
    }


    @Override
    public void channelActive(final ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        conChannels.add(channel);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        Channel channel = ctx.channel();
        conChannels.remove(channel);
        if (mapChannelsUsernames.containsKey(channel)) {
            String username = mapChannelsUsernames.get(channel);
            mapChannelsUsernames.remove(channel);
            String host = ((InetSocketAddress)channel.remoteAddress()).getAddress().getHostAddress();
            int port = ((InetSocketAddress)channel.remoteAddress()).getPort();
            chatServer.disconnected(host, port, username);
        }
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, String json) throws Exception {
        MsgBase msg = MsgHandler.fromJson(json);
        switch (msg.getType()) {
            case SIGNUP -> signup(ctx.channel(), ((MsgLogin) msg));
            case LOGIN -> login(ctx.channel(), ((MsgLogin) msg));
            case MSGTXT -> msgTxtReception(ctx.channel(), ((MsgPayload) msg));
        }
    }

    private void signup(Channel channel, MsgLogin msg) throws JsonProcessingException {
        String username = msg.getAuthentication().getUsername();
        String password = msg.getAuthentication().getPassword();
        String result = chatServer.signup(username, password);
        MsgPayload msgRes = new MsgPayload(SIGNUP, result);
        channel.writeAndFlush(msgRes.toJson());
    }

    private void login(Channel channel, MsgLogin msg) throws JsonProcessingException {
        MsgPayload msgRes;
        String result;
        String username = msg.getAuthentication().getUsername();
        String password = msg.getAuthentication().getPassword();
        boolean b = authenticate(username, password);
        if (b) {
            msgRes = new MsgPayload(LOGRES, OK);
            conChannels.remove(channel);
            channel.writeAndFlush(msgRes.toJson());
            mapChannelsUsernames.put(channel, username);
            result = OK;
        }else{
            msgRes = new MsgPayload(LOGRES, ERROR_USERNAME_PASSWORD);
            channel.writeAndFlush(msgRes.toJson());
            result = ERROR_USERNAME_PASSWORD;
        }
        String host = ((InetSocketAddress)channel.remoteAddress()).getAddress().getHostAddress();
        int port = ((InetSocketAddress)channel.remoteAddress()).getPort();
        chatServer.loggedIn(b, host, port, username, result);
    }

    private boolean authenticate(String username, String password) {
        return chatServer.authenticate(username, password);
    }

    private void msgTxtReception(Channel channel, MsgPayload msg) {
        if (mapChannelsUsernames.containsKey(channel)) {
            String json = "";
            String username = mapChannelsUsernames.get(channel);
            String txt = msg.getPayload();
            MsgPayload msgPayload = new MsgPayload(MSGTXT,  txt);
            try {
                json = msgPayload.toJson();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            for (Channel c : mapChannelsUsernames.keySet()) {
                c.writeAndFlush(json);
            }
            String host = ((InetSocketAddress)channel.remoteAddress()).getAddress().getHostAddress();
            int port = ((InetSocketAddress)channel.remoteAddress()).getPort();
            chatServer.msgTxtReceived(host, port, username, txt);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }

}