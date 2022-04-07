package flashnetty.protocol.response;

import flashnetty.protocol.Packet;
import lombok.Data;

import static flashnetty.protocol.Command.QUIT_GROUP_RESPONSE;

@Data
public class QuitGroupResponsePacket extends Packet {
    private String groupId;

    public Boolean isSuccess() {
        return success;
    }

    private Boolean success;



    @Override
    public Byte getCommand() {
        return QUIT_GROUP_RESPONSE;
    }
}
