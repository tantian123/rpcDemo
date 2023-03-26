package com.tt.rpcDemo.proto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class MyRpcResponse implements Serializable {
    private String code;
    private String msg;
}
