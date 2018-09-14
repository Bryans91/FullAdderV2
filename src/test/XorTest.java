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
public class XorTest {
	
	private Node xor;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() {
		this.xor = new XOR();		
		try {
			this.xor.addInput(1);
			this.xor.addInput(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Test
	public void test() {
		assertTrue("Check if result == 1", this.xor.doCalc() == 1);
		
	}

}
