package com.mike.netty.protocol.request;

import com.mike.netty.protocol.Packet;
import lombok.Data;

@Data
public class GroupMessageRequestPacket extends Packet {
    private String toGroupId;
    private String message;
    @Override
    public Byte getCommand() {
        return null;
    }
}
