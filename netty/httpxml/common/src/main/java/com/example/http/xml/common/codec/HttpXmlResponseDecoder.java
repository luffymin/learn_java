package com.example.http.xml.common.codec;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpResponse;

import java.util.List;

public class HttpXmlResponseDecoder extends AbstractHttpXmlDecoder<FullHttpResponse> {

    public HttpXmlResponseDecoder(Class<?> clazz) {
        super(clazz);
    }

    @Override
    protected  void decode(ChannelHandlerContext ctx, FullHttpResponse resp, List<Object> out) throws Exception {
        HttpXmlResponse response = new HttpXmlResponse(resp, decode0(ctx, resp.content()));
        out.add(response);
    }
}
