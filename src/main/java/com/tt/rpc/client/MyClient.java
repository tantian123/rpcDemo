package com.tt.rpc.client;

import java.io.IOException;
import java.net.Socket;

public class MyClient {
    public static void main(String[] args) throws IOException {
        Socket socket=new Socket("localhost",7788);
        System.out.println(""+socket.getInetAddress() + socket.getPort());
        socket.close();
    }
}
