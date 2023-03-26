package com.tt.nettyDemo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class NettyServer {
    public static void main(String[] args) throws InterruptedException {
        // 两个事件循环组，其中bossGroup只是用来接受消息的group，workerGroup是真正进行处理的group
        // handler对应bossGroup，childHandler对应workerGroup

        EventLoopGroup fatherGroup=new NioEventLoopGroup();
        EventLoopGroup childGroup=new NioEventLoopGroup();
        ServerBootstrap serverBootstrap=new ServerBootstrap();
        serverBootstrap.group(fatherGroup,childGroup)
                //设置服务端通道实现类型
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.DEBUG))
                //使用匿名内部类的形式初始化通道对象
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
//                        pipeline.addLast(new StringDecoder());// 添加都是ChannelHandler
//                        pipeline.addLast(new StringEncoder());
                        pipeline.addLast(new ServerHandler());
                    }
                });
        //绑定端口号，启动服务端
        //ChannelFuture提供操作完成时一种异步通知的方式。一般在Socket编程中，等待响应结果都是同步阻塞的，而Netty则不会造成阻塞，因为ChannelFuture是采取类似观察者模式的形式进行获取结果
        ChannelFuture future= serverBootstrap.bind(8888).sync();


        // 使用sync()方法可以阻塞
        future.channel().closeFuture().sync();

        fatherGroup.shutdownGracefully();
        childGroup.shutdownGracefully();
    }
}
