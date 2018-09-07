package Nodes;

import java.util.Iterator;

public class NOT extends Node {
	
	protected int inputCount = 1;
	
	public void handle() throws Exception {
		//check if all input recieved
		if(this.input.size() == 1) {
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
		System.out.println("NOT:"+this.input.get(0));
		int out = 0;
		
		if(this.input.get(0) == 0){
			out = 1;
		}
		
		return out;
	}

}
