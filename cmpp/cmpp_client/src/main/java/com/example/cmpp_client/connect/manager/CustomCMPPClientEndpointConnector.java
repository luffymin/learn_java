package com.example.cmpp_client.connect.manager;

import com.zx.sms.connect.manager.cmpp.CMPPClientEndpointConnector;
import io.netty.channel.ChannelPipeline;

public class CustomCMPPClientEndpointConnector extends CMPPClientEndpointConnector {

    public CustomCMPPClientEndpointConnector(CustomCMPPClientEndpointEntity e)
    {
        super(e);
    }

    @Override
    protected void doinitPipeLine(ChannelPipeline pipeline) {
        super.doinitPipeLine(pipeline);
        pipeline.remove("sessionLoginManager");
        pipeline.addLast("sessionLoginManager", new CustomCMPPSessionLoginManager(getEndpointEntity()));
    }

}