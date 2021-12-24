package com.example.cmpp_server.connect.manager;

import com.zx.sms.BaseMessage;
import com.zx.sms.codec.cmpp.msg.CmppSubmitRequestMessage;
import com.zx.sms.codec.cmpp.msg.CmppSubmitResponseMessage;
import com.zx.sms.common.util.ChannelUtil;
import com.zx.sms.common.util.MsgId;
import com.zx.sms.handler.cmpp.CMPPSubmitLongMessageHandler;
import io.netty.channel.ChannelFuture;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.Promise;
import org.marre.sms.SmsMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;


public class CustomCMPPSubmitLongMessageHandler extends CMPPSubmitLongMessageHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomCMPPSubmitLongMessageHandler.class);

    private CustomCMPPServerChildEndpointEntity entity;

    public CustomCMPPSubmitLongMessageHandler(CustomCMPPServerChildEndpointEntity entity) {
        super(entity);
        this.entity = entity;
    }

    @Override
    protected void resetMessageContent(CmppSubmitRequestMessage cmppSubmitRequestMessage, SmsMessage smsMessage) {
        super.resetMessageContent(cmppSubmitRequestMessage, smsMessage);
        logger.info("receive client submit request message, content: {}, destterminalId: {}", cmppSubmitRequestMessage.getMsgContent(), cmppSubmitRequestMessage.getDestterminalId());

        CmppSubmitResponseMessage responseMessage = new CmppSubmitResponseMessage(cmppSubmitRequestMessage.getHeader());
        responseMessage.setResult(0);
        responseMessage.setRequest(cmppSubmitRequestMessage);

        GenericFutureListener listener = future -> {
            if (!future.isSuccess()) {
                logger.error("send summit response message to {} failure, msg id: {}", entity.getUserName(), responseMessage.getMsgId());
            } else {
                logger.info("send summit response message to {} succeed msg id: {}", entity.getUserName(), responseMessage.getMsgId());
            }
        };
        ChannelFuture promise = ChannelUtil.asyncWriteToEntity(entity, responseMessage, listener);
        if (promise == null) {
            logger.error("send summit response message to {} failure, msg id: {}", entity.getUserName(), responseMessage.getMsgId());
        }
    }
}