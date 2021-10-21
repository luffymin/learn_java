package com.example.cmpp_server.connect.manager;

import com.zx.sms.codec.cmpp.msg.CmppConnectRequestMessage;
import com.zx.sms.connect.manager.EndpointEntity;
import com.zx.sms.session.cmpp.SessionLoginManager;

public class CustomCmppSessionLoginManager extends SessionLoginManager {

    public CustomCmppSessionLoginManager(EndpointEntity entity) {
        super(entity);
    }

    @Override
    protected EndpointEntity queryEndpointEntityByMsg(Object msg) {
        if (msg instanceof CmppConnectRequestMessage) {
            CmppConnectRequestMessage message = (CmppConnectRequestMessage)msg;
            String username = message.getSourceAddr();
            short version = message.getVersion();

        }

        return null;
    }

}