package tcc.hotel;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "hotelReservationDocs")
public
class HotelReservationList {

    List<HotelReservationDoc> reservations;

    public HotelReservationList() {
    }

    @XmlElement(name="hotelReservation")
    public List<HotelReservationDoc> getReservations() {
        return reservations;
    }

    public void setReservations(List<HotelReservationDoc> reservations) {
        this.reservations = reservations;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("hotel reservations [ ");

        reservations.forEach(sb::append);
        sb.append(" ]");

        return sb.toString();
    }
}
