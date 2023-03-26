package com.tt.rpcDemo.client;

import com.tt.rpcDemo.proto.MyRpcRequest;
import com.tt.rpcDemo.proto.MyRpcResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MyRpcClient implements AbstractRpcClient{
    private Socket socket;
    @Override
    public void start() throws IOException {
        socket=new Socket("localhost",7788);
    }

    @Override
    public MyRpcResponse send(MyRpcRequest myRpcRequest) throws IOException, ClassNotFoundException {
        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
        outputStream.writeObject(myRpcRequest);
        System.out.println("request is :");
        System.out.println(myRpcRequest.getRequestParams()[0]);

        ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream()) ;

        MyRpcResponse myRpcResponse = (MyRpcResponse) inputStream.readObject();
        System.out.println("response is :");
        System.out.println(myRpcResponse.getMsg());

        return myRpcResponse;
    }
}
