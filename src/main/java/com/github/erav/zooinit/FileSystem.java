package com.github.erav.zooinit;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


/**
 * @author adoneitan@gmail.com
 * @since 6/22/16.
 */
public class FileSystem
{
	private static final Logger logger = LogManager.getLogger(FileSystem.class);

	public static String readFile(String filename) throws FileNotFoundException {
		return readFile(new File(filename));
	}

	public static String readFile(File file) throws FileNotFoundException {
		return new Scanner(file).useDelimiter("\\Z").next();
	}

	public static String readContent(String resource, String defaultResource) throws FileNotFoundException
	{
		String content;
		if (new File(resource).exists())
		{
			File fullPath = new File(resource);
			content = readFile(fullPath);
			reportResourcePath(fullPath.getAbsolutePath());
		}
		else if (new File(currentRuntimePath() + resource).exists())
		{
			File relPath = new File(currentRuntimePath() + resource);
			content = readFile(relPath);
			reportResourcePath(relPath.getAbsolutePath());
		}
		else if (exists(resource))
		{
			content = readFile(resource);
			reportResourcePath(resource);
		}
		else if (exists(defaultResource))
		{
			logger.warn("could not find specified resource, loading default resource");
			reportResourcePath(defaultResource);
			content = readFile(defaultResource);
		}
		else
		{
			reportResourcePath("");
			throw new IllegalArgumentException("no configuration specified or found. cannot continue.");
		}

		return content;
	}

	private static void reportResourcePath(String resource)
	{
		logger.info("current runtime path: '" + currentRuntimePath() + "'");
		logger.info("loading test configuration from: '" + new File(resource).getPath() + "'");
	}

	public static String currentRuntimePath() {
		return FileSystem.class.getClassLoader().getResource(".").getPath();
	}

	public static boolean exists(String resourcePath) {
		return ClassLoader.getSystemResourceAsStream(resourcePath) != null;
	}
}
