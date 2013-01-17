package gregsharek;

import java.io.StringWriter;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * FibCalc
 * 
 * This object uses JAX-RS and can be deployed as a RESTful web service.
 * 
 * When deployed as a web service, the object accepts the following HTTP method & URI:
 * 
 * GET /fibonacci/<non-negative-integer n> - returns the first n instances in the Fibonacci sequence. Using the following
 * sample XML format:
 * 
 * <fibonacci>
 *   <value index="0">0</value>
 *   <value index="1">1</value>
 *   <value index="2">1</value>
 *   <value index="3">2</value>
 * </fibonacci>
 * 
 * 404 is returned for all other values of n, including negative integers.
 * 
 * @author gsharek
 *
 */
@Path( "/fibonacci/{count: [0-9]+}" )
public class FibCalc {
		
	/**
	 * Exception indicates that the count parameter was invalid.  This is thrown when the value is negative.
	 * 
	 * @author gsharek
	 *
	 */
	static class InvalidCountException extends Exception { 
		
		InvalidCountException( String reason ) {
			super( reason );
		}		
	};
	
	/**
	 * When thrown, causes the server request to return HTTP status 500 & the entity will be:
	 * 
	 *  <error> <message value> </error>
	 * 
	 * @author gsharek
	 *
	 */
	static class InternalErrorException extends WebApplicationException {
		
		/**
		  * When thrown, causes the server request to return HTTP status 500 & the entity will be:
		 * 
		 *  <error> <message value> </error>
	 	 *
		 * @param message error string
		 */
		public InternalErrorException( String message ) {
			
			super( Response.status( Response.Status.INTERNAL_SERVER_ERROR ).entity( "<error>" + message + "<error>" ).type( "application/xml" ).build() );
		}		
	}
	
	/**
	 * Calculates the first <count> number of instances in the Fibonacci sequence.
	 * 
	 * @param count - the first number of instances in the Feibonacci sequence returned
	 * @return a long[] which contains the first <count> number of instances in the Fibonacci sequence
	 * 
	 * @throws InvalidCountException throw when count is < 0
	 */
	private long[] calcFib( int count ) throws InvalidCountException {
		
		if ( count < 0 ) {
			
			throw new InvalidCountException( "Series length must be greater than or equal to zero.  Submited length: '" + count + "'"  );
		}
		
		long[] series = new long[ count ];
		
		long first = 0, second=1;
		
		for( int i = 0; i < count; i++ ) {
			series[ i ] = first;
			first = second;
			second = series[ i ] + second;
		}
		
		return series;		
	}
	
	/**
	 * This method is exposed as a RESTful web service, using GET.  Returns the first <countParam> in the Fibonacci sequence
	 * using the following sample XML format:
     * 
     * <fibonacci>
     *   <value index="0">0</value>
     *   <value index="1">1</value>
     *   <value index="2">1</value>
     *   <value index="3">2</value>
     * </fibonacci>
     * 
     * 404 is returned for all other values of countParam, including negative integers.
	 * 
	 * @param countParam - the first number of instances in the Fibonacci sequence returned
	 * 
	 * @return the above XML string
	 */
	@GET
	@Produces( "application/xml" )
	public String getFib( @PathParam( "count" ) String countParam ) {
		
		try {
		
			int count = Integer.valueOf( countParam );
			
			//
			// create XML output
			//
			
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
				
			Element root = doc.createElement( "fibonacci" );
			doc.appendChild( root );
						
			long[] series = calcFib( count );
			
			for( int i = 0; i < series.length; i++ ) {
			
				Element value = doc.createElement( "value" );
				root.appendChild( value );
				
				value.appendChild( doc.createTextNode( String.valueOf( series[ i ] ) ) ) ;
				
				Attr attr = doc.createAttribute( "index" );
				attr.setValue( String.valueOf( i ) ) ;
				
				value.setAttributeNode( attr );
			}

			//
			// write out XML string
			//
			
			Transformer trans = TransformerFactory.newInstance().newTransformer();
			trans.setOutputProperty( OutputKeys.OMIT_XML_DECLARATION, "yes" );
			trans.setOutputProperty( OutputKeys.INDENT, "yes" );
			
			StringWriter writer = new StringWriter();
			trans.transform( new DOMSource( doc ), new StreamResult( writer ) );
						
			return writer.getBuffer().toString();			
		}
		catch (Exception e ) {
			
			throw new InternalErrorException( e.getMessage() );
		}
	}	
}
