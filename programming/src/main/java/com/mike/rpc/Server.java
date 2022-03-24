package com.mike.rpc;

import java.io.IOException;

public interface Server {
    public void start() throws IOException;

    public void stop();
    public  void register (Class serviceInterface,Class imple);
    public boolean isRunning();
    public  int getPort();
}
