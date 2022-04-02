package com.mike.netty.protocol.request;

import com.mike.netty.protocol.Packet;
import lombok.Data;

import static com.mike.netty.protocol.Command.LOGIN_REQUEST;

@Data
public class LoginRequestPacket extends Packet {

    private String userId;

    private String userName;

    private String password;

    @Override
    public Byte getCommand() {
        return LOGIN_REQUEST;
    }
}
