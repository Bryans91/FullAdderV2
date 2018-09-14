/**
 * 
 */
package test;
import java.util.ArrayList;

import Nodes.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import Circuit.Circuit;

/**
 * @author bryan
 *
 */
public class CircuitTest {
	
	
	private Circuit circuit;
	private String compare = "fox", subject = "The lazy fox jumps over the fox fox";
	private int result;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() {
		this.circuit = new Circuit("Circuit1_FullAdder.txt");
	}

	@Test
	public void test() {
		assertTrue("Check if countMatches works ", this.circuit.countMatches(this.subject, this.compare) == 3);
	
		
	}

}
