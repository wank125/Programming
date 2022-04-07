package flashnetty.protocol.request;

import flashnetty.protocol.Packet;
import lombok.Data;

import static flashnetty.protocol.Command.LIST_GROUP_MEMBERS_REQUEST;

@Data
public class ListGroupMembersRequestPacket extends Packet {
    private String groupId;

    @Override
    public Byte getCommand() {
        return LIST_GROUP_MEMBERS_REQUEST;
    }
}
