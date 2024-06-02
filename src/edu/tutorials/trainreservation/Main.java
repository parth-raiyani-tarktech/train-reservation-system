package edu.tutorials.trainreservation;

import edu.tutorials.trainreservation.domain.Coach;
import edu.tutorials.trainreservation.domain.Seat;
import edu.tutorials.trainreservation.domain.Ticket;
import edu.tutorials.trainreservation.domain.Train;
import edu.tutorials.trainreservation.input.TrainSearchRequest;
import edu.tutorials.trainreservation.reader.DataReader;
import edu.tutorials.trainreservation.reader.TrainSearchRequestReader;
import edu.tutorials.trainreservation.service.TicketBookingService;
import edu.tutorials.trainreservation.service.TrainService;
import edu.tutorials.trainreservation.utils.Utils;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<Train> trains = DataReader.readTrainData();
        displayTrainDetails(trains);

        TrainService trainService = new TrainService(trains);
        TicketBookingService ticketBookingService = new TicketBookingService(trainService);

        Scanner sc = new Scanner(System.in);
        while(true) {
            System.out.println("\nEnter train search request, PNR number or 'REPORT' (type 'exit' to quit)");

            String input = sc.nextLine();
            if(input.equals("exit")) {
                break;
            }

            if(input.matches("\\d+")) { // if input is a PNR number
                long pnr = Long.parseLong(input);
                Ticket ticket = ticketBookingService.getBookingDetails(pnr);
                if(ticket != null) {
                    System.out.println(ticket.getTrainNumber() + " " + ticket.getFrom() + " " + ticket.getTo() + " " + ticket.getTravelDate() + " " + ticket.getTotalFare() + " " + Utils.toCommaSeparatedSeatNo(ticket.getBookedSeats()));
                } else {
                    System.out.println("Invalid PNR");
                }
            } else if(input.equalsIgnoreCase("REPORT")) {
                ticketBookingService.generateAndPrintReport();
            } else { // if input is a train search request
                TrainSearchRequest trainSearchRequest = TrainSearchRequestReader.read(input);
                System.out.println(trainSearchRequest + "\n");

                List<Train> trainsForRoute = trainService.findTrains(trainSearchRequest);
                displayTrainNo(trainsForRoute);

                if(!trainsForRoute.isEmpty()) {
                    String trainNo = DataReader.readTrainNoToBookTicket();
                    Ticket bookedTicket = ticketBookingService.bookTicket(trainNo, trainSearchRequest.getCoachType(), trainSearchRequest.getTravelDate(), trainSearchRequest.getPassengerCount(), trainSearchRequest.getSourceCity(), trainSearchRequest.getDestinationCity());
                    System.out.println(bookedTicket.getPnr() + " " + bookedTicket.getTotalFare() + " " + Utils.toCommaSeparatedSeatNo(bookedTicket.getBookedSeats()));
                }
            }
        }

        displayTickets(ticketBookingService.getBookedTickets());
    }

    private static void displayTickets(List<Ticket> bookedTickets) {
        System.out.println("Booked Tickets: ");
        for(Ticket ticket : bookedTickets) {
            System.out.println(ticket);
        }
    }

    private static void displayTrainNo(List<Train> trains) {
        System.out.println("Found " + trains.size() + " trains");

        for(Train train : trains) {
            System.out.println("Train: " + train.getTrainNumber());
        }
    }

    private static void displayTrainDetails(List<Train> trains) {
        for(Train train : trains) {
            System.out.println("Train: " + train.getTrainNumber());

            for(Coach coach : train.getCoaches()) {
                System.out.println("Coach: " + coach.getCoachName() + " Type: " + coach.getCoachType());

                for (Seat seat : coach.getSeats()) {
                    System.out.print(seat + " ");
                }
                System.out.print("\n\n");
            }
        }
        System.out.println();
    }
}
