package edu.tutorials.trainreservation.domain;

import edu.tutorials.trainreservation.exception.UnavailableSeatException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Train {
    private final String trainNo;
    private final List<City> cities;
    private final List<Coach> coaches;

    public Train(String trainNo, List<City> cities, List<Coach> coaches) {
        this.trainNo = trainNo;
        this.cities = cities;
        this.coaches = coaches;
    }

    public String getTrainNumber() {
        return trainNo;
    }

    public List<Coach> getCoaches() {
        return coaches;
    }

    public boolean hasRoute(String sourceCity, String destinationCity) {
        int sourceIndex = -1;
        int destinationIndex = -1;
        for (int i = 0; i < cities.size(); i++) {
            City city = cities.get(i);
            if (sourceIndex == -1 && city.getName().equals(sourceCity)) {
                sourceIndex = i;
            }
            if (destinationIndex == -1 && city.getName().equals(destinationCity)) {
                destinationIndex = i;
            }
            if (sourceIndex != -1 && destinationIndex != -1) {
                break;
            }
        }
        return sourceIndex != -1 && destinationIndex != -1 && sourceIndex < destinationIndex;
    }

    public City getSource() {
        return cities.get(0);
    }

    public City getDestination() {
        return cities.get(cities.size() - 1);
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

    public int calculateDistance(String sourceCity, String destinationCity) {
        int sourceDistance = getCity(sourceCity).getDistance();
        int destinationDistance = getCity(destinationCity).getDistance();
        return destinationDistance - sourceDistance;
    }

    public City getCity(String cityName) {
        for (City city : cities) {
            if (city.getName().equals(cityName)) {
                return city;
            }
        }
        throw new IllegalArgumentException("City not found: " + cityName);
    }
}