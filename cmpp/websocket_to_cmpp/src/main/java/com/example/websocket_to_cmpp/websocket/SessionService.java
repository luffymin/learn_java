package com.example.websocket_to_cmpp.websocket;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SessionService {

    private static Map<String, WebSocketSession> sessionMap = new ConcurrentHashMap<String, WebSocketSession>();

    public void add(String id, WebSocketSession session) {
        sessionMap.put(id, session);
    }

    public void remove(String id) {
        sessionMap.remove(id);
    }

    public WebSocketSession get(String id) {
        return sessionMap.get(id);
    }

}