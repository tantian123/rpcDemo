package com.tt.rpcDemo.api;

import java.lang.reflect.Method;

public class HelloServiceImpl implements HelloService {
    public String hello(String name) {
        System.out.println("hello"+name);
        return "hello"+name;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object invoke = method.invoke(this, args);
        return invoke;
    }
}
