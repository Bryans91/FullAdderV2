package Circuit;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

import Nodes.Node;
import Nodes.PROBE;

public class Circuit {
	
	private boolean readCarryIn = false;
	private int a, b, carryIn;
	private NodeFactory factory;
	private String fileName;

	private ArrayList<String> starting;
	
	private Node Cout,S;
	
	private HashMap<String, Node> allNodes; 
	
	
	public Circuit(String fileName){	
		this(fileName, 0);
		readCarryIn = true;
	}
	
	public Circuit(String fileName, int carryIn){
		this.allNodes = new HashMap<String, Node>();
		this.starting = new ArrayList<String>();
		this.fileName = fileName;
		this.factory = NodeFactory.getInstance();
		
		//inputs
		this.carryIn = carryIn;
		this.a = 0; //default
		this.b = 0; //default
		
	}
	
	public void run() {
		this.create();
		
		for (String key : this.starting) {
			try {
				System.out.println("Starting: " + key);
				this.allNodes.get(key).handle();
			} catch (Exception e) {
				System.out.println("Something went wrong handling the circuit;");
				e.printStackTrace();
			}
		}
		
		System.out.println("Output: \n CarryOut: "+ this.getCarryOutValue() + " \n S: "+this.getSValue());
		
	}
	

	public void create() {
		ArrayList<String> lines = this.readFile(this.fileName);
		
		for (Iterator<String> line = lines.iterator(); line.hasNext();) {
		    String item = line.next();
		    	    
		    if(item.contains("A:") || item.contains("B:") || item.contains("Cin:") ) {
		    	//set or couple input
		    	this.handleInput(item);
		    } else if(item.contains("S:") || item.contains("Cout:")) {
		    	//handle outputs
		    	this.handleOutput(item);
		    } else if(item.contains("NODE")) {
		    	this.handleNode(item);
		    }
		}
		
		//set output values
		this.Cout = this.allNodes.get("Cout");
		this.S = this.allNodes.get("S");
	}
	
	private void handleNode(String line) {
		String parent = line.substring(0, line.indexOf(':'));
		
		if(countMatches(line,"NODE") > 1 || line.contains("Cout") || line.contains("S")) {
			//link
			
			String values = line.substring(line.indexOf(":")+1, line.length() -1);
			String[] children = values.split(",");
			
			//add children to node
			for(String child : children) {
				//add child
				System.out.println("Add child: " + child);
				this.allNodes.get(parent).addChild(this.allNodes.get(child));
			}
			 
		} else {
			//create
			String type = line.substring(line.indexOf(":")+1, line.length() -1);
			this.allNodes.put(parent,factory.createNode(type));
		}
		
	}
	
	private void handleOutput(String line) {
		//create
		if(line.contains("S:")) this.allNodes.put("S", factory.createNode("PROBE"));
		if(line.contains("Cout:")) this.allNodes.put("Cout", factory.createNode("PROBE"));
	}
	
	private void handleInput(String line) {
		if(line.contains("NODE")) {
			//set input & starters
			int input = this.a;
			if(line.contains("B:")) input = this.b;
			if(line.contains("Cin:") && readCarryIn) input = this.carryIn;
			
			String values = line.substring(line.indexOf(":")+1, line.length() -1);
			String[] nodes = values.split(",");

			//each node add input
			for(String node : nodes) {
				if(!this.starting.contains(node)) this.starting.add(node);
				try {
					System.out.println("Add input: " + input + " TO: " + node);
					this.allNodes.get(node).addInput(input);
				} catch (Exception e) {
					System.out.println("Error adding input");
					e.printStackTrace();
				}
			}
		} else {
			//set values
			int input = 0;
			if(line.contains("INPUT_HIGH")) input = 1;
			
			if(line.contains("A:")) this.a = input;
			if(line.contains("B:")) this.b = input;
			if(line.contains("Cin:")) this.carryIn = input;
		}
	}
	
	public int countMatches(String str, String sub) {
		if (str == "" || sub == "") {
			return 0;
		}
		int count = 0;
		int idx = 0;
		while ((idx = str.indexOf(sub, idx)) != -1) {
		   count++;
		   idx += sub.length();
		}
		return count;
	}
	

	private ArrayList<String> readFile(String fileName){
		ArrayList<String> lines = new ArrayList<String>();
		try
        {
			FileReader fr = new java.io.FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            String s;
            while ((s = br.readLine()) != null)
            {
            	//if not a comment && clean up line
            	s = s.replaceAll("\\t", "").replaceAll("\\n", "").replaceAll(" ", "").replaceAll("  ","").replaceAll("   ","");
            	if(!s.contains("#")) {
            		lines.add(s);
            	}
            }
                	
        }
        catch (FileNotFoundException e){ 
        	System.out.println("File: " + fileName + " does not exist. Exiting program."); System.exit(1); 
    	}
        catch (IOException e){ 
        	System.out.println("Failed to read file: " + fileName + ". Exiting program."); System.exit(1); 
    	}
	    return lines;
	}
	
	public void printNodes() {
		System.out.println("All known nodes:");
		for (String key : this.allNodes.keySet()) {
			System.out.println(key + " : " + this.allNodes.get(key).getClass().getSimpleName());
		}
	}
	
	public void printStarters() {
		System.out.println("Starter nodes");
		for (String key : this.starting) {
			System.out.println(key);
		}
	}
	
	public int getCarryOutValue() {
		return this.Cout.getOutput();
	}
	public int getSValue() {
		return this.S.getOutput();
	}

}
