package Nodes;

public class XOR extends Node {
	
	protected int inputCount = 2;
	
	public int doCalc(){
		System.out.println("XOR:"+this.input.get(0) + " | "+ this.input.get(1));
		int out = 0;
		
		if(this.input.get(0) != this.input.get(1)){
			out = 1;
		}
		
		return out;
	}

}
