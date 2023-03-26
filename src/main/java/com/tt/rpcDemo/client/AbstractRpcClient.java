package com.tt.rpcDemo.client;

import com.tt.rpcDemo.proto.MyRpcRequest;
import com.tt.rpcDemo.proto.MyRpcResponse;

import java.io.IOException;

public interface AbstractRpcClient {
    void start() throws IOException;
    MyRpcResponse send(MyRpcRequest request) throws IOException, ClassNotFoundException;
}
