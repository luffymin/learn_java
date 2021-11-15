package com.example.http.xml.common.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import java.util.List;

public class HttpXmlResponseEncoder extends AbstractHttpXmlEncoder<HttpXmlResponse> {

    @Override
    protected void encode(ChannelHandlerContext ctx, HttpXmlResponse resp, List<Object> out) throws Exception {
        ByteBuf body = encode0(ctx, resp.getResult());
        FullHttpResponse response = resp.getHttpResponse();
        if (response == null) {
            response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, body);
        } else {
            response = new DefaultFullHttpResponse(resp.getHttpResponse().protocolVersion(), resp.getHttpResponse().status(), body);
        }
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/xml");
        response.headers().setInt(HttpHeaderNames.CONTENT_LENGTH, body.readableBytes());
        out.add(response);
    }
}
