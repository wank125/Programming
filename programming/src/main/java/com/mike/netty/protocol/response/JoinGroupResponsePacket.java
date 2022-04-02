package com.mike.netty.protocol.response;

import com.mike.netty.protocol.Packet;
import lombok.Data;

import static com.mike.netty.protocol.Command.JOIN_GROUP_RESPONSE;

@Data
public class JoinGroupResponsePacket extends Packet {
    private String groupId;
    private Boolean success;
    private String reason;

    public Boolean isSuccess() {
        return success;
    }

    @Override
    public Byte getCommand() {
        return JOIN_GROUP_RESPONSE;
    }
}
