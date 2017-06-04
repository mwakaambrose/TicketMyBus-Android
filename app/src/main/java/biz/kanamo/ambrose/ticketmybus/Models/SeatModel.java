package biz.kanamo.ambrose.ticketmybus.Models;

/**
 * Created by ambrose on 3/20/17.
 */

public class SeatModel {
    private int seatNumber;
    private boolean isBooked;
    private boolean isSeat;

    public boolean isSeat() {
        return isSeat;
    }

    public void setSeat(boolean seat) {
        isSeat = seat;
    }

    public SeatModel(int seatNumber, boolean isBooked, boolean isSeat) {
        this.seatNumber = seatNumber;
        this.isBooked = isBooked;
        this.isSeat = isSeat;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }
    public boolean getIsBooked() {
        return isBooked;
    }

    public void setIsBooked(boolean isBooked) {
        this.isBooked = isBooked;
    }
}
