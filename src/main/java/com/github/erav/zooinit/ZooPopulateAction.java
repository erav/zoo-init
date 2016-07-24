package com.github.erav.zooinit;

import com.github.erav.treemod.traverse.JSONTraverseAction;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.apache.curator.framework.CuratorFramework;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.zookeeper.data.Stat;

import java.util.Map;
import java.util.Map.Entry;

/**
 * @author adoneitan@gmail.com
 * @since 23 May 2016
 */
public class ZooPopulateAction implements JSONTraverseAction
{
	private static Logger logger = LogManager.getLogger(ZooPopulateAction.class);

	protected CuratorFramework curator;

	public ZooPopulateAction(CuratorFramework curator)
	{
		this.curator = curator;
	}

	@Override
	public boolean start(JSONObject jsonObject)
	{
		return true;
	}

	@Override
	public boolean traverseEntry(String path, Entry<String, Object> entry)
	{
		return true;
	}

	@Override
	public boolean removeEntry(String fullPathToEntry, Entry<String, Object> entry)
	{
		return false;
	}

	@Override
	public boolean recurInto(String fullPath, JSONObject map)
	{
		if ("/".equals(fullPath)) {
			return true;
		}
		return commitData(fullPath, map);
	}
	@Override
	public boolean recurInto(String fullPath, JSONArray entryValue)
	{
		return true;
	}

	@Override
	public void handleLeaf(String fullPath, Entry<String, Object> entry) {
	}

	@Override
	public void handleLeaf(String fullPathToContainingList, int listIndex, Object listItem) {
	}

	@Override
	public void end() {
	}

	@Override
	public Object result()
	{
		return null;
	}

	private boolean commitData(String fullPath, Map<String, Object> map)
	{
		String data = map.get("data") != null ? (String) map.get("data") : "";
		try
		{
			Stat stat = curator.checkExists().forPath(fullPath);
			if (stat == null)
			{
				curator.create().forPath(fullPath, data.getBytes());
			}
			else
			{
				curator.setData().forPath(fullPath, data.getBytes());
			}
			return true;
		}
		catch (Exception e)
		{
			logger.error("could not create path for: " + fullPath + " with: " + data, e);
			throw new IllegalStateException(e);
		}
	}
}
