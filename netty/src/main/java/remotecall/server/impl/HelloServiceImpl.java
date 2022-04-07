package remotecall.server.impl;

import remotecall.HelloService;

import java.util.Date;

public class HelloServiceImpl implements HelloService {
    @Override
    public String echo(String msg) {
        return "echo:" + msg;
    }

    @Override
    public Date getTime() {
        return new Date();
    }

    @Override
    public String toString() {
        return "HelloServiceImpl{}";
    }
}
