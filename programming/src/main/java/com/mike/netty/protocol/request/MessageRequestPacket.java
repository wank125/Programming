package com.mike.netty.protocol.request;

import com.mike.netty.protocol.Packet;
import lombok.Data;

import static com.mike.netty.protocol.Command.MESSAGE_REQUEST;

@Data
public class MessageRequestPacket extends Packet {
    public MessageRequestPacket(String message) {
        this.message = message;
    }

    public MessageRequestPacket(String message, String toUserId) {
        this.message = message;
        this.toUserId = toUserId;
    }

    private String message;
    private String toUserId;

    @Override
    public Byte getCommand() {
        return MESSAGE_REQUEST;
    }

}
