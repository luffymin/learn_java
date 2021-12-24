package com.example.cmpp_server.connect.manager;

import com.zx.sms.connect.manager.cmpp.CMPPServerEndpointConnector;
import io.netty.channel.ChannelPipeline;

public class CustomCMPPServerEndpointConnector extends CMPPServerEndpointConnector {

    public CustomCMPPServerEndpointConnector(CustomCMPPServerEndpointEntity e) {
        super(e);
    }

    @Override
    protected void doinitPipeLine(ChannelPipeline pipeline) {
        super.doinitPipeLine(pipeline);
        pipeline.replace("sessionLoginManager", "sessionLoginManager", new CustomCMPPSessionLoginManager(this.getEndpointEntity()));
    }

}