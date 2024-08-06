package edu.tutorials.trainreservation.utils;

import edu.tutorials.trainreservation.domain.Seat;

import java.time.LocalDate;
import java.util.List;

public class Utils {
    public static LocalDate toLocalDate(String str) {
        return LocalDate.parse(str);
    }

    public static String toCommaSeparatedSeatNo(List<Seat> bookedSeats) {
        StringBuilder result = new StringBuilder();
        for (Seat seat : bookedSeats) {
            if (result.length() == 0) {
                result.append(seat);
            } else {
                result.append(", ").append(seat);
            }
        }

        return result.toString();
    }
}
