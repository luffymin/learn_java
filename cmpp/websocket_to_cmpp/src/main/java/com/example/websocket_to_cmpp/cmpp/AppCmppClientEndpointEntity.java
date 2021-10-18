package com.example.websocket_to_cmpp.cmpp;

import com.zx.sms.connect.manager.cmpp.CMPPClientEndpointConnector;
import com.zx.sms.connect.manager.cmpp.CMPPClientEndpointEntity;

public class AppCmppClientEndpointEntity extends CMPPClientEndpointEntity {

    private String webSocketSessionId = "";

    @Override
    protected CMPPClientEndpointConnector buildConnector() {
        return new AppCmppClientEndpointConnector(this);
    }

    public String getWebSocketSessionId() {
        return webSocketSessionId;
    }

    public void setWebSocketSessionId(String webSocketSessionId) {
        this.webSocketSessionId = webSocketSessionId;
    }
}
