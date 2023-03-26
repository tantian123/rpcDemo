package com.tt.rpc.register;

import com.tt.rpc.server.GetList;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class RegisterCenter {
    public static Map<String,Class> registerMap=new HashMap<>();

    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(7788);
        boolean flag=true;
        while(flag){
            Socket socket = serverSocket.accept();
            System.out.println(""+socket.getInetAddress() + socket.getPort());
            socket.close();
        }
    }
    public void register(Class serviceClazz){
        registerMap.put(serviceClazz.getName(),serviceClazz);
    }

    public static void main(String[] args) throws IOException {
        RegisterCenter registerCenter = new RegisterCenter();
        registerCenter.register(GetList.class);
        registerCenter.start();
    }



}
