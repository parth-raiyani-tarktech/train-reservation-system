package edu.tutorials.trainreservation.service;

import edu.tutorials.trainreservation.domain.CoachType;
import edu.tutorials.trainreservation.domain.Seat;
import edu.tutorials.trainreservation.domain.Ticket;
import edu.tutorials.trainreservation.domain.Train;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TicketBookingService {
    private final TrainService trainService;
    private final List<Ticket> bookedTickets;

    public TicketBookingService(TrainService trainService) {
        this.trainService = trainService;
        bookedTickets = new ArrayList<>();
    }

    public Ticket bookTicket(String trainNumber, CoachType coachType, LocalDate travelDate, int passengerCount) {
        Train selectedTrain = trainService.getTrainByNumber(trainNumber);

        List<Seat> bookedSeats = selectedTrain.reserveSeats(coachType, travelDate, passengerCount);
        double totalFare = coachType.calculateFare(selectedTrain.getTotalDistance(), passengerCount);

        long pnr = bookedTickets.size() + 1;

        Ticket ticket = new Ticket(pnr, selectedTrain.getTrainNumber(), selectedTrain.getSource(), selectedTrain.getDestination(), travelDate, totalFare, bookedSeats);
        bookedTickets.add(ticket);

        return ticket;
    }

    public List<Ticket> getBookedTickets() {
        return bookedTickets;
    }
}