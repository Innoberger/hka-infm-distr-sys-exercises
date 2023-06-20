package tcc;

import tcc.flight.FlightReservationDoc;
import tcc.hotel.HotelReservationDoc;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.MalformedURLException;
import java.net.URL;

public class TransactionLibrary {

    public static void confirm(FlightReservationDoc flightReservationTry, HotelReservationDoc hotelReservationTry, WebTarget baseTarget) throws MalformedURLException {
        WebTarget flightTarget = getWebTarget(flightReservationTry.getUrl(), baseTarget);
        WebTarget hotelTarget = getWebTarget(hotelReservationTry.getUrl() + "2", baseTarget);

        try {
            Response responseFlight = flightTarget.request().accept(MediaType.TEXT_PLAIN).put(Entity.xml(flightReservationTry));

            if (responseFlight.getStatus() != 200) {
                System.out.println("Failed to confirm flight : HTTP " + responseFlight.getStatus());
                throw new BookingException(BookingException.BookingError.COULD_NOT_CONFIRM);
            }

            System.out.println("Successfully confirmed flight");

            Response responseHotel = hotelTarget.request().accept(MediaType.TEXT_PLAIN).put(Entity.xml(hotelReservationTry));

            if (responseHotel.getStatus() != 200) {
                System.out.println("Failed to confirm hotel : HTTP " + responseHotel.getStatus());
                throw new BookingException(BookingException.BookingError.COULD_NOT_CONFIRM);
            }

            System.out.println("Successfully confirmed hotel");
            System.out.println("Entire booking transaction confirmed");
        } catch (BookingException e) {
            // do rollback on exception;
            System.out.println("Rolling back changes...");

            Response flightRollback = flightTarget.request().accept(MediaType.APPLICATION_XML).delete();
            System.out.println("Rollback of flight : HTTP " + flightRollback.getStatus());

            Response hotelRollback = hotelTarget.request().accept(MediaType.APPLICATION_XML).delete();
            System.out.println("Rollback of hotel : HTTP " + hotelRollback.getStatus());

            System.out.println("Entire booking was rolled back");
        }
    }

    private static WebTarget getWebTarget(String url, WebTarget baseTarget) throws MalformedURLException {
        return baseTarget.path(new URL(url).getPath().replaceFirst("/", ""));
    }

}
