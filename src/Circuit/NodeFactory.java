package Circuit;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;

import Nodes.*;
import Nodes.Node;

public class NodeFactory {
	
	//instance
	private static NodeFactory instance = null;
	
	public static NodeFactory getInstance() {
		if(instance == null) {
			instance = new NodeFactory();
		}
		return instance;
	 }
	
	//collection of all node types
	private HashMap<String, Node> commands; 
	
	//constructor
	public NodeFactory() {
		//load node options by filename
		this.commands = new HashMap<String, Node>();
		ServiceLoader<Node> loader = ServiceLoader.load(Node.class);
		for (Node command : loader) {
			if(command.getClass().getSimpleName() != "Node") {
				this.commands.put(command.getClass().getSimpleName(), command);
			}
		}
		
	}
	
	public Node createNode(String nodeType , String name){	
		Node returnNode = this.createNode(nodeType);
		if(returnNode != null) returnNode.setName(name);
		return returnNode;
	}
	
	public Node createNode(String nodeType){	
	
		Node node = null;
		try {
			if (commands.get(nodeType) != null) {		
				try {
					node = (Node) Class.forName("Nodes."+nodeType).newInstance();
				} catch (InstantiationException e) {
					System.out.println("Can't create node");
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					System.out.println("Can't access node");
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					System.out.println("No node with that name was found");
					e.printStackTrace();
				}
			} else {
				System.out.println("Not found");
			}
		} catch(Exception e) {
			System.out.println("Nodetype not present in map");
		}
		
		// If node is null, not a valid node.
		if (node != null) {

			return node;
		}
		return null;
	}
	
	public void printOptions() {
		Map mp = this.commands;
		System.out.println("Nodemap:");
	    Iterator it = mp.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        System.out.println(pair.getKey() + " = " + pair.getValue());
	        it.remove(); // avoids a ConcurrentModificationException
	    }
	}
	
}