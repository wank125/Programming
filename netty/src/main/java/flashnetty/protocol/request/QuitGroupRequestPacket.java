package flashnetty.protocol.request;

import flashnetty.protocol.Packet;
import lombok.Data;

import static flashnetty.protocol.Command.QUIT_GROUP_REQUEST;

@Data
public class QuitGroupRequestPacket extends Packet {
    private String groupId;

    @Override
    public Byte getCommand() {
        return QUIT_GROUP_REQUEST;
    }
}
