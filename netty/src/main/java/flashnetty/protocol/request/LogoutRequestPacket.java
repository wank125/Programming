package flashnetty.protocol.request;

import flashnetty.protocol.Packet;

import static flashnetty.protocol.Command.LOGOUT_REQUEST;

public class LogoutRequestPacket extends Packet {
    @Override
    public Byte getCommand() {
        return LOGOUT_REQUEST;
    }
}
