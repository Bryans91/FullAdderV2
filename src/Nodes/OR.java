package Nodes;

public class OR extends Node {
	
	protected int inputCount = 2;
	
	public int doCalc(){
		System.out.println("OR:"+this.input.get(0) + " | "+ this.input.get(1));
		int out = 0;
		
		if(this.input.get(0) == 1 || this.input.get(1) == 1){
			out = 1;
		}
		
		return out;
	}

}
