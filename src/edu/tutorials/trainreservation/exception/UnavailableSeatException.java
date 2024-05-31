package edu.tutorials.trainreservation.exception;

public class UnavailableSeatException extends RuntimeException {
    public UnavailableSeatException(String message) {
        super(message);
    }
}
