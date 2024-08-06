package edu.tutorials.trainreservation.service;

import edu.tutorials.trainreservation.domain.CoachType;
import edu.tutorials.trainreservation.domain.Seat;
import edu.tutorials.trainreservation.domain.Ticket;
import edu.tutorials.trainreservation.domain.Train;
import edu.tutorials.trainreservation.utils.Utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class TicketBookingService {
    private final TrainService trainService;
    private final List<Ticket> bookedTickets;
    public static HashMap<Long, ReportService> report; 
    public TicketBookingService(TrainService trainService) {
        this.trainService = trainService;
        bookedTickets = new ArrayList<>();
        report = new HashMap<>();
    }

    public Ticket bookTicket(String trainNumber, CoachType coachType, LocalDate travelDate, int passengerCount) {
        Train selectedTrain = trainService.getTrainByNumber(trainNumber);

        List<Seat> bookedSeats = selectedTrain.reserveSeats(coachType, travelDate, passengerCount);
        double totalFare = coachType.calculateFare(selectedTrain.getTotalDistance(selectedTrain.getSource(), selectedTrain.getDestination()), passengerCount);

        long pnr = bookedTickets.size() + 100000001;

        Ticket ticket = new Ticket(pnr, selectedTrain.getTrainNumber(), selectedTrain.getSource(), selectedTrain.getDestination(), travelDate, totalFare, bookedSeats);
        bookedTickets.add(ticket);
        
        report.put(pnr, new ReportService(pnr, selectedTrain.getTrainNumber(), totalFare, selectedTrain.getSource().getName(), selectedTrain.getDestination().getName(), travelDate.toString(), Utils.toCommaSeparatedSeatNo(bookedSeats)));

        return ticket;
    }

    public List<Ticket> getBookedTickets() {
        return bookedTickets;
    }
}