package com.mike.netty.protocol;

import lombok.Data;

import static com.mike.netty.protocol.Command.LOGIN_RESPONSE;

@Data
public class LoginResponsePacket extends Packet {

    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {
        return LOGIN_RESPONSE;
    }
}
