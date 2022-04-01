package com.mike.netty.server.session;

import lombok.Data;

@Data
public class Session {

    private String userId;
    private String userName;

    public Session(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }
}
