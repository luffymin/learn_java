package com.example.websocket_to_cmpp.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.nio.charset.Charset;
import java.util.*;
import com.example.websocket_to_cmpp.cmpp.AppCmppClientEndpointEntity;
import com.example.websocket_to_cmpp.cmpp.CmppService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zx.sms.connect.manager.EndpointEntity.SupportLongMessage;

@Component
public class AppTextWebSocketHandler extends TextWebSocketHandler {

    @Autowired
    private WebSocketSessionService webSocketSessionService;

    @Autowired
    private CmppService cmppService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        webSocketSessionService.add(session.getId(), session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        webSocketSessionService.remove(session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readValue(message.getPayload(), JsonNode.class);
        String command = node.get("command").asText();
        switch(command){
            case "CMPP_CONNECT":
                AppCmppClientEndpointEntity client = new AppCmppClientEndpointEntity();
                client.setWebSocketSessionId(session.getId());
                client.setId(node.get("id").asText());
                client.setServiceId(node.get("serviceId").asText());
                client.setHost(node.get("host").asText());
                client.setPort(node.get("port").asInt());
                client.setVersion((short)(node.get("version").asInt() << 4));
                client.setChartset(Charset.forName("utf-8"));
                client.setSpCode(node.get("spCode").asText());
                client.setGroupName("client");
                client.setUserName(node.get("userName").asText());
                client.setPassword(node.get("password").asText());
                client.setMaxChannels((short)5);
                client.setUseSSL(false);
                client.setWriteLimit(70);
                client.setReSendFailMsg(false);
                client.setSupportLongmsg(SupportLongMessage.BOTH);
                cmppService.addClient(client);
                break;
            case "CMPP_SUBMIT":
                JsonNode destterminalId = node.get("destterminalId");
                if (destterminalId.isArray()) {
                    List<String> list = new ArrayList<String>();
                    for (JsonNode id : destterminalId) {
                        list.add(id.asText());
                    }
                    cmppService.sendSubimtRequestMessage(node.get("msgContent").asText(), list.toArray(new String[list.size()]));
                }
                break;
            default:
                break;
        }
    }

}