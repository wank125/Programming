package com.mike.netty.protocol;

import lombok.Data;

import static com.mike.netty.protocol.Command.MESSAGE_RESPONSE;

@Data
public class MessageResponsePacket extends Packet {
    private String message;

    @Override
    public Byte getCommand() {
        return MESSAGE_RESPONSE;
    }
}
