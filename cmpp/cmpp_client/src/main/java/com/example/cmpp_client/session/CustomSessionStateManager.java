package com.example.cmpp_client.session;

import com.zx.sms.BaseMessage;
import com.zx.sms.codec.cmpp.msg.Message;
import com.zx.sms.common.storedMap.VersionObject;
import com.zx.sms.connect.manager.EndpointEntity;
import com.zx.sms.session.cmpp.SessionStateManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

import java.util.concurrent.ConcurrentMap;

public class CustomSessionStateManager extends SessionStateManager {

    public CustomSessionStateManager(EndpointEntity entity, ConcurrentMap<Integer, VersionObject<Message>> storeMap, boolean preSend) {
        super(entity, storeMap, preSend);
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object message, ChannelPromise promise) {
        if (message instanceof BaseMessage) {
            BaseMessage msg = (BaseMessage) message;
            ctx.write(msg, promise);
        } else {
            ctx.write(message, promise);
        }
    }

    @Override
    public void channelRead(final ChannelHandlerContext ctx, Object msg) {
        ctx.fireChannelRead(msg);
    }
}

