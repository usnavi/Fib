package gregsharek.test;

import gregsharek.FibCalc;

import javax.ws.rs.WebApplicationException;

import junit.framework.TestCase;

import org.junit.Assert;

/**
 * TestFibCalc
 * 
 * JUnit tests to verify the public methods of FibCalc and interesting corner cases.
 * 
 * @author gsharek
 *
 */
public class TestFibCalc extends TestCase {

	/**
	 * Verify FibCalc.getFib method with given integer & expected output string.
	 * 
	 * @param count - input parameter
	 * @param gold - expected output
	 */
	private void assertGetFib( int count, String gold ) {
		
		FibCalc fibcalc = new FibCalc();

	   Assert.assertEquals( gold, fibcalc.getFib( String.valueOf(  count ) ) );				
	}
	
	public void testGetFibZero() {
		
		assertGetFib( 0, "<fibonacci/>\n" );
	}

	public void testGetFibOne() {
		
		assertGetFib( 1, "<fibonacci>\n<value index=\"0\">0</value>\n</fibonacci>\n" );
	}

	public void testGetFibTwo() {
		
		assertGetFib( 2, "<fibonacci>\n<value index=\"0\">0</value>\n<value index=\"1\">1</value>\n</fibonacci>\n" );
	}

	public void testGetFibFive() {
		
		assertGetFib( 5, "<fibonacci>\n<value index=\"0\">0</value>\n<value index=\"1\">1</value>\n<value index=\"2\">1</value>\n<value index=\"3\">2</value>\n<value index=\"4\">3</value>\n</fibonacci>\n" );
	}
	
	/**
	 * Verify FibCalc.getFib correctly throws an exception when given a negative integer.
	 */
	public void testGetFibBadCount() {
		
		FibCalc fibcalc = new FibCalc();
		
		int count = -1;
		
		try {
			fibcalc.getFib( String.valueOf( -1 ) );				
		} catch ( WebApplicationException e ) {
			
			Assert.assertEquals( 500, e.getResponse().getStatus() );
			Assert.assertEquals( "<error>Series length must be greater than or equal to zero.  Submited length: '" + count + "'<error>", (String)e.getResponse().getEntity() );
			
			return;
		}
		
		Assert.fail();
	} 
}
