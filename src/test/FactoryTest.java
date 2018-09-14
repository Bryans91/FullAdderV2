/**
 * 
 */
package test;
import java.util.ArrayList;

import Nodes.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import Circuit.NodeFactory;

/**
 * @author bryan
 *
 */
public class FactoryTest {
	
	
	private NodeFactory factory;
	private Node node = null;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() {
		this.factory = NodeFactory.getInstance();
		
		this.node = factory.createNode("NOT", "NODE1");
		
	}

	@Test
	public void test() {
		assertTrue("Check if node created",this.node != null);
		assertTrue("Check if node name is NODE1",this.node.getName().equals("NODE1"));
		assertTrue("Check if node type is NOT",this.node.getClass().getSimpleName().equals("NOT"));
		
	}

}
