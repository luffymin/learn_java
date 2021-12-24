package com.example.cmpp_server.connect.manager;

import com.zx.sms.codec.cmpp.msg.CmppActiveTestResponseMessage;
import com.zx.sms.handler.cmpp.CmppActiveTestResponseMessageHandler;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomCmppActiveTestResponseMessageHandler extends CmppActiveTestResponseMessageHandler {
    private static final Logger logger = LoggerFactory.getLogger(CustomCMPPSubmitLongMessageHandler.class);

    private CustomCMPPServerChildEndpointEntity entity;

    public CustomCmppActiveTestResponseMessageHandler(CustomCMPPServerChildEndpointEntity entity) {
        this.entity = entity;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext paramChannelHandlerContext, CmppActiveTestResponseMessage paramI) throws Exception {
        logger.info("cmpp active test response, username: {}, seq: {}", entity.getUserName(), paramI.getSequenceNo());
        super.channelRead0(paramChannelHandlerContext, paramI);
    }
}

