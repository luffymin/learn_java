package com.example.websocket_to_cmpp.cmpp;

import com.zx.sms.common.GlobalConstance;
import com.zx.sms.connect.manager.EndpointEntity;
import com.zx.sms.connect.manager.cmpp.CMPPClientEndpointConnector;
import com.zx.sms.connect.manager.cmpp.CMPPClientEndpointEntity;
import com.zx.sms.connect.manager.cmpp.CMPPCodecChannelInitializer;
import com.zx.sms.connect.manager.cmpp.CMPPEndpointEntity;

import io.netty.channel.ChannelPipeline;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class AppCmppClientEndpointConnector extends CMPPClientEndpointConnector {

    public AppCmppClientEndpointConnector(CMPPClientEndpointEntity e)
    {
        super(e);
    }

    @Override
    protected void doinitPipeLine(ChannelPipeline pipeline) {
        CMPPCodecChannelInitializer codec = null;
        EndpointEntity entity = getEndpointEntity();
        if (entity instanceof AppCmppClientEndpointEntity) {
            pipeline.addLast(GlobalConstance.IdleCheckerHandlerName,
                    new IdleStateHandler(0, 0, ((AppCmppClientEndpointEntity) getEndpointEntity()).getIdleTimeSec(), TimeUnit.SECONDS));

            codec = new CMPPCodecChannelInitializer(((AppCmppClientEndpointEntity) getEndpointEntity()).getVersion());

        } else {
            pipeline.addLast(GlobalConstance.IdleCheckerHandlerName,
                    new IdleStateHandler(0, 0, 30, TimeUnit.SECONDS));

            codec = new CMPPCodecChannelInitializer();
        }
        pipeline.addLast("CmppServerIdleStateHandler", GlobalConstance.idleHandler);
        pipeline.addLast(codec.pipeName(), codec);
        pipeline.addLast("sessionLoginManager", new AppCmppSessionLoginManager(getEndpointEntity()));
    }
}