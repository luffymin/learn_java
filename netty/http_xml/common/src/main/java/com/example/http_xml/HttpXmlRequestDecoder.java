package com.example.http_xml_server;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.util.List;

public class HttpXmlRequestDecoder extends AbstractHttpXmlDecoder<FullHttpRequest> {

    public HttpXmlRequestDecoder(Class<?> clazz) {
        super(clazz);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, FullHttpRequest req, List<Object> out) throws Exception {
        if (!req.decoderResult().isSuccess()) {
            sendError(ctx, HttpResponseStatus.BAD_REQUEST);
            return;
        }
        HttpXmlRequest request = new HttpXmlRequest(req, super.decode0(ctx, req.content()));
        out.add(request);
    }

    private static void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                status, Unpooled.copiedBuffer("Failure: " + status.toString()
                + "\r\n", CharsetUtil.UTF_8));
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain; charset=UTF-8");
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }
}
