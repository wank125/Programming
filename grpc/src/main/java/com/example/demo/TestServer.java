package com.example.demo;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.justtest.GreeterGrpc;

import java.io.IOException;

import io.grpc.justtest.TestRequest;
import io.grpc.stub.StreamObserver;
import io.grpc.justtest.TestResponse;

public class TestServer {
    private final int port = 50051;
    private Server server;

    private void start() throws IOException {
        server = ServerBuilder.forPort(port).addService(new GreeterImple()).build().start();
        System.out.println("服务启动");
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.out.println("关闭GRPC");
                TestServer.this.stop();
            }
        });

    }

    private void stop() {
        if (server != null)
            server.shutdown();
    }

    private void blockUntilShutDown() throws InterruptedException {
        if (server != null)
            server.awaitTermination();
    }

    public static void main(String[] args) throws Exception {
        TestServer server = new TestServer();
        server.start();
        server.blockUntilShutDown();
    }

    class GreeterImple extends GreeterGrpc.GreeterImplBase {
        public void testSomeThing(TestRequest request, StreamObserver<TestResponse> responseObserver) {
            TestResponse build = TestResponse.newBuilder().setMessage(request.getName()).build();
            responseObserver.onNext(build);
            responseObserver.onCompleted();
        }

    }
}
