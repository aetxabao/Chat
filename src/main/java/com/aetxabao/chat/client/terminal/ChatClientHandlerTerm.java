package com.aetxabao.chat.client.terminal;

import com.aetxabao.chat.client.model.MsgHandler;
import com.aetxabao.chat.client.model.msg.MsgBase;
import com.aetxabao.chat.client.model.msg.MsgPayload;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import static com.aetxabao.chat.server.consts.AppConsts.*;

public class ChatClientHandlerTerm extends SimpleChannelInboundHandler<String> {

    private final ChatClientTerm chatClientTerm;

    public ChatClientHandlerTerm(ChatClientTerm chatClientTerm) {
        this.chatClientTerm = chatClientTerm;
    }

    @Override
    public void channelActive(final ChannelHandlerContext ctx) {
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        chatClientTerm.disconnected();
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
        if (payload.equals(OK)) {
            chatClientTerm.setAuthorized(true, OK);
        } else {
            chatClientTerm.setAuthorized(false, payload);
        }
    }

    private void msgTxt(Channel channel, MsgPayload msg) {
        chatClientTerm.receivedText(msg.getPayload());
    }

}