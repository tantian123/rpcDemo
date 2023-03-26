package com.tt.rpcDemo.service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public interface AbstractRpcService {
    Map<String, Object> handlerMap = new HashMap<>();
    void start(int port) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException;
    void stop() throws IOException;
    void register(String serviceName, Object service);
}
