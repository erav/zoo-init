# zoo-init
Initialize a zookeeper instance with data from a json file. full delete and dump also supported.

A zookeeper's tree of nodes and their data is formattable to a JSON object which is in essence a tree as well. This possibility is used to automate the loading\unloading of data for zookeeper.
For a given namespace, the following actions are supported:
- populate a zookeeper instance with content from a JSON file.
- dump a zookeeper instance's content tree to a JSON file.
- delete a zookeeper instance's content.

## Note: This tool should be used only for initialization of a zookeeper node and before the zookeeper cluster is online and serving requests from outside the cluster. 
