package com.example.cmpp_server.connect.manager;

import com.zx.sms.connect.manager.cmpp.CMPPServerEndpointEntity;

public class CustomCMPPServerEndpointEntity extends CMPPServerEndpointEntity {

    @Override
    protected CustomCMPPServerEndpointConnector buildConnector() {
        return new CustomCMPPServerEndpointConnector(this);
    }

}