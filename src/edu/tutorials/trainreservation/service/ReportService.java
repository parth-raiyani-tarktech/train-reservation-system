package edu.tutorials.trainreservation.service;

public class ReportService {
    private long pnr;
    private double cost;
    private String from , to, date, seats, trainNo; 
    public ReportService(long pnr, String trainNo, double cost, String from, String to, String date, String seats){
        this.trainNo = trainNo;
        this.cost = cost;
        this.from = from;
        this.to = to;
        this.date = date;
        this.seats = seats;
        this.pnr = pnr;
    }
    public String pnrFormat(){
        return trainNo + " " + from + " " + to + " " + date + " " + cost + " " + seats; 
    }
    public String reportFormat(){
        return pnr + ", " + date + ", " + trainNo + ", " + from + ", " + to + ", " + cost + ", " + seats;
    }
}
