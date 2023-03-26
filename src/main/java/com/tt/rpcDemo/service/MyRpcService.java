package com.tt.rpcDemo.service;

import com.tt.rpcDemo.proto.MyRpcRequest;
import com.tt.rpcDemo.proto.MyRpcResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;

public class MyRpcService implements AbstractRpcService {
    ServerSocket socketServer;

    @Override
    public void start(int port) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        socketServer = new ServerSocket(port);
        Socket accept = socketServer.accept();
        ObjectInputStream inputStream = new ObjectInputStream(accept.getInputStream()) ;
        MyRpcRequest myRpcRequest = (MyRpcRequest) inputStream.readObject();

        Object o = handlerMap.get(myRpcRequest.getRequestClassName());

        // 通过反射获取
        Class<?> aClass = o.getClass();
        String returnMsg= (String) aClass
                .getMethod(myRpcRequest.getRequestMethodName(), String.class)
                .invoke(o, myRpcRequest.getRequestParams()[0]);

        MyRpcResponse myRpcResponse = MyRpcResponse.builder()
                .msg(returnMsg)
                .build();
        ObjectOutputStream outputStream =new ObjectOutputStream(accept.getOutputStream()) ;
        outputStream.writeObject(myRpcResponse);
    }

    @Override
    public void stop() throws IOException {
        socketServer.close();
    }

    @Override
    public void register(String serviceName, Object service) {
        handlerMap.put(serviceName, service);
    }
}