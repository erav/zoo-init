package com.github.erav.zooinit;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import com.github.erav.treemod.path.SlashDelimiter;
import com.github.erav.treemod.traverse.TreeTraverser;
import net.minidev.json.parser.ParseException;
import org.apache.curator.framework.CuratorFramework;

/**
 * @author adoneitan@gmail.com
 * @since 6/22/16.
 */
public class ZooPopulate
{
	protected CuratorFramework zkCurator;

	public ZooPopulate(CuratorFramework zkCurator)
	{
		this.zkCurator = zkCurator;
	}

	public void populate(String json) throws ParseException
	{
		ZooPopulateAction toZk = new ZooPopulateAction(zkCurator);
		TreeTraverser tr = new TreeTraverser(toZk, new SlashDelimiter()).with("/");
		tr.traverse((JSONObject) JSONValue.parseWithException(json));
	}
}
