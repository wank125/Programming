package com.mike.netty.protocol.response;

import com.mike.netty.protocol.Packet;
import lombok.Data;

import java.util.List;

import static com.mike.netty.protocol.Command.CREATE_GROUP_RESPONSE;

@Data
public class CreateGroupResponsePacket extends Packet {

    private boolean success;

    private String groupId;

    private List<String> userNameList;
    @Override
    public Byte getCommand() {
        return CREATE_GROUP_RESPONSE;
    }
}
