package gregsharek.test;

import junit.framework.TestCase;

import org.junit.Assert;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * TestFibonacciREST
 * 
 * JUnit tests to verify the GET response of the FibCalc object deployed as a JAX-RS web service.
 * 
 * @author gsharek
 *
 */
public class TestFibonacciREST extends TestCase {

	private static final String URI = "http://localhost:8080/fibonacci/";
	
	/**
	 * Verify the Fib REST server with given count & expected output string.
	 * 
	 * @param count - input parameter
	 * @param gold - expected output
	 */
	private void assertREST( int count, String gold ) {
		
		Client client = Client.create();
		
		WebResource web = client.resource( URI + count );
		
		ClientResponse resp = web.accept( "application/xml" ).get( ClientResponse.class );
		
		Assert.assertEquals( 200, resp.getStatus() );
		
		Assert.assertEquals( gold, resp.getEntity( String.class ) );
	}
	
	public void testGetFibZero() {
		
		assertREST( 0, "<fibonacci/>\n" );
	}
	
	public void testGetFibOne() {
		
		assertREST( 1, "<fibonacci>\n<value index=\"0\">0</value>\n</fibonacci>\n" );
	}

	public void testGetFibTwo() {
		
		assertREST( 2, "<fibonacci>\n<value index=\"0\">0</value>\n<value index=\"1\">1</value>\n</fibonacci>\n" );
	}

	public void testGetFibFive() {
		
		assertREST( 5, "<fibonacci>\n<value index=\"0\">0</value>\n<value index=\"1\">1</value>\n<value index=\"2\">1</value>\n<value index=\"3\">2</value>\n<value index=\"4\">3</value>\n</fibonacci>\n" );
	}
	
	public void testGetFibBadCount() {
		
		Client client = Client.create();
		
		WebResource web = client.resource( URI + "-1" );
		
		ClientResponse resp = web.accept( "application/xml" ).get( ClientResponse.class );
		
		Assert.assertEquals( 404, resp.getStatus() );		
	}	
}