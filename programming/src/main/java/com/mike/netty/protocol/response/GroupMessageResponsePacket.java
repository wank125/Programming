package com.mike.netty.protocol.response;

import com.mike.netty.protocol.Packet;
import com.mike.netty.server.session.Session;
import lombok.Data;

import static com.mike.netty.protocol.Command.GROUP_MESSAGE_RESPONSE;

@Data
public class GroupMessageResponsePacket extends Packet {
    private String fromGroupId;
    private String Message;
    private Session fromUser;

    @Override
    public Byte getCommand() {
        return GROUP_MESSAGE_RESPONSE;
    }
}
