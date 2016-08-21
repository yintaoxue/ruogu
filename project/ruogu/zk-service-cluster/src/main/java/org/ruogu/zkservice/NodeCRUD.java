package org.ruogu.zkservice;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

/**
 * NodeCRUD
 * 
 * @author xueyintao
 */
public class NodeCRUD {

	private static ZooKeeper zk = null;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			// 创建zk连接
			zk = new ZooKeeper("localhost:2181", 3000, null);
			
			// 创建节点
			zk.create("/new_node", "this is new node".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
			System.out.println("create node /new_node");
			
			// 获取节点值，并设置watcher
			byte[] data = zk.getData("/new_node", new DefaultWatcher(), null);
			System.out.println("get data:" + new String(data));
			
			
			Thread.sleep(1000000);
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (KeeperException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	static class DefaultWatcher implements Watcher {

		public void process(WatchedEvent e) {
			System.out.println("event,state:" + e.getState() + ", type:" + e.getType() + ", path:" + e.getPath());
			try {
				byte[] data = zk.getData("/new_node", new DefaultWatcher(), null);
				System.out.println("get data:" + new String(data));
			} catch (KeeperException e1) {
				e1.printStackTrace();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		
	}

}

