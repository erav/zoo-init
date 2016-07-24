package com.github.erav.zooinit;

import net.minidev.json.JSONObject;
import org.apache.curator.framework.CuratorFramework;

/**
 * @author adoneitan@gmail.com
 * @since 6/14/16.
 */
public class ZooDelete
{
	protected CuratorFramework zkCurator;
	public JSONObject result;

	public ZooDelete(CuratorFramework zkCurator)
	{
		this.zkCurator = zkCurator;
	}

	public void deleteLeaf(String path) throws Exception {
		zkCurator.delete().forPath(path);
	}

	public void deleteRoot() throws Exception {
		deletePath("");
	}

	public void deletePath(String path) throws Exception
	{
		for (String child: zkCurator.getChildren().forPath(path))
		{
			String childPath = path + "/" + child;
			deletePath(childPath);
		}
		deleteLeaf(path);
	}

}
