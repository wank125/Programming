package flashnetty.client.handler;

import com.mike.netty.protocol.response.ListGroupMembersResponsePacket;
import com.mike.netty.server.session.Session;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.List;

public class ListGroupMembersResponseHandler extends SimpleChannelInboundHandler<ListGroupMembersResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListGroupMembersResponsePacket msg) throws Exception {
        String groupId = msg.getGroupId();
        List<Session> sessionList = msg.getSessionList();
        System.out.println("群[" + groupId + "]中的人包括：" +sessionList);

    }
}
