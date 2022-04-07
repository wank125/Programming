package flashnetty.protocol.response;

import flashnetty.protocol.Packet;
import lombok.Data;

import static flashnetty.protocol.Command.MESSAGE_RESPONSE;

@Data
public class MessageResponsePacket extends Packet {
    private String fromUserId;

    private String fromUserName;

    private String message;

    @Override
    public Byte getCommand() {
        return MESSAGE_RESPONSE;
    }
}
