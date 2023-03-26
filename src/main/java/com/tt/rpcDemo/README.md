
1.这次迭代的实现方式
原始的方案实现RPC框架，采用Socket通信、动态代理与反射与Java原生的序列化

2.socket通信
ServerSocket和Socket的使用
ServerSocket用于服务端，Socket用于客户端

ServerSocket的使用：
创建ServerSocket对象，绑定端口号
通过accept方法，监听客户端的连接请求，接收客户端的连接请求，返回Socket对象
每个socket都有一个输入流和一个输出流，通过输入流读取客户端发送的数据，通过输出流向客户端发送数据

3.动态代理
客户端方面，由于在客户端这一侧我们并没有接口的具体实现类，就没有办法直接生成实例对象。
这时，我们可以通过动态代理的方式生成实例，并且调用方法时生成需要的RpcRequest对象并且发送给服务端。

Proxy.newProxyInstance(ClassLoader loader, Class<?>[] interfaces, InvocationHandler h)
loader：类加载器
interfaces：需要实现的接口
h：InvocationHandler接口的实现类

InvocationHandler接口的实现类
invoke(Object proxy, Method method, Object[] args)
proxy：代理对象
method：被代理对象的方法
args：被代理对象的方法的参数

4.反射

5.java原生的序列化
实现Serializable接口
调用ObjectOutputStream的writeObject方法，将对象序列化后写入到输出流中
调用ObjectInputStream的readObject方法，从输入流中反序列化后读取对象


6.具体实现的代码结构和逻辑
rpc-api：定义了接口和实体类
rpc-client：客户端
rpc-server：服务端
rpc-proto:定义了通信的协议（格式）

优化点：服务器端采用多线程，采用线程池




