package com.example.websocket_to_cmpp.cmpp;

import com.google.common.primitives.Bytes;
import com.zx.sms.codec.cmpp.msg.CmppConnectRequestMessage;
import com.zx.sms.common.util.CachedMillisecondClock;
import com.zx.sms.connect.manager.ClientEndpoint;
import com.zx.sms.connect.manager.EndpointEntity;
import com.zx.sms.connect.manager.cmpp.CMPPEndpointEntity;
import com.zx.sms.session.cmpp.SessionLoginManager;
import com.zx.sms.session.cmpp.SessionState;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppCmppSessionLoginManager extends SessionLoginManager {

    private static final Logger logger = LoggerFactory.getLogger(AppCmppSessionLoginManager.class);

    public AppCmppSessionLoginManager(EndpointEntity entity) {
        super(entity);
    }

    @Override
    protected void doLogin(Channel ch) {
        AppCmppClientEndpointEntity cliententity = (AppCmppClientEndpointEntity) entity;
        CmppConnectRequestMessage req = new CmppConnectRequestMessage();
        req.setSourceAddr(cliententity.getUserName());
        String timestamp = DateFormatUtils.format(CachedMillisecondClock.INS.now(), "MMddHHmmss");
        req.setTimestamp(Long.parseLong(timestamp));
        byte[] userBytes = cliententity.getUserName().getBytes(cliententity.getChartset());
        byte[] passwdBytes = cliententity.getPassword().getBytes(cliententity.getChartset());
        byte[] timestampBytes = timestamp.getBytes(cliententity.getChartset());
        req.setAuthenticatorSource(DigestUtils.md5(Bytes.concat(userBytes, new byte[9], passwdBytes, timestampBytes)));
        req.setVersion(cliententity.getVersion());
        ch.writeAndFlush(req);
        logger.info("session Start :Send AppCmppSessionLoginManager seq :{}", req.getHeader().getSequenceId());
    }

    @Override
    protected void doLoginSuccess(ChannelHandlerContext ctx, EndpointEntity entity, Object msg) {
        //super.doLoginSuccess(ctx, entity, msg);
    }

    @Override
    protected void failedLogin(ChannelHandlerContext ctx, Object msg, long status) {
        //super.failedLogin(ctx, msg, status);
        //System.out.println("test");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("test");
        ctx.fireChannelRead(msg);
    }
}