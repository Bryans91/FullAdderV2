/**
 * 
 */
package test;

import Nodes.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


/**
 * @author bryan
 *
 */
public class AndTest {
	
	
	private Node and;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() {
		this.and = new AND();
		try {
			this.and.addInput(1);
			this.and.addInput(1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Test
	public void test() {
		assertTrue("Check if result == 1", this.and.doCalc() == 1);
		
	}

}
