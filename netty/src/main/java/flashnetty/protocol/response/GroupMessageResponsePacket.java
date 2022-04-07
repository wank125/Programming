package flashnetty.protocol.response;

import flashnetty.protocol.Packet;
import flashnetty.server.session.Session;
import lombok.Data;

import static flashnetty.protocol.Command.GROUP_MESSAGE_RESPONSE;

@Data
public class GroupMessageResponsePacket extends Packet {
    private String fromGroupId;
    private String Message;
    private Session fromUser;

    @Override
    public Byte getCommand() {
        return GROUP_MESSAGE_RESPONSE;
    }
}
