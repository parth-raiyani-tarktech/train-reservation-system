package edu.tutorials.trainreservation.domain;

import edu.tutorials.trainreservation.utils.Utils;

import java.time.LocalDate;
import java.util.List;

public class Ticket {
    private final long pnr;
    private final String trainNumber;
    private final City from;
    private final City to;
    private final LocalDate travelDate;
    private final double totalFare;
    private final List<Seat> bookedSeats;

    public Ticket(long pnr, String trainNumber, City from, City to, LocalDate travelDate, double totalFare, List<Seat> bookedSeats) {
        this.pnr = pnr;
        this.trainNumber = trainNumber;
        this.from = from;
        this.to = to;
        this.travelDate = travelDate;
        this.totalFare = totalFare;
        this.bookedSeats = bookedSeats;
    }

    public String toString() {
        return String.format(String.format("PNR: %d, Train: %s, Travel Date: %s, Total Fare: %.2f, Booked Seats: %s", pnr, trainNumber, travelDate.toString(), totalFare, Utils.toCommaSeparatedSeatNo(bookedSeats)));
    }

}
