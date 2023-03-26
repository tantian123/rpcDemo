package com.tt.rpc.demo;

import org.apache.zookeeper.*;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ZookDemo {
    private static final Logger LOGGER = LoggerFactory.getLogger(ZookDemo.class);
    public static ZooKeeper zooKeeper=null;
    public void init() throws IOException {
        zooKeeper= new ZooKeeper("127.0.0.1:2181", 10000, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println("zookeeper启动成功");
            }
        });

    }
    public void create(String address) throws InterruptedException, KeeperException {
        String s = zooKeeper.create("/zk_demo1", address.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    public byte[] getData() throws InterruptedException, KeeperException {
        byte[] data = zooKeeper.getData("/zk_demo1", false, null);
        System.out.println(new String(data));
        return data;
    }

    public List<String> getDataList() throws InterruptedException, KeeperException {
        List<String> resList=new ArrayList<>();

        List<String> children = zooKeeper.getChildren("/zk_demo1", false, null);
        for (String child : children) {
            byte[] data = zooKeeper.getData("/zk_demo1" + "/" + child, false, null);
            resList.add(new String(data));
        }
        return resList;
    }

    public void testDelete() throws KeeperException, InterruptedException {
        // 指定要删除的版本，-1表示删除所有版本
        zooKeeper.delete("/zk_demo1", -1);
    }


    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        ZookDemo zookDemo = new ZookDemo();
        zookDemo.init();
        zookDemo.create("tt123");
        byte[] data = zookDemo.getData();
        zookDemo.testDelete();
    }
}
