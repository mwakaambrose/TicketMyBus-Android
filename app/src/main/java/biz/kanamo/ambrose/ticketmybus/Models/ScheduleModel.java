package biz.kanamo.ambrose.ticketmybus.Models;

/**
 * Created by ambrose on 3/17/17.
 */

public class ScheduleModel {
    private String numPlate;
    private String departureTime;
    private String fromTo;
    private String totalPassengers;
    private String remainingSeats;
    private String price;

    public ScheduleModel(String numPlate, String departureTime, String fromTo, String totalPassengers, String remainingSeats, String price) {
        this.numPlate = numPlate;
        this.departureTime = departureTime;
        this.fromTo = fromTo;
        this.totalPassengers = totalPassengers;
        this.remainingSeats = remainingSeats;
        this.price = price;
    }

    public String getNumPlate() {
        return numPlate;
    }

    public void setNumPlate(String numPlate) {
        this.numPlate = numPlate;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getFromTo() {
        return fromTo;
    }

    public void setFromTo(String fromTo) {
        this.fromTo = fromTo;
    }

    public String getTotalPassengers() {
        return totalPassengers;
    }

    public void setTotalPassengers(String totalPassengers) {
        this.totalPassengers = totalPassengers;
    }

    public String getRemainingSeats() {
        return remainingSeats;
    }

    public void setRemainingSeats(String remainingSeats) {
        this.remainingSeats = remainingSeats;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}