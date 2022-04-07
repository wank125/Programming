package flashnetty.protocol.request;

import flashnetty.protocol.Packet;
import lombok.Data;

import static flashnetty.protocol.Command.LOGIN_REQUEST;

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
