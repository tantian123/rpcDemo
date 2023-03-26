package com.tt.rpc.server;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GetList {
    public List<String> getData(){
        List<String> list = Stream.of("1", "2", "3").collect(Collectors.toList());
        return list;
    }

    public static void main(String[] args) {
        GetList getList=new GetList();
        GetList o = (GetList)Proxy.newProxyInstance(getList.getClass().getClassLoader(), getList.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (method.getName().equals("getData")) {
                    System.out.println("method getList");
                }
                return method.invoke(getList, args);
            }
        });
        List<String> data = o.getData();
        for (String datum : data) {
            System.out.println(datum);
        }
    }
  // 写一个查询基金实时行情的接口，实现类是从数据库查询，然后把实现类注册到注册中心，然后客户端通过注册中心获取实现类，然后调用实现类的方法获取数据。
}