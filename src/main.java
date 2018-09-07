import Circuit.Circuit;
import Circuit.NodeFactory;
import Nodes.AND;
import Nodes.NAND;
import Nodes.NOT;
import Nodes.Node;
import Nodes.OR;
import Nodes.PROBE;
import Nodes.XOR;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		
		Circuit C = new Circuit("Circuit1_FullAdder.txt", 0);
		
//		C.printNodes();
//		C.printStarters();
		
		C.run();
		
	
		
	
	}

}
