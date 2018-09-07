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

		Circuit circ = new Circuit("Circuit1_FullAdder.txt");

		circ.run();
		//circ.printStarters();
		//circ.printNodes();
	
	}

}
