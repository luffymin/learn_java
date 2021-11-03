package com.example.cmpp_client.connect.manager;

import com.zx.sms.connect.manager.EndpointEntity;
import com.zx.sms.session.cmpp.SessionLoginManager;

public class CustomCMPPSessionLoginManager extends SessionLoginManager {

    public CustomCMPPSessionLoginManager(EndpointEntity entity) {
        super(entity);
    }

}