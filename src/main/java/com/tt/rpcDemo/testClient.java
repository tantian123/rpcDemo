package com.tt.rpcDemo;

import com.tt.rpcDemo.client.MyRpcClient;
import com.tt.rpcDemo.proto.MyRpcRequest;
import com.tt.rpcDemo.proto.MyRpcResponse;
import com.tt.rpcDemo.api.HelloService;

import java.io.IOException;
import java.lang.reflect.Proxy;

public class testClient {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
//        // 启动客户端 发送请求
//        MyRpcClient myRpcClient = new MyRpcClient();
//        myRpcClient.start();
//
//        MyRpcRequest myRpcRequest = MyRpcRequest.builder()
//                .requestClassName("helloService")
//                .requestMethodName("hello")
//                .requestParams(new Object[]{"tt"})
//                .build();
//        myRpcClient.send(myRpcRequest);
        invokeHelloService();
    }

    // Proxy.newProxyInstance可以封装成一个方法
    public static void invokeHelloService(){
        HelloService helloService = (HelloService)Proxy.newProxyInstance(
                HelloService.class.getClassLoader(),
                new Class[]{HelloService.class},
                (proxy, method, args1) -> {
                    MyRpcClient myRpcClient = new MyRpcClient();
                    myRpcClient.start();

                    MyRpcRequest myRpcRequest = MyRpcRequest.builder()
                            .requestClassName("helloService")
                            .requestMethodName(method.getName())
                            .requestParams(args1)
                            .build();
                    MyRpcResponse rpcResponse = myRpcClient.send(myRpcRequest);

                    return rpcResponse.getMsg();
                }
        );

        helloService.hello("tantian");
    }
}
