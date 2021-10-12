package com.example.websocket_to_cmpp.cmpp;

import com.zx.sms.connect.manager.cmpp.CMPPClientEndpointConnector;
import com.zx.sms.connect.manager.cmpp.CMPPClientEndpointEntity;

public class AppCmppClientEndpointEntity extends CMPPClientEndpointEntity {

    @Override
    protected CMPPClientEndpointConnector buildConnector() {
        return new AppCmppClientEndpointConnector(this);
    }
}
