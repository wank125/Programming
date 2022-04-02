package com.mike.netty.protocol.response;

import com.mike.netty.protocol.Packet;
import com.mike.netty.server.session.Session;
import lombok.Data;

@Data
public class GroupMessageResponsePacket extends Packet {
    private String fromGroupId;
    private String Message;
    private Session fromUser;

    @Override
    public Byte getCommand() {
        return null;
    }
}
