package de.hska.iwi.vislab.lab2.example;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.NotAllowedException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class FibonacciTest {

	private HttpServer server;
	private WebTarget target;

	@Before
	public void setUp() throws Exception {
		// start the server
		server = Main.startServer();
		// create the client
		Client c = ClientBuilder.newClient();

		// uncomment the following line if you want to enable
		// support for JSON in the client (you also have to uncomment
		// dependency on jersey-media-json module in pom.xml and
		// Main.startServer())
		// --
		// c.configuration().enable(new
		// org.glassfish.jersey.media.json.JsonJaxbFeature());

		target = c.target(Main.BASE_URI);
	}

	@After
	public void tearDown() throws Exception {
		server.shutdown();
	}

	@Test
	public void testInvalidMethod() {
		assertThrows(NotAllowedException.class, () ->
			target.path("fibonacci").request().accept(MediaType.TEXT_PLAIN).get(String.class)
		);
	}

	@Test
	public void testNextFibonacciWithoutCookie() {
		Response response = target.path("fibonacci").request().accept(MediaType.TEXT_PLAIN).post(null);

		assertEquals("fibonacci(0) = 0", response.readEntity(String.class));
		assertEquals("0", response.getCookies().get("FibonacciIndex").getValue());
	}

	@Test
	public void testNextFibonacciWithCookieIndexReset() {
		Response response = target.path("fibonacci").request().accept(MediaType.TEXT_PLAIN).cookie("FibonacciIndex", "RESET").post(null);

		assertEquals("fibonacci(0) = 0", response.readEntity(String.class));
		assertEquals("0", response.getCookies().get("FibonacciIndex").getValue());
	}

	@Test
	public void testNextFibonacciWithCookieIndex0() {
		Response response = target.path("fibonacci").request().accept(MediaType.TEXT_PLAIN).cookie("FibonacciIndex", "0").post(null);

		assertEquals("fibonacci(1) = 1", response.readEntity(String.class));
		assertEquals("1", response.getCookies().get("FibonacciIndex").getValue());
	}

	@Test
	public void testNextFibonacciWithCookieIndex1() {
		Response response = target.path("fibonacci").request().accept(MediaType.TEXT_PLAIN).cookie("FibonacciIndex", "1").post(null);

		assertEquals("fibonacci(2) = 1", response.readEntity(String.class));
		assertEquals("2", response.getCookies().get("FibonacciIndex").getValue());
	}

	@Test
	public void testNextFibonacciWithCookieIndex2() {
		Response response = target.path("fibonacci").request().accept(MediaType.TEXT_PLAIN).cookie("FibonacciIndex", "2").post(null);

		assertEquals("fibonacci(3) = 2", response.readEntity(String.class));
		assertEquals("3", response.getCookies().get("FibonacciIndex").getValue());
	}

	@Test
	public void testNextFibonacciWithCookieIndex3() {
		Response response = target.path("fibonacci").request().accept(MediaType.TEXT_PLAIN).cookie("FibonacciIndex", "3").post(null);

		assertEquals("fibonacci(4) = 3", response.readEntity(String.class));
		assertEquals("4", response.getCookies().get("FibonacciIndex").getValue());
	}

	@Test
	public void testNextFibonacciWithCookieIndex4() {
		Response response = target.path("fibonacci").request().accept(MediaType.TEXT_PLAIN).cookie("FibonacciIndex", "4").post(null);

		assertEquals("fibonacci(5) = 5", response.readEntity(String.class));
		assertEquals("5", response.getCookies().get("FibonacciIndex").getValue());
	}

	@Test
	public void testNextFibonacciWithCookieIndex5() {
		Response response = target.path("fibonacci").request().accept(MediaType.TEXT_PLAIN).cookie("FibonacciIndex", "5").post(null);

		assertEquals("fibonacci(6) = 8", response.readEntity(String.class));
		assertEquals("6", response.getCookies().get("FibonacciIndex").getValue());
	}

	@Test
	public void testDeleteFibonacciWithoutCookie() {
		Response response = target.path("fibonacci").request().accept(MediaType.TEXT_PLAIN).delete();

		assertEquals("Reset OK", response.readEntity(String.class));
		assertEquals("RESET", response.getCookies().get("FibonacciIndex").getValue());
	}

	@Test
	public void testDeleteFibonacciWithCookie() {
		Response response = target.path("fibonacci").request().accept(MediaType.TEXT_PLAIN).cookie("FibonacciIndex", "1234567890").delete();

		assertEquals("Reset OK", response.readEntity(String.class));
		assertEquals("RESET", response.getCookies().get("FibonacciIndex").getValue());
	}
}
