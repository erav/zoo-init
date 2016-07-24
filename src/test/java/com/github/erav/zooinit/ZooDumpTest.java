package com.github.erav.zooinit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * @author adoneitan@gmail.com
 * @since 20 June 2016
 */
public class ZooDumpTest
{
	private static final Logger logger = LogManager.getLogger(ZooDumpTest.class);

	@Test
	public void test() throws Exception
	{
		// start the zookeeper client
		CuratorFramework zkCurator = CuratorFrameworkFactory.builder().
				namespace(TestConfig.namespace).
				connectString(TestConfig.zkServer).
				retryPolicy(new ExponentialBackoffRetry(200, 100)).
				build();

		zkCurator.start();

		ZooDump dump = new ZooDump(zkCurator);
		dump.dump();
		logger.info(dump.result.toJSONString());
	}
}