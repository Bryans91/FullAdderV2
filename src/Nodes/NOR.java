package Nodes;

public class NOR extends Node {

	//2 inputs
	
	public int doCalc(){
		
		int out = 0;
		
		if(this.input.get(0) == 0 && this.input.get(1) == 0){
			out = 1;
		}
		
		return out;
	}
	
}
