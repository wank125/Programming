package com.mike.netty.protocol.command;

import com.mike.netty.client.ClientServiceImpl;
import com.mike.netty.client.ClientServiceProxy;
import com.mike.netty.protocol.request.MessageRequestPacket;
import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ConsoleCommandManager implements ConsoleCommand {

    private ClientCommand clientCommand;
    private Map<String, ConsoleCommand> consoleCommandMap;


    public ConsoleCommandManager() {

        clientCommand = (ClientCommand) new ClientServiceProxy().bind(new ClientCommandImpl());

        consoleCommandMap = new HashMap<>();
        consoleCommandMap.put("sendToUser", new SendToUserConsoleCommand());
        consoleCommandMap.put("logout", new LogoutConsoleCommand());
        consoleCommandMap.put("createGroup", new CreateGroupConsoleCommand());
        consoleCommandMap.put("joinGroup", new JoinGroupConsoleCommand());
        consoleCommandMap.put("listGroupMembers", new ListGroupMembersConsoleCommand());
        consoleCommandMap.put("sendToGroup", new SendToGroupConsoleCommand());
    }

    @Override
    public void exec(Scanner scanner, Channel channel) {
        String command = scanner.next();
        // ConsoleCommand consoleCommand = consoleCommandMap.get(command);
        if (command != null) {
            //consoleCommand.exec(scanner, channel);
            doExec(command, scanner, channel);
        } else {
            System.err.println("无法识别[" + command + "]指令，请重新输入!");
        }
    }

    private void doExec(String command, Scanner scanner, Channel channel) {
        switch (command) {
            case "sendToUser":
                clientCommand.sendToUser(scanner, channel);
                break;
            case "logout":
                clientCommand.logout(scanner, channel);
                break;
            case "createGroup":
                clientCommand.createGroup(scanner, channel);
                break;
            case "joinGroup":
                clientCommand.joinGroup(scanner, channel);
                break;
            case "listGroupMembers":
                clientCommand.listGroupMembers(scanner, channel);
                break;
            case "sendToGroup":
                clientCommand.sendToGroup(scanner, channel);
                break;
        }
    }

    class ClientCommandImpl implements ClientCommand {
        ClientService clientService;

        @Override
        public void sendToUser(Scanner scanner, Channel channel) {
            System.out.print("发送消息给某个某个用户：");
            String toUserId = scanner.next();
            String message = scanner.next();
            //channel.writeAndFlush(new MessageRequestPacket(toUserId, message));
            clientService.sendToUser(toUserId, message);
        }

        @Override
        public void createGroup(Scanner scanner, Channel channel) {

        }

        @Override
        public void joinGroup(Scanner scanner, Channel channel) {

        }

        @Override
        public void listGroupMembers(Scanner scanner, Channel channel) {

        }

        @Override
        public void sendToGroup(Scanner scanner, Channel channel) {

        }

        @Override
        public void logout(Scanner scanner, Channel channel) {

        }
    }
}
