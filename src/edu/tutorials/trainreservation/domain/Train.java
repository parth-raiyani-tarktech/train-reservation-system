package edu.tutorials.trainreservation.domain;

import edu.tutorials.trainreservation.exception.UnavailableSeatException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Train {
    private final String trainNo;
    private final List<City> locations;
    private final List<Coach> coaches;
    private City tempSource, tempDest;

    public Train(String trainNo, List<City> locations, List<Coach> coaches) {
        this.trainNo = trainNo;
        this.locations = locations;
        this.coaches = coaches;
    }

    public String getTrainNumber() {
        return trainNo;
    }

    public List<Coach> getCoaches() {
        return coaches;
    }

    public boolean hasRoute(String sourceCity, String destinationCity) {
        int sourceOrDestination = 0; //source
        for(int i=0;i<locations.size();i++){
            if(sourceOrDestination == 0 && locations.get(i).getName().equals(sourceCity)){
                tempSource = new City(sourceCity, locations.get(i).getDistance());
                sourceOrDestination++;
            }
            else if( sourceOrDestination == 1 && locations.get(i).getName().equals(destinationCity)){
                tempDest = new City(destinationCity, locations.get(i).getDistance());
                return true;
            }
        }
        return false;
    }

    public City getSource() {
        return tempSource;
    }

    public City getDestination() {
        return tempDest;
    }

    public boolean hasCoachType(CoachType coachType) {
        for (Coach coach : coaches) {
            if (coach.getCoachType().equals(coachType)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasAvailableSeats(CoachType coachType, LocalDate travelDate, int passengerCount) {
        List<Seat> availableSeats = getAvailableSeats(coachType, travelDate);

        return availableSeats.size() >= passengerCount;
    }

    public List<Seat> reserveSeats(CoachType coachType, LocalDate travelDate, int passengerCount) {
        if(!hasAvailableSeats(coachType, travelDate, passengerCount)) {
            throw new UnavailableSeatException("No available seats for date " + travelDate + " in coach type " + coachType + " for " + passengerCount + " passengers");
        }

        List<Seat> availableSeats = getAvailableSeats(coachType, travelDate);

        List<Seat> reservedSeats = new ArrayList<>();
        for (int i = 0; i < passengerCount; i++) {

            Seat seat = availableSeats.get(i);
            seat.reserveSeat(travelDate);

            reservedSeats.add(seat);
        }

        return reservedSeats;
    }

    private List<Seat> getAvailableSeats(CoachType coachType, LocalDate travelDate) {
        List<Seat> availableSeats = new ArrayList<>();

        for (Coach coach : coaches) {
            if (coach.getCoachType().equals(coachType)) {
                availableSeats.addAll(coach.getAvailableSeats(travelDate));
            }
        }
        return availableSeats;
    }

    public int getTotalDistance(City sourceCity, City destinationCity) {
        int sourceOrDestination = 0; //source
        int dist = 0;
        for(int i=0;i<locations.size();i++){
            if(sourceOrDestination == 0 && locations.get(i).getName().equals(sourceCity.getName())){
                sourceOrDestination++;
                dist = locations.get(i).getDistance();
            }
            else if( sourceOrDestination == 1 && locations.get(i).getName().equals(destinationCity.getName())){
                return locations.get(i).getDistance()-dist;
            }
        }
        return 0;
    }
}
