package com.github.erav.zooinit;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

/**
 * @author adoneitan@gmail.com
 * @since 6/26/16.
 */
public class CliArgs
{
	public static CliArgs args = new CliArgs();

	@Parameter(names = {"-h", "--help"}, description = "shows help about this program", help = true)
	Boolean help = false;

	@Parameter(names = {"-f", "--filename"}, description = "name of file with json to load to zookeeper", required = true)
	String filename = "";

	@Parameter(names = {"-s", "--zk_server"}, description = "zookeeper server name (host:port)", required = true)
	String zkServer = "";

	@Parameter(names = {"-n", "--namespace"}, description = "zookeeper namespace to work under", required = true)
	String namespace = "";

	@Parameter(names = {"-a", "--action"}, description = "action to perform {commit, delete, dump}", required = true)
	String action = "";


	public static Boolean init(String[] args)
	{
		JCommander parser = new JCommander();
		parser.addObject(CliArgs.args);
		parser.parse(args);
		if (CliArgs.args.help) {
			parser.usage();
			System.out.println("  ** sample usage");
			System.out.println("\t\t-s localhost:2181 -f to_commit.json -n tester -a commit");
			System.out.println();
			System.out.println();
			System.exit(1);
		}
		return true;
	}

}
