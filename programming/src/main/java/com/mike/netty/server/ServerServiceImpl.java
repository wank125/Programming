package com.mike.netty.server;

import com.mike.netty.protocol.command.ClientService;

public class ServerServiceImpl implements ClientService {
    @Override
    public void sendToUser(String toUserId, String msg) {

    }

    @Override
    public void createGroup(String userIds) {

    }

    @Override
    public void joinGroup(String groupId) {

    }

    @Override
    public void listGroupMembers(String groupId) {

    }

    @Override
    public void sendToGroup(String groupId, String message) {

    }

    @Override
    public void logout() {

    }
}
