package com.example.udp.proto.common.msg;

import jdk.javadoc.internal.doclets.formats.html.markup.Head;

import java.nio.ByteBuffer;

public class Message implements Cloneable {

    public static final Integer USERNAME_REQUEST = 0x00000001;
    public static final Integer USERNAME_RESPONSE = 0x80000001;

    private Header header;
    private byte[] buffer;

    public Message() {
        header = new Header();
        buffer = null;
    };

    public Message(int commandId, int sequenceId) {
        Header header = new Header();
        header.setSequenceId(sequenceId);
        header.setCommandId(commandId);
        setHeader(header);
        buffer = null;
    };

    public Message(byte[] buffer) {
        assert buffer.length >= Header.HEADER_LENGTH;

        byte[] intBytes = new byte[4];
        System.arraycopy(buffer, 0, intBytes, 0, 4);
        int packetLength =  ByteBuffer.wrap(intBytes).getInt();
        System.arraycopy(buffer, 4, intBytes, 0, 4);
        int commandId = ByteBuffer.wrap(intBytes).getInt();
        System.arraycopy(buffer, 8, intBytes, 0, 4);
        int sequenceId = ByteBuffer.wrap(intBytes).getInt();
        Header header = new Header();
        header.setPacketLength(packetLength);
        header.setCommandId(commandId);
        header.setSequenceId(sequenceId);
        setHeader(header);

        byte[] bodyBuffer = new byte[buffer.length - Header.HEADER_LENGTH];
        System.arraycopy(buffer, Header.HEADER_LENGTH, bodyBuffer, 0, buffer.length - Header.HEADER_LENGTH);
        setBodyBuffer(bodyBuffer);
    };

    public void setCommandId(int commandId) {
        header.setCommandId(commandId);
    }

    public int getCommandId() {
        return header.getCommandId();
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Header getHeader() {
        return header;
    }

    public void setBodyBuffer(byte[] buffer) {
        this.buffer = buffer;
    }

    public byte[] getBodyBuffer() {
        return buffer;
    }

    public byte[] getBuffer() {
        byte[] b1 = ByteBuffer.allocate(4).putInt(header.getPacketLength()).array();
        byte[] b2 = ByteBuffer.allocate(4).putInt(header.getCommandId()).array();
        byte[] b3 = ByteBuffer.allocate(4).putInt(header.getSequenceId()).array();
        byte[] bytes = null;
        if (buffer != null) {
            bytes = new byte[b1.length + b2.length + b3.length + buffer.length];
        }
        else {
            bytes = new byte[b1.length + b2.length + b3.length];
        }
        System.arraycopy(b1, 0, bytes, 0, b1.length);
        System.arraycopy(b2, 0, bytes, b1.length, b2.length);
        System.arraycopy(b3, 0, bytes, b1.length + b2.length, b3.length);
        if (buffer != null) {
            System.arraycopy(buffer, 0, bytes, b1.length + b2.length + b3.length, buffer.length);
        }
        return bytes;
    }

    public int getSequenceNo() {
        return getHeader().getSequenceId();
    }

    public void setSequenceNo(int seq) {
        getHeader().setSequenceId(seq);
    }
}