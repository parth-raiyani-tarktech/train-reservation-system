package edu.tutorials.trainreservation.reader;

import edu.tutorials.trainreservation.domain.CoachType;
import edu.tutorials.trainreservation.input.TrainSearchRequest;
import edu.tutorials.trainreservation.utils.Utils;

import java.time.LocalDate;

import static edu.tutorials.trainreservation.utils.Constants.SPACE;

public class TrainSearchRequestReader {
    public static TrainSearchRequest read(String input) {
        String[] bookingRequest = input.split(SPACE);

        String sourceCity = bookingRequest[0];
        String destinationCity = bookingRequest[1];
        LocalDate travelDate = Utils.toLocalDate(bookingRequest[2]);
        CoachType coachType = toCoachType(bookingRequest[3]);
        int noOfPassengers = Integer.parseInt(bookingRequest[4]);

        return new TrainSearchRequest(sourceCity, destinationCity, travelDate, coachType, noOfPassengers);
    }

    private static CoachType toCoachType(String coachType) {
        return switch (coachType) {
            case "SL" -> CoachType.SLEEPER;
            case "3A" -> CoachType.TIER_3_AC;
            case "2A" -> CoachType.TIER_2_AC;
            case "1A" -> CoachType.TIER_1_AC;
            default -> throw new IllegalArgumentException("Invalid Coach Type: " + coachType);
        };
    }
}