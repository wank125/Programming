package flashnetty.client;

import flashnetty.protocol.Call;
import io.netty.channel.Channel;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class ClientServiceProxyFactory {
    public static Object getProxy(final Class classType, final Channel channel) {
        InvocationHandler handler = (proxy, method, args) -> {
            try {
                Call call = new Call(classType.getName(), method.getName(), method.getParameterTypes(), args);
                channel.writeAndFlush(call);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return proxy;
        };

        return Proxy.newProxyInstance(classType.getClassLoader(), new Class[]{classType}, handler);
    }
}
