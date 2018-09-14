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
public class NorTest {
	
	
	private Node nor;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() {
		this.nor = new NOR();
		try {
			this.nor.addInput(0);
			this.nor.addInput(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Test
	public void test() {
		assertTrue("Check if result == 1", this.nor.doCalc() == 1);
		
	}

}
