package tcc.flight;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "flightReservationDocs")
public
class FlightReservationList {

    List<FlightReservationDoc> reservations;

    public FlightReservationList() {
    }

    @XmlElement(name="flightReservation")
    public List<FlightReservationDoc> getReservations() {
        return reservations;
    }

    public void setReservations(List<FlightReservationDoc> reservations) {
        this.reservations = reservations;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("flight reservations [ ");

        reservations.forEach(sb::append);
        sb.append(" ]");

        return sb.toString();
    }
}
