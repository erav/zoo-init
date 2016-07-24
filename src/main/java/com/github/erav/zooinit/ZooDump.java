package com.github.erav.zooinit;

import net.minidev.json.JSONObject;
import org.apache.curator.framework.CuratorFramework;

import java.util.List;

/**
 * @author adoneitan@gmail.com
 * @since 6/14/16.
 */
public class ZooDump
{
	protected CuratorFramework zkCurator;
	public JSONObject result;

	public ZooDump(CuratorFramework zkCurator)
	{
		this.zkCurator = zkCurator;
	}

	public void dump() throws Exception
	{
		result = new JSONObject();
		JSONObject dump = result;
		if (zkCurator.getNamespace() != null && zkCurator.getNamespace().length() > 0)
		{
			JSONObject nameSpace = new JSONObject();
			result.put(zkCurator.getNamespace(), nameSpace);
			dump = nameSpace;
		}
		dumpRecur("", dump);
	}

	public void dumpRecur(String path, JSONObject result) throws Exception
	{
		List<String> children = zkCurator.getChildren().forPath(path);
		for (String child : children)
		{
			JSONObject value = new JSONObject();
			result.put(child, value);
			String childPath = path + "/" + child;
			dumpRecur(childPath, value);
		}
		String data = new String(zkCurator.getData().forPath(path));
		if (data != null) {
			result.put("data", data);
		}
	}

}
