package com.example.websocket_to_cmpp;

import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.websocket_to_cmpp.websocket.SubmitHandler;

@Configuration
public class WebSocketToCmppConfiguration {
    
    @Bean
    WebSocketConfigurer createWebSocketConfigurer(@Autowired SubmitHandler submitHandler)
    {
        return new WebSocketConfigurer() {
            public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
                registry.addHandler(submitHandler, "/submit");
            }
        };
    }

}