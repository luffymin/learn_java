package com.example.websocket_to_cmpp.cmpp;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import com.example.websocket_to_cmpp.websocket.WebSocketSessionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.primitives.Bytes;
import com.zx.sms.codec.cmpp.msg.*;
import com.zx.sms.common.util.CachedMillisecondClock;
import com.zx.sms.connect.manager.EndpointEntity;
import com.zx.sms.session.cmpp.SessionLoginManager;

public class AppCmppSessionLoginManager extends SessionLoginManager {

    private static final Logger logger = LoggerFactory.getLogger(AppCmppSessionLoginManager.class);

    public AppCmppSessionLoginManager(EndpointEntity entity) {
        super(entity);
    }

    private WebSocketSessionService webSocketSessionService = new WebSocketSessionService();
    private CmppService cmppService = new CmppService();

    @Override
    protected void doLogin(Channel ch) {
        AppCmppClientEndpointEntity cliententity = (AppCmppClientEndpointEntity)entity;
        CmppConnectRequestMessage req = new CmppConnectRequestMessage();
        req.setSourceAddr(cliententity.getUserName());
        String timestamp = DateFormatUtils.format(CachedMillisecondClock.INS.now(), "MMddHHmmss");
        req.setTimestamp(Long.parseLong(timestamp));
        byte[] userBytes = cliententity.getUserName().getBytes(cliententity.getChartset());
        byte[] passwdBytes = cliententity.getPassword().getBytes(cliententity.getChartset());
        byte[] timestampBytes = timestamp.getBytes(cliententity.getChartset());
        req.setAuthenticatorSource(DigestUtils.md5(Bytes.concat(userBytes, new byte[9], passwdBytes, timestampBytes)));
        req.setVersion(cliententity.getVersion());
        ch.writeAndFlush(req);
        logger.info("session Start :Send AppCmppSessionLoginManager seq :{}", req.getHeader().getSequenceId());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);

        if(msg instanceof CmppConnectResponseMessage) {
            CmppConnectResponseMessage resp = (CmppConnectResponseMessage)msg;
            AppCmppClientEndpointEntity cliententity = (AppCmppClientEndpointEntity)entity;
            if(resp.getStatus() != 0) {
                cmppService.removeClient(cliententity.getId());
            }

            ObjectMapper mapper = new ObjectMapper();
            ObjectNode node = mapper.createObjectNode();
            node.put("command", "CMPP_CONNECT_RESP");
            node.put("sequenceId", (new Integer(resp.getSequenceNo())).toString());
            node.put("authenticatorISMG", Hex.encodeHexString(resp.getAuthenticatorISMG()));
            node.put("status", (new Long(resp.getStatus())).toString());
            node.put("version", (new Integer(resp.getVersion() >> 4)).toString());
            String s = mapper.writeValueAsString(node);
            webSocketSessionService.get(cliententity.getWebSocketSessionId()).sendMessage(new TextMessage(s));
        }
        else if(msg instanceof CmppSubmitResponseMessage) {
            CmppSubmitResponseMessage resp = (CmppSubmitResponseMessage)msg;
            AppCmppClientEndpointEntity cliententity = (AppCmppClientEndpointEntity)entity;

            ObjectMapper mapper = new ObjectMapper();
            ObjectNode node = mapper.createObjectNode();
            node.put("command", "CMPP_SUBMIT_RESP");
            node.put("sequenceId", (new Integer(resp.getHeader().getSequenceId())).toString());
            node.put("msgId", resp.getMsgId().toString());
            node.put("result", resp.getResult());
            String s = mapper.writeValueAsString(node);
            webSocketSessionService.get(cliententity.getWebSocketSessionId()).sendMessage(new TextMessage(s));
        }
        else if(msg instanceof  CmppDeliverRequestMessage) {
            System.out.println(msg);
        }
        else if(msg instanceof CmppActiveTestRequestMessage) {
            CmppActiveTestRequestMessage req = (CmppActiveTestRequestMessage)msg;
            CmppActiveTestResponseMessage resp = new CmppActiveTestResponseMessage(req.getHeader().getSequenceId());
            ctx.channel().writeAndFlush(resp);
        }
        else {
            System.out.println(msg.getClass());
        }
    }
}