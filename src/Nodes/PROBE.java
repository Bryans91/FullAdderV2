package Nodes;


public class PROBE extends Node {

	//1 input
	
	public void handle() {
		//if results are in set output
		
		if(this.input.size() == 1) {
			this.visited = true;
			this.output = this.input.get(0);
		}
	}
	
}