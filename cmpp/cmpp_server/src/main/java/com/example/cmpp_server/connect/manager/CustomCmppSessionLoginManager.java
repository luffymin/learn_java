package com.example.cmpp_server.connect.manager;

import com.example.cmpp_server.CustomSpringContextAware;
import com.zx.sms.codec.cmpp.msg.CmppConnectRequestMessage;
import com.zx.sms.connect.manager.EndpointEntity;
import com.zx.sms.session.cmpp.SessionLoginManager;
import lombok.extern.slf4j.Slf4j;
import java.nio.charset.Charset;
import com.example.cmpp_server.CustomClientProperties;


@Slf4j
class CustomCMPPSessionLoginManager extends SessionLoginManager {

    public CustomCMPPSessionLoginManager(EndpointEntity entity) {
        super(entity);
    }

    @Override
    protected EndpointEntity queryEndpointEntityByMsg(Object msg) {
        CustomClientProperties properties = CustomSpringContextAware.getBean(CustomClientProperties.class);

        if (msg instanceof CmppConnectRequestMessage) {
            CmppConnectRequestMessage message = (CmppConnectRequestMessage)msg;
            short version = message.getVersion();

            CustomCMPPServerChildEndpointEntity entity = new CustomCMPPServerChildEndpointEntity();
            entity.setId(String.format(properties.getUserName()));
            entity.setMsgSrc(properties.getMsgSrc());
            entity.setSpCode(properties.getSpCode());
            entity.setServiceId(properties.getServiceId());
            entity.setChartset(Charset.forName("utf-8"));
            entity.setGroupName(properties.getGroupName());
            entity.setUserName(properties.getUserName());
            entity.setPassword(properties.getPassword());
            entity.setVersion(version);
            entity.setSupportLongmsg(EndpointEntity.SupportLongMessage.BOTH);
            entity.setLiftTime(30 * 1000);
            entity.setReSendFailMsg(false);
            return entity;
        }

        return null;
    }

}