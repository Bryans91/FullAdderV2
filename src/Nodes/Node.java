package Nodes;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class Node {
	
	protected String name = "";
	protected ArrayList<Node> parents = new ArrayList<Node>(); //child nodes
	protected ArrayList<Node> children = new ArrayList<Node>(); //child nodes
	protected ArrayList<Integer> input = new ArrayList<Integer>(); //collection of inputs
	protected int output; //output variable
	protected int inputcount = 2;
	protected boolean visited = false;
	protected boolean looped = false;
	
	
	public void handle() throws Exception {
		
		//check if all input recieved
		if(this.input.size() == this.inputcount) {
			if(!this.visited) {
				this.visited = true;
				//set output of node
				this.output = this.doCalc();
				
				//System.out.println(this.getInfo());
				
				//iterate children and pass on output
				for (Iterator<Node> nodes = this.children.iterator(); nodes.hasNext();) {
					Node node = nodes.next();
					node.addInput(this.output);
					
					//call next handle operation
					node.handle();
				}
			} else {
				this.looped = true;
			}
		}
		
	}
	
	public int doCalc(){
		return this.output;
	}
	
	public String getInfo() {
		String info = "\n";
		for (Node node : this.parents) {
			info = info + node.getInfo();
		}
		
		return info + this.getName() + "-" + this.getClass().getSimpleName() + " -> " + this.output + " ";
	}
	
	public void printTree() {
		

		System.out.print(this.getName() + "("+ this.getClass().getSimpleName()+") = Out: " + this.output);
		
		if(this.children.size() > 0) {
			System.out.println("\nChildren:");
			for (Node node : this.children) {
				System.out.print("\t" + node.getName() + "("+ node.getClass().getSimpleName()+")");
			}
			System.out.print("\n");
		
			
	
			for (Node node : this.children) {
				node.printTree();
			}
		} else {
			System.out.println("\n__________________________________\n");
		}
	}

	
	
	public void addChild(Node node) {
		this.children.add(node);
		node.addParent(this);
	}
	
	public void addParent(Node node) {
		this.parents.add(node);
	}
	
	public void addInput(int in) throws Exception {
		if(this.input.size() < 2){
			this.input.add(in);
		} else {
			throw new Exception("Trying to add more than 2 inputs to a node, circuit invalid");
		}
	}
	
	public int getOutput() {
		return this.output;
	}
	
	public boolean getVisited() {
		return this.visited;
	}
	
	public boolean getLooped() {
		return this.looped;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	
}
