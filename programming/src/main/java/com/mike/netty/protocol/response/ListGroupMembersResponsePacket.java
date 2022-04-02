package com.mike.netty.protocol.response;

import com.mike.netty.protocol.Packet;
import com.mike.netty.server.session.Session;
import lombok.Data;

import java.util.List;

import static com.mike.netty.protocol.Command.LIST_GROUP_MEMBERS_RESPONSE;

@Data
public class ListGroupMembersResponsePacket extends Packet {
    private String groupId;
    private List<Session> sessionList;

    @Override
    public Byte getCommand() {
        return LIST_GROUP_MEMBERS_RESPONSE;
    }
}
