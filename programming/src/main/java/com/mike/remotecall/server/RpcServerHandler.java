/*
 * Copyright 2022 WangKai
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mike.remotecall.server;

import com.mike.remotecall.struct.Call;
import com.mike.remotecall.server.impl.HelloServiceImpl;
import com.mike.remotecall.struct.Header;
import com.mike.remotecall.struct.MessageType;
import com.mike.remotecall.struct.NettyMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RpcServerHandler extends ChannelInboundHandlerAdapter {

    //保存所有可用服务
    public static ConcurrentHashMap<String, Object> registryMap = new ConcurrentHashMap<>();

    //保存所有相关的服务类
    List<String> classNames = new ArrayList<String>();


    private static Map<String, Object> remoteObjects = new HashMap();

    static {
        // remoteObjects.put("com.mike.remotecall.HelloService", new HelloServiceImpl());
    }

    public RpcServerHandler() {
        scannerClass("com.mike.remotecall.server.impl");
        doRegister();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage message = (NettyMessage) msg;
        if (message.getHeader() != null
                && message.getHeader().getType() == MessageType.CALL_REQ.value()) {
            NettyMessage resp = null;
            Call call = (Call) message.getBody();
            Call invoke = invoke(call);
            resp = buildResponse(invoke);
            System.out.println("The Call response is : " + resp + " body [" + resp.getBody() + "]");
            ctx.writeAndFlush(resp);
        } else {
            ctx.fireChannelRead(msg);
        }
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("收到客户端连接");
        super.channelActive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
        ctx.fireExceptionCaught(cause);
    }

    private NettyMessage buildResponse(Call result) {
        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setType(MessageType.CALL_RESP.value());
        message.setHeader(header);
        message.setBody(result);
        return message;
    }

    public Call invoke(Call call) {
        Object result;
        try {
            String className = call.getClassName();
            String methodName = call.getMethodName();
            Object[] params = call.getParams();
            Class[] paramTypes = call.getParamTypes();

            Class<?> classType = Class.forName(className);
            Method method = classType.getMethod(methodName, paramTypes);
            //  Object remoteObject = remoteObjects.get(className);
            Object remoteObject = registryMap.get(className);
            if (remoteObject == null) {
                throw new Exception(className + "的远程对象不存在");
            } else {
                result = method.invoke(remoteObject, params);
            }
        } catch (Exception e) {
            result = e;
        }

        call.setResult(result);
        return call;
    }

    private void scannerClass(String packageName) {
        URL url = this.getClass().getClassLoader().getResource(packageName.replaceAll("\\.", "/"));
        File dir = new File(url.getFile());
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                scannerClass(packageName + "." + file.getName());
            } else {
                classNames.add(packageName + "." + file.getName().replaceAll(".class", "").trim());
            }
        }
    }

    private void doRegister() {
        if (classNames.size() == 0) {
            return;
        }
        for (String className : classNames) {
            try {
                Class<?> clazz = Class.forName(className);
                Class<?> clazzInterface = clazz.getInterfaces()[0];
                registryMap.put(clazzInterface.getName(), clazz.newInstance());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }


}
