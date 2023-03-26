package com.tt.rpcDemo;

import com.tt.rpcDemo.api.HelloServiceImpl;
import com.tt.rpcDemo.service.MyRpcService;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class testService {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        // 启动服务端 注册服务
        MyRpcService myRpcService = new MyRpcService();
        HelloServiceImpl helloService = new HelloServiceImpl();
        myRpcService.register("helloService",helloService);
        myRpcService.start(7788);

    }
}
