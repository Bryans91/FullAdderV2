package Circuit;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
	private ArrayList<String> errorbag;
	
	
	private Node Cout,S;
	
	private HashMap<String, Node> allNodes; 
	
	
	public Circuit(String fileName){	
		this(fileName, 0);
		readCarryIn = true;
	}
	
	public Circuit(String fileName, int carryIn){
		this.allNodes = new HashMap<String, Node>();
		this.starting = new ArrayList<String>();
		this.errorbag = new ArrayList<String>();
		this.fileName = fileName;
		this.factory = NodeFactory.getInstance();
		
		//inputs
		this.carryIn = carryIn;
		this.a = 0; //default
		this.b = 0; //default
		
	}
	
	public void run() {
		
				
		try {
			//override file input
			this.fileName = overrideFile();
			
			this.create();	
			
		} catch(Exception e) {
			System.out.println("Something went wrong creating the circuit");
		}
		
		
		
		try {
			System.out.print("Startvalues: \n A:"+this.a + " \n B:"+this.b+" \n Cin:"+this.carryIn+"\n");
			//run circuit
			for (String key : this.starting) {
				try {
					this.allNodes.get(key).handle();
				} catch (Exception e) {
					System.out.println("Something went wrong handling the circuit;");
					e.printStackTrace();
				}
			}
		} catch(Exception e) {
			System.out.println("Something went wrong running the circuit");
		}
		
		try {
			//checks
			this.checkVisited(); //check not connected
			this.checkLooped(); //check infinite loop
			
			if(this.errorbag.size() > 0) {
				for (String error : this.errorbag) {
					System.out.println(error);
				}
				
			} else {
				for (String key : this.starting) {
					this.allNodes.get(key).printTree();
				}
				
				System.out.println("Output: \n CarryOut: "+ this.getCarryOutValue() + " \n S: "+this.getSValue());
				
			}
		} catch(Exception e) {
			System.out.println("Something went wrong when checking and showing the results");
		}
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
				this.allNodes.get(parent).addChild(this.allNodes.get(child));
			}
			 
		} else {
			//create
			String type = line.substring(line.indexOf(":")+1, line.length() -1);
			this.allNodes.put(parent,factory.createNode(type, parent));
		}
		
	}
	
	private void handleOutput(String line) {
		//create
		if(line.contains("S:")) this.allNodes.put("S", factory.createNode("PROBE", "S"));
		if(line.contains("Cout:")) this.allNodes.put("Cout", factory.createNode("PROBE", "Cout"));
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
			
			if(line.contains("A:")) {
				this.a = input;
				this.a = this.overrideInputs("A", this.a);
			}
			
			
			if(line.contains("B:")) {
				this.b = input;
				this.b = this.overrideInputs("B", this.b);
			}
			
			if(line.contains("Cin:")) {
				this.carryIn = input;
				this.carryIn = this.overrideInputs("Cin", this.carryIn);
			}
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
	
	public void checkVisited() {
		boolean error = false;
		for (Node node : this.allNodes.values()) {
			if(!node.getVisited()) error = true; 
		}
		if(error) this.errorbag.add("One or more nodes were not visited, circuit incorrect!");
	}
	
	public void checkLooped() {
		boolean error = false;
		for (Node node : this.allNodes.values()) {
			if(node.getLooped()) error = true; 
		}
		if(error) this.errorbag.add("One or more nodes were visited multiple times, circuit incorrect!");
	}
	
	public void printNames() {
		for (Node node : this.allNodes.values()) {
			System.out.println(node.getName());
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
	
	public int overrideInputs(String id, int current) {
		System.out.println("Set value for " + id +", currently("+ current + " from file): Leave empty for current");
		try {
			String input = this.readInput();
			if(!input.isEmpty()) {
				int var = Integer.parseInt(input);
				
				if(var == 1 || var == 0) {
					return var;
				}
			}
		} catch(Exception e) {
			System.out.println("Unable to parse int, using default");
		}
		return current;
	}
	
	public String overrideFile() {
		System.out.println("Change file name? (Current: " + this.fileName + "): Leave empty for current");
		String override = this.readInput();
		
		if(!override.isEmpty()) return override;
			
		return this.fileName;
	}
	
	
	public String readInput() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String s = "";
		try {
			s = br.readLine();
		} catch (IOException e) {
			System.out.println("Error reading input");
		}
		return s;
		
	}
	
	

}
