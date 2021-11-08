package com.example.http_xml_server;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;

import java.util.List;

public class HttpXmlResponseDecoder extends AbstractHttpXmlDecoder<DefaultFullHttpResponse> {

    public HttpXmlResponseDecoder(Class<?> clazz) {
        super(clazz);
    }

    @Override
    protected  void decode(ChannelHandlerContext ctx, DefaultFullHttpResponse msg, List<Object> out) throws Exception {
        HttpXmlResponse response = new HttpXmlResponse(msg, decode0(ctx, msg.content()));
        out.add(response);
    }
}
