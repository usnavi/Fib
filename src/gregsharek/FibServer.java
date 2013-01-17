package gregsharek;

import java.io.IOException;
import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import org.glassfish.grizzly.http.server.HttpServer;

import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;

/**
 * This demonstration uses the Grizzly web server shipped with the Jersey implementation of JAX-RS.
 * 
 * Once executed, it accepts requests at http://localhost:8080/fibonacci/<non-negative integer>
 * 
 * Hit return to exit the program.
 * 
 * @author gsharek
 *
 */
public class FibServer {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		
		URI uri = UriBuilder.fromUri( "http://localhost/" ).port( 8080 ).build(); 
		
		HttpServer server = GrizzlyServerFactory.createHttpServer( uri, 
				new PackagesResourceConfig( "gregsharek" ) ); 
		
		System.out.println( "Accepting requests at http://localhost:8080/<non-negative integer>  Hit return to exit." );
		System.in.read();
		server.stop();
	}

}
