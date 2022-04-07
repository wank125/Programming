package flashnetty.protocol.response;

import flashnetty.protocol.Packet;
import lombok.Data;

import static flashnetty.protocol.Command.JOIN_GROUP_RESPONSE;

@Data
public class JoinGroupResponsePacket extends Packet {
    private String groupId;
    private Boolean success;
    private String reason;

    public Boolean isSuccess() {
        return success;
    }

    @Override
    public Byte getCommand() {
        return JOIN_GROUP_RESPONSE;
    }
}
