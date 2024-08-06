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

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<Train> trains = DataReader.readTrainData();
        // displayTrainDetails(trains);

        TrainService trainService = new TrainService(trains);
        TicketBookingService ticketBookingService = new TicketBookingService(trainService);

        Scanner sc = new Scanner(System.in);
        while(true) {
            System.out.print("\nInput: ");

            String input = sc.nextLine();
            if(input.equals("exit")) {
                break;
            }

            // Search Trains
            TrainSearchRequest trainSearchRequest = TrainSearchRequestReader.read(input);
            if(trainSearchRequest == null){
                continue;
            }
            // System.out.println(trainSearchRequest + "\n");

            List<Train> trainsForRoute = trainService.findTrains(trainSearchRequest);
            displayTrainNo(trainsForRoute);

            if(!trainsForRoute.isEmpty()) {
                // Select Train Number
                String trainNo = DataReader.readTrainNoToBookTicket();
                Ticket bookedTicket = ticketBookingService.bookTicket(trainNo, trainSearchRequest.getCoachType(), trainSearchRequest.getTravelDate(), trainSearchRequest.getPassengerCount());
                System.out.println("Ticket booked successfully: \n" + bookedTicket);
            }
        }
        sc.close();

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
