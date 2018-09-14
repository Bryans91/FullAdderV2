/**
 * 
 */
package test;
import java.util.ArrayList;

import Nodes.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author bryan
 *
 */
public class NandTest {
	
	
	private Node nand;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() {
		this.nand = new NAND();
		ArrayList<Integer> input = new ArrayList<Integer>();
		try {
			this.nand.addInput(1);
			this.nand.addInput(1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Test
	public void test() {
		assertTrue("Check if result == 0", this.nand.doCalc() == 0);
		
	}

}
