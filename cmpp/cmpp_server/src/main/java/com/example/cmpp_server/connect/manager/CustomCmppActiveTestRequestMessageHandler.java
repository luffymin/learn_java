package com.example.cmpp_server.connect.manager;

import com.zx.sms.codec.cmpp.msg.CmppActiveTestRequestMessage;
import com.zx.sms.handler.cmpp.CmppActiveTestRequestMessageHandler;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomCmppActiveTestRequestMessageHandler extends CmppActiveTestRequestMessageHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomCMPPSubmitLongMessageHandler.class);

    private CustomCMPPServerChildEndpointEntity entity;

    public CustomCmppActiveTestRequestMessageHandler(CustomCMPPServerChildEndpointEntity entity) {
        this.entity = entity;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CmppActiveTestRequestMessage e) throws Exception {
        logger.info("cmpp active test request, username: {}, seq: {}", entity.getUserName(), e.getSequenceNo());
        super.channelRead0(ctx, e);
    }
}
