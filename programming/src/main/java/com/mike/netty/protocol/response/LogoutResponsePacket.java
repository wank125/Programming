package com.mike.netty.protocol.response;

import com.mike.netty.protocol.Packet;
import lombok.Data;

import static com.mike.netty.protocol.Command.LOGOUT_RESPONSE;

@Data
public class LogoutResponsePacket extends Packet {
    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {
        return LOGOUT_RESPONSE;
    }
}
