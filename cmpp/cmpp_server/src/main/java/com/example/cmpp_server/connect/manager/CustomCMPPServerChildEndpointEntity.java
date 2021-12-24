package com.example.cmpp_server.connect.manager;

import com.zx.sms.connect.manager.cmpp.CMPPServerChildEndpointEntity;

public class CustomCMPPServerChildEndpointEntity extends CMPPServerChildEndpointEntity {

    @Override
    protected CustomCMPPServerChildEndpointConnector buildConnector() {
        return new CustomCMPPServerChildEndpointConnector(this);
    }
}