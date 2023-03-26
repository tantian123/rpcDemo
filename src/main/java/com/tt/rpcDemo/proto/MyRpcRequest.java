package com.tt.rpcDemo.proto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class MyRpcRequest implements Serializable {
    private String requestMethodName;
    private String requestClassName;
    private Object[] requestParams;
    private Class<?>[] requestParamTypes;
    private String requestVersion;
}
