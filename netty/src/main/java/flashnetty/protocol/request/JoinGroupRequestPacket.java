package flashnetty.protocol.request;

import flashnetty.protocol.Packet;
import lombok.Data;

import static flashnetty.protocol.Command.JOIN_GROUP_REQUEST;

@Data
public class JoinGroupRequestPacket extends Packet {
    private String groupId;

    @Override
    public Byte getCommand() {
        return JOIN_GROUP_REQUEST;
    }
}
