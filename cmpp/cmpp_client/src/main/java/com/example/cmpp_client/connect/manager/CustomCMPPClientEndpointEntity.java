package com.example.cmpp_client.connect.manager;

import com.zx.sms.connect.manager.cmpp.CMPPClientEndpointEntity;

public class CustomCMPPClientEndpointEntity extends CMPPClientEndpointEntity {

    @Override
    protected CustomCMPPClientEndpointConnector buildConnector() {
        return new CustomCMPPClientEndpointConnector(this);
    }

}