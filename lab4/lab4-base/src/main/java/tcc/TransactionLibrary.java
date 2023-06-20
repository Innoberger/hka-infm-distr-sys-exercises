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

    public static void confirm(FlightReservationDoc flightReservationTry, HotelReservationDoc hotelReservationTry, WebTarget baseTarget) throws MalformedURLException, BookingException {
        WebTarget flightTarget = getWebTarget(flightReservationTry.getUrl(), baseTarget);
        WebTarget hotelTarget = getWebTarget(hotelReservationTry.getUrl(), baseTarget);

        try {
            Response responseFlight = flightTarget.request().accept(MediaType.TEXT_PLAIN).put(Entity.xml(flightReservationTry));

            if (responseFlight.getStatus() != 200) {
                System.out.println("Failed : HTTP error code : " + responseFlight.getStatus());
                throw new BookingException(BookingException.BookingError.COULD_NOT_CONFIRM);
            }

            Response responseHotel = hotelTarget.request().accept(MediaType.TEXT_PLAIN).put(Entity.xml(hotelReservationTry));

            if (responseHotel.getStatus() != 200) {
                System.out.println("Failed : HTTP error code : " + responseHotel.getStatus());
                throw new BookingException(BookingException.BookingError.COULD_NOT_CONFIRM);
            }
        } catch (BookingException e) {
            // do rollback on exception;
            flightTarget.request().accept(MediaType.APPLICATION_XML).delete();
            hotelTarget.request().accept(MediaType.APPLICATION_XML).delete();
            throw new BookingException(BookingException.BookingError.COULD_NOT_CONFIRM);
        }
    }

    private static WebTarget getWebTarget(String url, WebTarget baseTarget) throws MalformedURLException {
        return baseTarget.path(new URL(url).getPath().replaceFirst("/", ""));
    }

}
