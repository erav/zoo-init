package com.github.erav.zooinit;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * @author adoneitan@gmail.com
 * @since 6/26/16.
 */
public class FileSystemTest
{
	@Test
	public void readFile() throws Exception
	{
		String content = FileSystem.readContent("to_commit.json", "to_commit.json");
		assertTrue(content != null && !content.isEmpty());
	}
}