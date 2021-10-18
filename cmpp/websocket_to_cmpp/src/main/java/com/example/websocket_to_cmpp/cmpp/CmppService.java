package com.example.websocket_to_cmpp.cmpp;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.Promise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import com.zx.sms.BaseMessage;
import com.zx.sms.codec.cmpp.msg.CmppSubmitRequestMessage;
import com.zx.sms.common.util.ChannelUtil;
import com.zx.sms.connect.manager.EndpointEntity;
import com.zx.sms.connect.manager.EndpointManager;

@Service
public class CmppService {

    private static final Logger logger = LoggerFactory.getLogger(CmppService.class);
    private static EndpointManager manager = EndpointManager.INS;

    public void addClient(AppCmppClientEndpointEntity client) {
        manager.addEndpointEntity(client);
        manager.openEndpoint(client);
    }

    public void removeClient(String id) {
        manager.remove(id);
    }

    public void sendSubimtRequestMessage(String msgContent, String[] destterminalId) throws Exception {
        CmppSubmitRequestMessage msg = new CmppSubmitRequestMessage();
        msg.setMsgContent(msgContent);
        msg.setDestterminalId(destterminalId);
        msg.setRegisteredDelivery((short)1);
        msg.setFeeterminaltype((short)0);
        msg.setFeeUserType((short)2);

        Set<EndpointEntity> endpoints = manager.allEndPointEntity();
        ArrayList<EndpointEntity> list = new ArrayList<>(endpoints);
        if(list.size() > 0) {
            int random = new Random().nextInt(list.size());
            EndpointEntity endpoint = list.get(random);
            msg.setServiceId(((AppCmppClientEndpointEntity)endpoint).getServiceId());
            msg.setMsgsrc(((AppCmppClientEndpointEntity)endpoint).getMsgSrc());
            msg.setSrcId(((AppCmppClientEndpointEntity)endpoint).getSpCode());

            List<Promise<BaseMessage>> promises = ChannelUtil.syncWriteLongMsgToEntity(endpoint.getId(), msg);
            for(Promise  promise: promises){
                promise.addListener(new GenericFutureListener<Future<BaseMessage>>() {
                    @Override
                    public void operationComplete(Future future) throws Exception {
                        if(future.isSuccess()){
                            logger.info("response:{}",future.get());
                        }else{
                            logger.error("response:{}",future.cause());
                        }
                    }
                });
            }
        }
    }
}