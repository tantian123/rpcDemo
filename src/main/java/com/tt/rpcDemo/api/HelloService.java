package com.tt.rpcDemo.api;

import java.lang.reflect.InvocationHandler;

public interface HelloService extends InvocationHandler {
    String hello(String name);
}
