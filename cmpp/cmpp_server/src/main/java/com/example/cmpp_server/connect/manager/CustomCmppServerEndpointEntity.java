package com.example.cmpp_server.connect.manager;

import com.zx.sms.connect.manager.cmpp.CMPPServerEndpointConnector;
import com.zx.sms.connect.manager.cmpp.CMPPServerEndpointEntity;

public class CustomCmppServerEndpointEntity extends CMPPServerEndpointEntity {

    @Override
    protected CMPPServerEndpointConnector buildConnector() {
        return new CustomCmppServerEndpointConnector(this);
    }

}