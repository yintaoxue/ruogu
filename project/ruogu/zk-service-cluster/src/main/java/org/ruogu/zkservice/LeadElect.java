package org.ruogu.zkservice;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

/**
 * LeadElect
 * 
 * @author xueyintao
 */
public class LeadElect implements Watcher, Runnable {

	private ZooKeeper zk = null;
	private static String serverId = "1";
	boolean isRunning = false;

	public LeadElect(ZooKeeper zk) {
		this.zk = zk;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			// 创建zk连接
			ZooKeeper zk = new ZooKeeper("localhost:2181", 3000, null);

			new LeadElect(zk).run();

			Thread.sleep(1000000);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		// 创建临时顺序节点
		try {
			zk.create("/cluster/server", serverId.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
			System.out.println("create cluster /cluster/server");

			// 获取节点值，并设置watcher
			List<String> list = zk.getChildren("/cluster", this);
			// 排序取最小的
			Collections.sort(list);
			String firstNode = list.get(0);
			byte[] data = zk.getData("/cluster/" + firstNode, null, null);
			String leaderId = new String(data);
			
			System.out.println("leaderId:" + leaderId + ",serverId:" + serverId);
			
			if (leaderId.equals(serverId)) {
				System.out.println("I'm leader!!!");
				doLeader();
			}
			

		} catch (KeeperException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void doLeader() {
		while (true) {
			System.out.println("leader do something.");
			isRunning = true;
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void doFollower() {
		System.out.println("I'm follower, waiting for leader down...");
	}

	public void process(WatchedEvent e) {
		try {
			System.out.println("event,state:" + e.getState() + ", type:" + e.getType() + ", path:" + e.getPath());
			// 获取节点值，并设置watcher
			List<String> list = zk.getChildren("/cluster", this);
			// 排序取最小的
			Collections.sort(list);
			String firstNode = list.get(0);
			byte[] data = zk.getData("/cluster/" + firstNode, null, null);
			String leaderId = new String(data);
	
			System.out.println("leaderId:" + leaderId + ",serverId:" + serverId);
	
			if (leaderId.equals(serverId)) {
				if (!isRunning) {
					System.out.println("I'm leader!!!");
					doLeader();
				}
			}
		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}

}
