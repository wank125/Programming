package flashnetty.protocol.response;

import flashnetty.protocol.Packet;
import flashnetty.server.session.Session;
import lombok.Data;

import java.util.List;

import static flashnetty.protocol.Command.LIST_GROUP_MEMBERS_RESPONSE;

@Data
public class ListGroupMembersResponsePacket extends Packet {
    private String groupId;
    private List<Session> sessionList;

    @Override
    public Byte getCommand() {
        return LIST_GROUP_MEMBERS_RESPONSE;
    }
}
