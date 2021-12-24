package com.example.cmpp_client.connect.manager;

import com.example.cmpp_client.session.CustomSessionStateManager;
import com.zx.sms.connect.manager.EndpointEntity;
import com.zx.sms.connect.manager.cmpp.CMPPClientEndpointConnector;
import com.zx.sms.session.AbstractSessionStateManager;
import io.netty.channel.ChannelPipeline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentMap;

public class CustomCMPPClientEndpointConnector extends CMPPClientEndpointConnector {

    private static final Logger logger = LoggerFactory.getLogger(CustomCMPPSessionLoginManager.class);

    public CustomCMPPClientEndpointConnector(CustomCMPPClientEndpointEntity e) {
        super(e);
    }

    @Override
    protected void doinitPipeLine(ChannelPipeline pipeline) {
        super.doinitPipeLine(pipeline);
        pipeline.replace("sessionLoginManager", "sessionLoginManager", new CustomCMPPSessionLoginManager(getEndpointEntity()));
    }

    @Override
    protected AbstractSessionStateManager createSessionManager(EndpointEntity entity, ConcurrentMap storeMap, boolean preSend) {
        return new CustomSessionStateManager(entity, storeMap, preSend);
    }
}