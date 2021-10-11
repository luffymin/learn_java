package com.example.websocket_to_cmpp.cmpp;

import org.springframework.stereotype.Service;
import com.zx.sms.connect.manager.EndpointManager;
import com.zx.sms.connect.manager.cmpp.CMPPClientEndpointEntity;

@Service
public class CmppService {

    private static EndpointManager manager = EndpointManager.INS;

    public void addClient(CMPPClientEndpointEntity client) {
        manager.addEndpointEntity(client);
        manager.openEndpoint(client);
    }

}