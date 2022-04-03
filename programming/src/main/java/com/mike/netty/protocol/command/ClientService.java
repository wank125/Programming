package com.mike.netty.protocol.command;

/**
 * 客户端请求
 */
public interface ClientService {

    /**
     * 发送消息到特定用户
     *
     * @param toUserId 目标用户
     * @param msg      消息
     */
    void sendToUser(String toUserId, String msg);


    /**
     * 创建组
     *
     * @param userIds 目标用户
     */
    void createGroup(String userIds);

    /**
     * 加入特定组
     *
     * @param groupId 目标组
     */
    void joinGroup(String groupId);

    /**
     * 查看组成员
     *
     * @param groupId 目标组
     */
    void listGroupMembers(String groupId);

    /**
     * 发送消息给特定组
     *
     * @param groupId 目标用户
     * @param message 消息
     */
    void sendToGroup(String groupId, String message);

    /**
     * 登出
     */
    void logout();
}
