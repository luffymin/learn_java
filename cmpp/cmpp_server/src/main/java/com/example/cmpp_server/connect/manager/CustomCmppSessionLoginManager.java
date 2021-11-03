package com.example.cmpp_server.connect.manager;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import com.example.cmpp_server.jpa.model.CmppServerChild;
import com.zx.sms.codec.cmpp.msg.CmppConnectRequestMessage;
import com.zx.sms.connect.manager.EndpointEntity;
import com.zx.sms.session.cmpp.SessionLoginManager;
import com.example.cmpp_server.jpa.service.CmppServerChildService;

@Slf4j
public class CustomCmppSessionLoginManager extends SessionLoginManager {

    public CustomCmppSessionLoginManager(EndpointEntity entity) {
        super(entity);
    }

    @Autowired
    private CmppServerChildService cmppServerChildService;

    @Override
    protected EndpointEntity queryEndpointEntityByMsg(Object msg) {
        if (msg instanceof CmppConnectRequestMessage) {
            CmppConnectRequestMessage message = (CmppConnectRequestMessage)msg;
            String username = message.getSourceAddr();
            short version = message.getVersion();
            List<CmppServerChild> list = cmppServerChildService.findByUserName(username);
            if(list.size() != 1) {
                log.info();
            }
        }

        return null;
    }

}