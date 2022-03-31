package com.mike.netty.protocol;

import lombok.Data;

import static com.mike.netty.protocol.Command.MESSAGE_REQUEST;

@Data
public class MessageRequestPacket extends Packet {
    private String message;

    @Override
    public Byte getCommand() {
        return MESSAGE_REQUEST;
    }

}
