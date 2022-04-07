package remotecall.client;

import remotecall.HelloService;
import remotecall.ProxyFactory;
import remotecall.api.IRpcService;

import java.util.Date;

public class SimpleNettyClient {
    public static void main(String[] args) throws Exception {

        //创建动态代理类实例
        HelloService helloService = (HelloService) ProxyFactory.getProxy(HelloService.class, "localhost", 8000);
        String hello = helloService.echo("hello");
        System.out.println(hello);
        Date time = helloService.getTime();
        System.out.println(time);


        IRpcService rpcService = (IRpcService) ProxyFactory.getProxy(IRpcService.class, "localhost", 8000);
        int sub = rpcService.sub(1, 3);
        System.out.println(sub);

        int add = rpcService.add(2, 2);
        System.out.println(add);

    }
}
