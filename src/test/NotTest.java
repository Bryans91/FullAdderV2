/**
 * 
 */
package test;
import java.util.ArrayList;

import Nodes.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import Nodes.NOT;
import Nodes.Node;

/**
 * @author bryan
 *
 */
public class NotTest {
	
	
	private Node not;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() {
		this.not = new NOT();
		try {
			this.not.addInput(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Test
	public void test() {
		assertTrue("Check if result == 1", this.not.doCalc() == 1);
		
	}

}
