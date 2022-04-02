package com.mike.netty.protocol.response;

import com.mike.netty.protocol.Packet;
import lombok.Data;

import static com.mike.netty.protocol.Command.QUIT_GROUP_RESPONSE;

@Data
public class QuitGroupResponsePacket extends Packet {
    private String groupId;

    public Boolean isSuccess() {
        return success;
    }

    private Boolean success;



    @Override
    public Byte getCommand() {
        return QUIT_GROUP_RESPONSE;
    }
}
