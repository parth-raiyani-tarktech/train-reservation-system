package edu.tutorials.trainreservation.service;

import edu.tutorials.trainreservation.domain.Seat;
import edu.tutorials.trainreservation.domain.Ticket;
import edu.tutorials.trainreservation.domain.Train;
import edu.tutorials.trainreservation.input.TrainSearchRequest;
import edu.tutorials.trainreservation.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class TicketBookingService {
    private final TrainService trainService;
    private final List<Ticket> bookedTickets;
    private static long pnrCounter = 100000000;

    public TicketBookingService(TrainService trainService) {
        this.trainService = trainService;
        bookedTickets = new ArrayList<>();
    }

    public Ticket bookTicket(String trainNumber, TrainSearchRequest trainSearchRequest) {
        Train selectedTrain = trainService.getTrainByNumber(trainNumber);
        List<Seat> bookedSeats = selectedTrain.reserveSeats(trainSearchRequest.getCoachType(), trainSearchRequest.getTravelDate(), trainSearchRequest.getPassengerCount());
        double totalFare = trainSearchRequest.getCoachType().calculateFare(selectedTrain.calculateDistance(trainSearchRequest.getSourceCity(), trainSearchRequest.getDestinationCity()), trainSearchRequest.getPassengerCount());
        long pnr = generatePNR();
        Ticket ticket = new Ticket(pnr, selectedTrain.getTrainNumber(), selectedTrain.getCity(trainSearchRequest.getSourceCity()), selectedTrain.getCity(trainSearchRequest.getDestinationCity()), trainSearchRequest.getTravelDate(), totalFare, bookedSeats);
        bookedTickets.add(ticket);
        return ticket;
    }

    private long generatePNR() {
        return ++pnrCounter;
    }

    public List<Ticket> getBookedTickets() {
        return bookedTickets;
    }

    public void generateAndPrintReport() {
        System.out.println("PNR, Date, Train No, From, To, Fare, Seats");
        for (Ticket ticket : bookedTickets) {
            System.out.println(ticket.getPnr() + ", " + ticket.getTravelDate() + ", " + ticket.getTrainNumber() + ", " + ticket.getFrom().getName() + ", " + ticket.getTo().getName() + ", " + ticket.getTotalFare() + ", " + Utils.toCommaSeparatedSeatNo(ticket.getBookedSeats()));
        }
    }

    public Ticket getBookingDetails(long pnr) {
        for (Ticket ticket : bookedTickets) {
            if (ticket.getPnr() == pnr) {
                return ticket;
            }
        }
        return null;
    }
}