package com.tt.rpc.server;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;

import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;

public class RpcServer {

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        new RpcServer().serving();
    }

    public void serving() throws IOException, KeeperException, InterruptedException {
        //获取本机ip地址
        String ip = null;
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        while (networkInterfaces.hasMoreElements()) {
            NetworkInterface ni = (NetworkInterface) networkInterfaces.nextElement();
            Enumeration<InetAddress> nias = ni.getInetAddresses();
            while (nias.hasMoreElements()) {
                InetAddress ia = (InetAddress) nias.nextElement();
                if (!ia.isLinkLocalAddress() && !ia.isLoopbackAddress() && ia instanceof Inet4Address) {
                    ip = ia.getHostAddress();
                }
            }
        }
        int port = 8988;

        //启动服务
        ServerSocket socket = new ServerSocket(port);
        System.out.println("服务器已启动...");
        //注册服务
        serverRegister(ip, port);
        //处理请求
        clientHandler(socket);
    }

    private void clientHandler(ServerSocket socket) throws IOException {
        while (true) {
            Socket accept = socket.accept();
            InputStream inputStream = accept.getInputStream();
            byte[] barr = new byte[1024];
            while (true) {
                int size = inputStream.read(barr);
                if (size == -1) {
                    //System.out.println("客户端已关闭..");
                    accept.close();
                    break;
                }
                String s = new String(barr, 0, size);
                //输出客户端消息
                System.out.println(accept.getInetAddress().getHostAddress() + ": " + s);
            }
        }

    }

    private void serverRegister(String ip, int port) throws IOException, KeeperException, InterruptedException {
        //注册服务
        ZooKeeper zooKeeper = new ZooKeeper("10.211.55.4: 2181",3000, null);
        try {
            ArrayList<ACL> acl = new ArrayList<>();
            acl.add(new ACL(31, ZooDefs.Ids.ANYONE_ID_UNSAFE));
            zooKeeper.create("/userServer", (ip + ":" + port).getBytes(StandardCharsets.UTF_8), acl, CreateMode.EPHEMERAL);
            System.out.println("服务发布成功!");
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
            throw e;
        }
    }
}