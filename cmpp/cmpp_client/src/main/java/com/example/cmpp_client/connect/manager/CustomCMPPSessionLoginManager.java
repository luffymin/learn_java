package com.example.cmpp_client.connect.manager;

import com.example.cmpp_client.CustomProperties;
import com.example.cmpp_client.CustomSpringContextAware;
import com.zx.sms.BaseMessage;
import com.zx.sms.LongSMSMessage;
import com.zx.sms.codec.cmpp.msg.CmppConnectResponseMessage;
import com.zx.sms.codec.cmpp.msg.CmppDeliverRequestMessage;
import com.zx.sms.codec.cmpp.msg.CmppSubmitRequestMessage;
import com.zx.sms.codec.cmpp.msg.CmppSubmitResponseMessage;
import com.zx.sms.codec.cmpp.wap.LongMessageFrame;
import com.zx.sms.codec.cmpp.wap.LongMessageFrameHolder;
import com.zx.sms.connect.manager.EndpointEntity;
import com.zx.sms.session.cmpp.SessionLoginManager;
import com.zx.sms.session.cmpp.SessionState;
import io.netty.channel.ChannelHandlerContext;
import org.marre.sms.SmsMessage;
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

            if (req instanceof LongSMSMessage) {
                LongSMSMessage lmsg = req;
                if (!lmsg.isReport()) {
                    // 长短信拆分
                    SmsMessage msgcontent = lmsg.getSmsMessage();
                    List<LongMessageFrame> frameList = LongMessageFrameHolder.INS.splitmsgcontent(msgcontent);

                    //保证同一条长短信，通过同一个tcp连接发送
                    for (LongMessageFrame frame : frameList) {
                        BaseMessage basemsg = (BaseMessage) lmsg.generateMessage(frame);
                        ctx.writeAndFlush(basemsg);
                    }
                }
            }
            else {
               ctx.writeAndFlush(req);
            }
        }
        else if (msg instanceof CmppSubmitResponseMessage) {
            logger.info("response:{}", msg);
        }
        else if (msg instanceof CmppDeliverRequestMessage) {
            logger.info("response:{}", msg);
        }
    }
}