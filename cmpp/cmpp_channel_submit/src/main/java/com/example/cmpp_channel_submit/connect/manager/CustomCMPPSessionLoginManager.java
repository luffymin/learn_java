package com.example.cmpp_client.connect.manager;

import com.example.cmpp_client.CustomProperties;
import com.example.cmpp_client.CustomSpringContextAware;
import com.zx.sms.BaseMessage;
import com.zx.sms.codec.cmpp.msg.CmppConnectResponseMessage;
import com.zx.sms.codec.cmpp.msg.CmppDeliverRequestMessage;
import com.zx.sms.codec.cmpp.msg.CmppSubmitRequestMessage;
import com.zx.sms.codec.cmpp.msg.CmppSubmitResponseMessage;
import com.zx.sms.common.util.ChannelUtil;
import com.zx.sms.connect.manager.EndpointEntity;
import com.zx.sms.session.cmpp.SessionLoginManager;
import com.zx.sms.session.cmpp.SessionState;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.Promise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CustomCMPPSessionLoginManager extends SessionLoginManager {

    private static final Logger logger = LoggerFactory.getLogger(CustomCMPPSessionLoginManager.class);

    public CustomCMPPSessionLoginManager(EndpointEntity entity) {
        super(entity);
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
        if ((msg instanceof CmppConnectResponseMessage) && (this.state == SessionState.Connect)) {
            CustomProperties properties = CustomSpringContextAware.getBean(CustomProperties.class);
            String msgContent = properties.getMsgContent();
            String destterminalId = properties.getDestterminalId();

            logger.info("start send submit request, msgContent: {}, destterminalId: {}", msgContent, destterminalId);
            CmppSubmitRequestMessage req = new CmppSubmitRequestMessage();
            req.setMsgContent(msgContent);
            req.setDestterminalId(destterminalId);
            req.setRegisteredDelivery((short) 1);
            req.setFeeterminaltype((short) 0);
            req.setFeeUserType((short) 2);

            req.setServiceId(((CustomCMPPClientEndpointEntity)entity).getServiceId());
            req.setMsgsrc(((CustomCMPPClientEndpointEntity)entity).getMsgSrc());
            req.setSrcId(((CustomCMPPClientEndpointEntity)entity).getSpCode());

            List<Promise<BaseMessage>> promises = ChannelUtil.syncWriteLongMsgToEntity(entity.getId(), req);
            for(Promise  promise: promises) {
                promise.addListener(new GenericFutureListener<Future<BaseMessage>>() {
                    @Override
                    public void operationComplete(Future future) throws Exception {
                        if(future.isSuccess()){
                            logger.info("response:{}", future.get());
                        }else{
                            logger.error("response:{}", future.cause());
                        }
                    }
                });
            }
        }
        else if (msg instanceof CmppSubmitResponseMessage) {
            logger.info("response:{}", (CmppSubmitResponseMessage)msg);
        }
        else if (msg instanceof CmppDeliverRequestMessage) {
            logger.info("response:{}", (CmppDeliverRequestMessage)msg);
            System.exit(0);
        }
    }
}