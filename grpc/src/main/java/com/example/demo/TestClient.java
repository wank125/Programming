package com.example.demo;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.justtest.GreeterGrpc;
import io.grpc.justtest.TestRequest;
import io.grpc.justtest.TestResponse;

import java.util.concurrent.TimeUnit;

public class TestClient {

    private final ManagedChannel channel;
    private final GreeterGrpc.GreeterBlockingStub blockingStub;
    private static final String host = "127.0.0.1";
    private static final int ip = 50051;

    public TestClient(String host, int port) {
        channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext(true).build();
        blockingStub = GreeterGrpc.newBlockingStub(channel);

    }

    public void shutDown() throws Exception {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public void testResult(String name) {
        TestRequest request = TestRequest.newBuilder().setName(name).build();
        TestResponse testResponse = blockingStub.testSomeThing(request);
        System.out.println(testResponse.getMessage());
    }

    public static void main(String[] args) {
        TestClient testClient = new TestClient(host, ip);
        for (int i = 0; i < 5; i++) {
            testClient.testResult("--" + i);
        }
    }
}
