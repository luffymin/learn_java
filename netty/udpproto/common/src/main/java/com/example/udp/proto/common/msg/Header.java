package com.example.udp.proto.common.msg;

import java.io.Serializable;

public class Header implements Serializable {
    private int headLength;
    private int packetLength;
    private int bodyLength;
    private int commandId;
    private int sequenceId;

    public static final Integer HEADER_LENGTH = 12;

    public void setHeadLength(int length) {
        this.headLength = length;
    }

    public int getHeadLength() {
        return headLength;
    }

    public void setPacketLength(int length) {
        this.packetLength = length;
        this.bodyLength = length - 12;
    }

    public int getPacketLength() {
        return packetLength;
    }

    public void setBodyLength(int length) {
        this.bodyLength = length;
        this.packetLength = length + 12;
    }

    public long getBodyLength() {
        return bodyLength;
    }

    public void setCommandId(int commandId) {
        this.commandId = commandId;
    }

    public int getCommandId() {
        return commandId;
    }

    public void setSequenceId(int sequenceId) {
        this.sequenceId = sequenceId;
    }

    public int getSequenceId() {
        return sequenceId;
    }

}