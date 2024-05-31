package edu.tutorials.trainreservation.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Coach {
    private final CoachType coachType;
    private final String coachName;
    private final List<Seat> seats;

    public Coach(String coachName, CoachType coachType, int totalSeats) {
        this.coachName = coachName;
        this.coachType = coachType;
        seats = createSeats(totalSeats);
    }

    private List<Seat> createSeats(int totalSeats) {
        List<Seat> seats = new ArrayList<>();
        for (int i = 1; i <= totalSeats; i++) {
            String seatNumber = coachName + "-" + i;
            seats.add(new Seat(seatNumber));
        }
        return seats;
    }

    public String getCoachName() {
        return coachName;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public CoachType getCoachType() {
        return coachType;
    }

    public List<Seat> getAvailableSeats(LocalDate travelDate) {
        List<Seat> availableSeats = new ArrayList<>();
        for (Seat seat : seats) {
            if (seat.isAvailableFor(travelDate)) {
                availableSeats.add(seat);
            }
        }
        return availableSeats;
    }
}
