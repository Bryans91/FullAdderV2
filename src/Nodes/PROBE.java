package Nodes;


public class PROBE extends Node {

	
	public void handle() {
		//if results are in set output
		
		if(this.input.size() == 1) {
			this.output = this.input.get(0);
		}
		
		System.out.println("Output is: " + this.output);
	}
	
}