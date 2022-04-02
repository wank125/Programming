package com.mike.netty.protocol.request;

import com.mike.netty.protocol.Packet;
import lombok.Data;

import java.util.List;

import static com.mike.netty.protocol.Command.CREATE_GROUP_REQUEST;

@Data
public class CreateGroupRequestPacket extends Packet {
    private List<String> userIdList;
    @Override
    public Byte getCommand() {
        return CREATE_GROUP_REQUEST;
    }
}
