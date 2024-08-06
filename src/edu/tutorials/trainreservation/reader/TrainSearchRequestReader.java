package edu.tutorials.trainreservation.reader;

import edu.tutorials.trainreservation.domain.CoachType;
import edu.tutorials.trainreservation.input.TrainSearchRequest;
import edu.tutorials.trainreservation.service.TicketBookingService;
import edu.tutorials.trainreservation.utils.Utils;

import java.time.LocalDate;

import static edu.tutorials.trainreservation.utils.Constants.SPACE;

public class TrainSearchRequestReader {
    public static TrainSearchRequest read(String input) {
        String[] bookingRequest = input.split(SPACE);
        
        if(bookingRequest.length == 1){
            if(bookingRequest[0].equals("REPORT")){
                System.out.println("\nPNR, DATE, TRAIN, FROM, TO, FARE, SEATS");
                for(long i=100000001;i<100000001+TicketBookingService.report.size();i++){
                    System.out.println(TicketBookingService.report.get(Long.valueOf(i)).reportFormat());
                }
                
            }
            else{
                if(!TicketBookingService.report.containsKey(Long.valueOf(bookingRequest[0]))){
                    System.out.println("Invalid PNR !");
                    return null;
                }
                System.out.println(TicketBookingService.report.get(Long.valueOf(bookingRequest[0])).pnrFormat());
            }
            return null;
        }

        String sourceCity = bookingRequest[0];
        String destinationCity = bookingRequest[1];
        LocalDate travelDate = Utils.toLocalDate(bookingRequest[2]);
        CoachType coachType = toCoachType(bookingRequest[3]);
        int noOfPassengers = Integer.parseInt(bookingRequest[4]);

        return new TrainSearchRequest(sourceCity, destinationCity, travelDate, coachType, noOfPassengers);
    }

    private static CoachType toCoachType(String coachType) {
        switch (coachType) {
            case "SL": return CoachType.SLEEPER;
            case "3A": return CoachType.TIER_3_AC;
            case "2A": return CoachType.TIER_2_AC;
            case "1A": return CoachType.TIER_1_AC;
            default: throw new IllegalArgumentException("Invalid Coach Type: " + coachType);
        }
    }
}
