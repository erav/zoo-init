package com.github.erav.zooinit;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * @author adoneitan@gmail.com
 * @since 20 June 2016
 */
public class ZooPopulateTest
{
	private static final Logger logger = LogManager.getLogger(ZooPopulateTest.class);

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

		String toCommit = FileSystem.readContent(TestConfig.filename, "");
		ZooPopulate zpop = new ZooPopulate(zkCurator);
		zpop.populate(toCommit);

		ZooDump zooDump = new ZooDump(zkCurator);
		zooDump.dump();

		logger.info("expected ----------------------------------------------------");
		logger.info(((JSONObject)JSONValue.parseWithException(toCommit)).toJSONString());
		logger.info("dump ----------------------------------------------------");
		logger.info(((JSONObject)zooDump.result.get(TestConfig.namespace)).toJSONString());
		logger.info("----------------------------------------------------");

		assertTrue(JSONValue.parseWithException(toCommit).equals(zooDump.result.get(TestConfig.namespace)));
	}
}