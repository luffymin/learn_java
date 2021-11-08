package com.example.http_xml;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.beans.XMLDecoder;
import java.io.ByteArrayInputStream;

public abstract class AbstractHttpXmlDecoder<T> extends MessageToMessageDecoder<T> {

    private Class<?> clazz;

    protected AbstractHttpXmlDecoder(Class<?> clazz) {
        this.clazz = clazz;
    }

    protected Object decode0(ChannelHandlerContext ctx, ByteBuf body) throws Exception {
        ByteArrayInputStream stream = new ByteArrayInputStream(body.array());
        XMLDecoder decoder = new XMLDecoder(stream);
        return decoder.readObject();
    }
}