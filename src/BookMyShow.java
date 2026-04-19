class Movie {
    private int id;
    private String name;
    private int duration;
}

class City {
    private String name;
}

class Theatre {
    private int id;
    private String name;
    private City city;
    private List<Screen> screens;
}

class Screen {
    private int id;
    private List<Seat> seats;
}

enum SeatType {
    REGULAR, PREMIUM
}

class Seat {
    private int seatId;
    private SeatType type;
}

import java.util.*;

class Show {
    private int showId;
    private Movie movie;
    private Screen screen;
    private Date startTime;

    private Map<Seat, Boolean> seatAvailability = new HashMap<>();

    public Show(Movie movie, Screen screen, Date time) {
        this.movie = movie;
        this.screen = screen;
        this.startTime = time;

        for (Seat seat : screen.getSeats()) {
            seatAvailability.put(seat, true); // available
        }
    }

    public synchronized boolean bookSeat(Seat seat) {
        if (seatAvailability.get(seat)) {
            seatAvailability.put(seat, false);
            return true;
        }
        return false;
    }
}

class Booking {
    private int bookingId;
    private Show show;
    private List<Seat> seats;
    private BookingStatus status;
}

enum BookingStatus {
    CREATED, CONFIRMED, FAILED
}

class Payment {
    private int paymentId;

    public boolean pay(double amount) {
        return true; // assume success
    }
}

class BookingService {

    public Booking createBooking(User user, Show show, List<Seat> seats) {

        List<Seat> bookedSeats = new ArrayList<>();

        for (Seat seat : seats) {
            if (show.bookSeat(seat)) {
                bookedSeats.add(seat);
            } else {
                System.out.println("Seat already booked");
                return null;
            }
        }

        Booking booking = new Booking();
        // set fields...

        Payment payment = new Payment();
        if (payment.pay(200)) {
            booking.setStatus(BookingStatus.CONFIRMED);
        } else {
            booking.setStatus(BookingStatus.FAILED);
        }

        return booking;
    }
}