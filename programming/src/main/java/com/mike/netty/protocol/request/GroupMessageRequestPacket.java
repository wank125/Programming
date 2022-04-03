package com.mike.netty.protocol.request;

import com.mike.netty.protocol.Packet;
import lombok.Data;

import static com.mike.netty.protocol.Command.GROUP_MESSAGE_REQUEST;

@Data
public class GroupMessageRequestPacket extends Packet {

    private String toGroupId;
    private String message;

    public GroupMessageRequestPacket(String toGroupId, String message) {
        this.toGroupId = toGroupId;
        this.message = message;
    }

    @Override
    public Byte getCommand() {
        return GROUP_MESSAGE_REQUEST;
    }
}
