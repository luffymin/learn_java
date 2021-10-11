package com.example.websocket_to_cmpp.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.zx.sms.connect.manager.cmpp.CMPPClientEndpointEntity;
import com.zx.sms.connect.manager.EndpointEntity.SupportLongMessage;
import com.example.websocket_to_cmpp.cmpp.CmppService;

import java.util.Map;
import java.nio.charset.Charset;


@Component
public class CmppHandler extends TextWebSocketHandler {

    private SessionService sessionService;
    private CmppService cmppService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessionService.add(session.getId(), session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessionService.remove(session.getId());
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        final Map map = mapper.readValue(message.getPayload(), Map.class);
        String command = map.get("command").toString();
        switch(command){
            case "CMPP_CONNECT":
                CMPPClientEndpointEntity client = new CMPPClientEndpointEntity();
                client.setId(map.get("id").toString());
                client.setServiceId(map.get("serviceId").toString());
                client.setHost(map.get("host").toString());
                client.setPort(Integer.parseInt(map.get("port").toString()));
                client.setChartset(Charset.forName("utf-8"));
                client.setSpCode(map.get("spCode").toString());
                client.setGroupName("client");
                client.setUserName(map.get("userName").toString());
                client.setPassword(map.get("password").toString());

                client.setMaxChannels((short)5);
                client.setVersion((short)(Integer.parseInt(map.get("version").toString()) << 8));
                client.setRetryWaitTimeSec((short)30);
                client.setUseSSL(false);
                client.setWriteLimit(70);
                client.setReSendFailMsg(true);
                client.setSupportLongmsg(SupportLongMessage.BOTH);
                cmppService.addClient(client);
                break;
            default:
                break;
        }
    }

}