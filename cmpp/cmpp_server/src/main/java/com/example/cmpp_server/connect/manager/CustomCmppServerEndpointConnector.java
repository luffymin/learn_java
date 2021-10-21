package com.example.cmpp_server.connect.manager;

import io.netty.channel.ChannelPipeline;
import com.zx.sms.connect.manager.cmpp.CMPPServerEndpointConnector;
import com.zx.sms.connect.manager.cmpp.CMPPServerEndpointEntity;

public class CustomCmppServerEndpointConnector extends CMPPServerEndpointConnector {

    public CustomCmppServerEndpointConnector(CMPPServerEndpointEntity e)
    {
        super(e);
    }

    @Override
    protected void doinitPipeLine(ChannelPipeline pipeline) {
        super.doinitPipeLine(pipeline);
        pipeline.remove("sessionLoginManager");
        pipeline.addLast("sessionLoginManager", new CustomCmppSessionLoginManager(this.getEndpointEntity()));
    }
}