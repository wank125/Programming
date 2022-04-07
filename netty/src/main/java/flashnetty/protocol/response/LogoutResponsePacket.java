package flashnetty.protocol.response;

import flashnetty.protocol.Packet;
import lombok.Data;

import static flashnetty.protocol.Command.LOGOUT_RESPONSE;

@Data
public class LogoutResponsePacket extends Packet {
    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {
        return LOGOUT_RESPONSE;
    }
}
