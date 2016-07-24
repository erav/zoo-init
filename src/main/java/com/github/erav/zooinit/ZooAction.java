package com.github.erav.zooinit;

/**
 * @author adoneitan@gmail.com
 * @since 7/21/16
 */
public class ZooAction
{
	static boolean isDelete(String action) {return "delete".equalsIgnoreCase(action);}

	static boolean isDump(String action) {return "dump".equalsIgnoreCase(action);}

	static boolean isCommit(String action) {return "commit".equalsIgnoreCase(action);}
}
