package com.spiderTest.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description: �̳߳�
 * @ClassName: ThreadPoolUtil
 * @author ylx
 */
public class ThreadPoolUtil {
	
	private static ExecutorService executor = Executors.newFixedThreadPool(8);
	
	static {
		int cpus = Runtime.getRuntime().availableProcessors();
		executor = Executors.newFixedThreadPool(cpus * 2 + 5);
	}
	
	/**
	 * ���̳߳��������
	 * 
	 * @param runnable
	 * @author ylx
	 */
	public static void addTask(Runnable runnable) {
		if (null != executor) {
			executor.execute(runnable);
		}
	}
}
