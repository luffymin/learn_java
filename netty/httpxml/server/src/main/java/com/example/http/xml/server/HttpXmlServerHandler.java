package com.example.http.xml.server;

import com.example.http.xml.common.codec.HttpXmlRequest;
import com.example.http.xml.common.codec.HttpXmlResponse;
import com.example.http.xml.common.pojo.Address;
import com.example.http.xml.common.pojo.Order;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;

public class HttpXmlServerHandler extends SimpleChannelInboundHandler<HttpXmlRequest> {

    private static Logger log = LoggerFactory.getLogger(HttpXmlServerHandler.class);

    @Override
    public void messageReceived(final ChannelHandlerContext ctx, HttpXmlRequest xmlRequest) throws Exception {
        HttpRequest request = xmlRequest.getRequest();
        if (request.uri().equals("/do")) {
            Order order = (Order) xmlRequest.getBody();
            log.info("Http server receive request: " + order);

            doBusiness(order);
            ChannelFuture future = ctx.writeAndFlush(new HttpXmlResponse(null, order));
            if (request.headers().get(HttpHeaderNames.CONNECTION) != HttpHeaderValues.KEEP_ALIVE.toString()) {
                future.addListener(new GenericFutureListener<Future<? super Void>>() {
                    public void operationComplete(Future future) throws Exception {
                        ctx.close();
                    }
                });
            }
        }
    }

    private void doBusiness(Order order) {
        order.getCustomer().setFirstName("狄");
        order.getCustomer().setLastName ("仁杰");
        List<String> midNames = new ArrayList<String>();
        midNames.add("李元芳");
        order.getCustomer().setMiddleNames(midNames);
        Address address = order.getBillTo();
        address.setCity("洛阳");
        address.setCountry("大唐");
        address.setState("河南道");
        address.setPostCode("123456");
        order.setBillTo(address);
        order.setShipTo(address);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        if (ctx.channel().isActive()) {
            sendError(ctx, HttpResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private static void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status,
                Unpooled.copiedBuffer("Failure: " + status.toString() + "\r\n", CharsetUtil.UTF_8));
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain; charset=UTF-8");
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }
}