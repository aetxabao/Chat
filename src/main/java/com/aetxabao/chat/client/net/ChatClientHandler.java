package com.aetxabao.chat.client.net;

import com.aetxabao.chat.client.model.MsgHandler;
import com.aetxabao.chat.client.model.msg.MsgBase;
import com.aetxabao.chat.client.model.msg.MsgPayload;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import static com.aetxabao.chat.server.consts.AppConsts.*;

public class ChatClientHandler extends SimpleChannelInboundHandler<String> {

    private final ChatClient chatClient;
    public ChatClientHandler(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @Override
    public void channelActive(final ChannelHandlerContext ctx) {
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        chatClient.disconnected();
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, String json) throws Exception {
        MsgBase msg = MsgHandler.fromJson(json);
        switch (msg.getType()) {
            case LOGRES -> logres(ctx.channel(), ((MsgPayload) msg));
            case MSGTXT -> msgTxt(ctx.channel(), ((MsgPayload) msg));
        }
    }

    private void logres(Channel channel, MsgPayload msg) {
        String payload = msg.getPayload();
        if (payload.equals(OK)){
            chatClient.setAuthorized(true, OK);
        }else{
            chatClient.setAuthorized(false, payload);
        }
    }

    private void msgTxt(Channel channel, MsgPayload msg) {
        chatClient.receivedText(msg.getPayload());
    }

}