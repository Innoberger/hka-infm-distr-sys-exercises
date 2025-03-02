package tcc;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tcc.flight.FlightReservationDoc;
import tcc.flight.FlightReservationList;
import tcc.hotel.HotelReservationDoc;
import tcc.hotel.HotelReservationList;

/**
 * Simple non-transactional client. Can be used to populate the booking services
 * with some requests.
 */
public class TestClient {

	public static void main(String[] args) {
		try {
			Client client = ClientBuilder.newClient();
			WebTarget target = client.target(TestServer.BASE_URI);

			GregorianCalendar tomorrow = new GregorianCalendar();
			tomorrow.setTime(new Date());
			tomorrow.add(GregorianCalendar.DAY_OF_YEAR, 1);

			// book flight

			WebTarget webTargetFlight = target.path("flight");

			FlightReservationDoc docFlight = new FlightReservationDoc();
			docFlight.setName("Christian");
			docFlight.setFrom("Karlsruhe");
			docFlight.setTo("Berlin");
			docFlight.setAirline("airberlin");
			docFlight.setDate(tomorrow.getTimeInMillis());

			Response responseFlight = webTargetFlight.request().accept(MediaType.APPLICATION_XML)
					.post(Entity.xml(docFlight));

			if (responseFlight.getStatus() != 200) {
				System.out.println("Failed : HTTP error code : " + responseFlight.getStatus());
			}

			FlightReservationDoc outputFlight = responseFlight.readEntity(FlightReservationDoc.class);
			System.out.println("Output from Server: " + outputFlight);

			// book hotel

			WebTarget webTargetHotel = target.path("hotel");

			HotelReservationDoc docHotel = new HotelReservationDoc();
			docHotel.setName("Christian");
			docHotel.setHotel("Interconti");
			docHotel.setDate(tomorrow.getTimeInMillis());

			Response responseHotel = webTargetHotel.request().accept(MediaType.APPLICATION_XML)
					.post(Entity.xml(docHotel));

			if (responseHotel.getStatus() != 200) {
				System.out.println("Failed : HTTP error code : " + responseHotel.getStatus());
			}

			HotelReservationDoc outputHotel = responseHotel.readEntity(HotelReservationDoc.class);
			System.out.println("Output from Server: " + outputHotel);

			// confirm via transaction library
			TransactionLibrary.confirm(outputFlight, outputHotel, target);

			// print all flight and hotel reservations
			Response flightsResponse = webTargetFlight.request().accept(MediaType.APPLICATION_XML).get();
			Response hotelsResponse = webTargetHotel.request().accept(MediaType.APPLICATION_XML).get();

			//System.out.println(flightsResponse.readEntity(String.class));

			System.out.println("All flight reservations: " + flightsResponse.readEntity(FlightReservationList.class));
			System.out.println("All hotel reservations: " + hotelsResponse.readEntity(HotelReservationList.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
