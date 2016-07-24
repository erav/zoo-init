package com.github.erav.zooinit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


/**
 * @author adoneitan@gmail.com
 * @since 5/4/16.
 */
public class Main
{
	private static final Logger logger = LogManager.getLogger(Main.class);

	public static void main(String[] args) throws Exception
	{
		CliArgs.init(args);
		run(CliArgs.args.namespace, CliArgs.args.zkServer, CliArgs.args.filename, CliArgs.args.action);
	}

	private static void run(String namespace, String zkServer, String filename, String action) throws Exception
	{
		logger.info("Starting zookeeper client...");
		CuratorFramework zkCurator = CuratorFrameworkFactory.builder().
				namespace(namespace).
				connectString(zkServer).
				retryPolicy(new ExponentialBackoffRetry(200, 100)).
				build();

		zkCurator.start();
		logger.info("Zookeeper client started");

		if (ZooAction.isCommit(action))
		{
			ZooDump zooDump = new ZooDump(zkCurator);
			zooDump.dump();
			if (zooDump.result != null && !zooDump.result.isEmpty()) {
				logger.error("namespace '" + namespace + "' is not empty. cannot commit new content");
			}
			else
			{
				logger.info("commiting content from: '" + filename + "'");
				String content = FileSystem.readContent(filename, "");
				logger.info(content);
				ZooPopulate zooPopulate = new ZooPopulate(zkCurator);
				zooPopulate.populate(content);
			}
		}
		else if (ZooAction.isDump(action))
		{
			logger.info("dumping content from: '" + filename + "'");
			ZooDump zooDump = new ZooDump(zkCurator);
			zooDump.dump();
			logger.info("--------------------------");
			logger.info(zooDump.result.toJSONString());
		}
		else if (ZooAction.isDelete(action))
		{
			logger.info("deleting root node for namespace: '" + namespace + "'");
			ZooDelete zkDel = new ZooDelete(zkCurator);
			zkDel.deleteRoot();
		}
		zkCurator.close();
	}

}
