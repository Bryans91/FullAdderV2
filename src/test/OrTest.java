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
public class OrTest {
	
	
	private Node or;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() {
		this.or = new OR();
		
		try {
			this.or.addInput(0);
			this.or.addInput(1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Test
	public void test() {
		assertTrue("Check if result == 1", this.or.doCalc() == 1);
		
	}

}
