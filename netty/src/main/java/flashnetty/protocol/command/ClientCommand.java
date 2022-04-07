package flashnetty.protocol.command;

import io.netty.channel.Channel;

import java.util.Scanner;

public interface ClientCommand {
    /**
     * 发送消息到特定用户
     */
    void sendToUser(Scanner scanner, Channel channel);


    /**
     * 创建组
     */
    void createGroup(Scanner scanner, Channel channel);

    /**
     * 加入特定组
     */
    void joinGroup(Scanner scanner, Channel channel);

    /**
     * 查看组成员
     */
    void listGroupMembers(Scanner scanner, Channel channel);

    /**
     * 发送消息给特定组
     */
    void sendToGroup(Scanner scanner, Channel channel);

    /**
     * 登出
     */
    void logout(Scanner scanner, Channel channel);
}
