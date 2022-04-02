package com.mike.netty.protocol.request;

import com.mike.netty.protocol.Packet;

import static com.mike.netty.protocol.Command.LOGOUT_REQUEST;

public class LogoutRequestPacket extends Packet {
    @Override
    public Byte getCommand() {
        return LOGOUT_REQUEST;
    }
}
