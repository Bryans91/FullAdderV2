package Nodes;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class Node {
	
	protected ArrayList<Node> children = new ArrayList<Node>(); //child nodes
	protected ArrayList<Integer> input = new ArrayList<Integer>(); //collection of inputs
	protected int output; //output variable
	protected String name; //identifier
	
	
	public void handle() throws Exception {
		
		//check if all input recieved
		if(this.input.size() == 2) {	
			
			//set output of node
			this.output = this.doCalc();
			
			//iterate children and pass on output
			for (Iterator<Node> nodes = this.children.iterator(); nodes.hasNext();) {
				Node node = nodes.next();
				node.addInput(this.output);
				
				//call next handle operation
				node.handle();
			}
		}
		
	}
	
	public int doCalc(){
		return this.output;
	}
	
	public void addChild(Node node) {
		this.children.add(node);
	}
	
	public void addInput(int in) throws Exception {
		if(this.input.size() <= 2){
			this.input.add(in);
		} else {
			throw new Exception("Trying to add more than 2 inputs to a node, circuit invalid");
		}
	}
	
	public int getOutput() {
		return this.output;
	}
	
	
}
