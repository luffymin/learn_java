package com.example.udp.proto.server;

import com.example.udp.proto.common.msg.Message;
import com.example.udp.proto.common.protobuf.UserNameProto;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UdpProtoServerChannelInitializer extends SimpleChannelInboundHandler<DatagramPacket> {

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, DatagramPacket packet) throws Exception {
        byte[] bytes = new byte[packet.content().readableBytes()];
        packet.content().getBytes(0, bytes);

        Message req = new Message(bytes);
        if(req.getCommandId() == Message.USERNAME_REQUEST) {
            byte[] bodyBuffer = req.getBodyBuffer();

            int code = 0;
            try {
                UserNameProto.UserName userName = UserNameProto.UserName.parseFrom(bodyBuffer);
                if(!userName.getUsername().isEmpty()) {
                    log.info("user name: {}", userName.getUsername());
                }
                else {
                    code = 2;
                }
            } catch (InvalidProtocolBufferException e) {
                e.printStackTrace();
                code = 1;
            }

            Message resp = new Message(Message.USERNAME_RESPONSE, req.getSequenceNo());
            UserNameProto.UserNameResp.Builder builder = UserNameProto.UserNameResp.newBuilder();
            builder.setCode(code).build();
            UserNameProto.UserNameResp userNameResp = builder.build();
            byte[] respBodyBuffer = userNameResp.toByteArray();
            resp.setBodyBuffer(respBodyBuffer);
            DatagramPacket respPacket = new DatagramPacket(Unpooled.copiedBuffer(resp.getBuffer()), packet.sender());
            ctx.writeAndFlush(respPacket);
        }
        else {
            log.info("commandId: {}", req.getCommandId());
        }
    }
}
