package com.example.cmpp_server.connect.manager;

import com.zx.sms.connect.manager.EndpointEntity;
import com.zx.sms.connect.manager.cmpp.CMPPServerChildEndpointConnector;
import io.netty.channel.ChannelPipeline;

public class CustomCMPPServerChildEndpointConnector extends CMPPServerChildEndpointConnector {

    public CustomCMPPServerChildEndpointConnector(CustomCMPPServerChildEndpointEntity endpoint) {
        super(endpoint);
    }

    @Override
    protected void doBindHandler(ChannelPipeline pipe, EndpointEntity cmppentity) {
        super.doBindHandler(pipe, cmppentity);

        CustomCMPPServerChildEndpointEntity cmppServerChildEndpointEntity = (CustomCMPPServerChildEndpointEntity)cmppentity;
        pipe.replace("CMPPSubmitLongMessageHandler", "CMPPSubmitLongMessageHandler", new CustomCMPPSubmitLongMessageHandler(cmppServerChildEndpointEntity));
        pipe.replace("CmppActiveTestRequestMessageHandler", "CmppActiveTestRequestMessageHandler", new CustomCmppActiveTestRequestMessageHandler(cmppServerChildEndpointEntity));
        pipe.replace("CmppActiveTestResponseMessageHandler", "CmppActiveTestResponseMessageHandler", new CustomCmppActiveTestResponseMessageHandler(cmppServerChildEndpointEntity));
    }
}