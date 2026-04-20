import java.util.*;

class User {
    private int id;
    private String name;

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

class Movie {
    private int id;
    private String name;
    private int duration;

    public Movie(int id, String name, int duration) {
        this.id = id;
        this.name = name;
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }
}

class City {
    private String name;

    public City(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class Theatre {
    private int id;
    private String name;
    private City city;
    private List<Screen> screens;

    public Theatre(int id, String name, City city, List<Screen> screens) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.screens = screens;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public City getCity() {
        return city;
    }

    public List<Screen> getScreens() {
        return screens;
    }
}

class Screen {
    private int id;
    private List<Seat> seats;

    public Screen(int id, List<Seat> seats) {
        this.id = id;
        this.seats = seats;
    }

    public int getId() {
        return id;
    }

    public List<Seat> getSeats() {
        return seats;
    }
}

enum SeatType {
    REGULAR, PREMIUM
}

class Seat {
    private int seatId;
    private SeatType type;

    public Seat(int seatId, SeatType type) {
        this.seatId = seatId;
        this.type = type;
    }

    public int getSeatId() {
        return seatId;
    }

    public SeatType getType() {
        return type;
    }
}

class Show {
    private int showId;
    private Movie movie;
    private Screen screen;
    private Date startTime;

    private Map<Seat, Boolean> seatAvailability = new HashMap<>();

    public Show(int showId, Movie movie, Screen screen, Date time) {
        this.showId = showId;
        this.movie = movie;
        this.screen = screen;
        this.startTime = time;

        for (Seat seat : screen.getSeats()) {
            seatAvailability.put(seat, true); // available
        }
    }

    public int getShowId() {
        return showId;
    }

    public Movie getMovie() {
        return movie;
    }

    public Screen getScreen() {
        return screen;
    }

    public Date getStartTime() {
        return startTime;
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
    private User user;
    private Show show;
    private List<Seat> seats;
    private BookingStatus status;

    public Booking(int bookingId, User user, Show show, List<Seat> seats) {
        this.bookingId = bookingId;
        this.user = user;
        this.show = show;
        this.seats = seats;
        this.status = BookingStatus.CREATED;
    }

    public int getBookingId() {
        return bookingId;
    }

    public User getUser() {
        return user;
    }

    public Show getShow() {
        return show;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }
}

enum BookingStatus {
    CREATED, CONFIRMED, FAILED
}

class Payment {
    private int paymentId;

    public Payment(int paymentId) {
        this.paymentId = paymentId;
    }

    public int getPaymentId() {
        return paymentId;
    }

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

        Booking booking = new Booking(123, user, show, bookedSeats);

        Payment payment = new Payment(456);
        if (payment.pay(200)) {
            booking.setStatus(BookingStatus.CONFIRMED);
        } else {
            booking.setStatus(BookingStatus.FAILED);
        }

        return booking;
    }
}

public class BookMyShow {
    public static void main(String[] args) {

        // Create movie
        Movie movie = new Movie(1, "Avengers", 180);

        // Create seats
        List<Seat> seats = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            seats.add(new Seat(i, SeatType.REGULAR));
        }

        // Create screen
        Screen screen = new Screen(1, seats);

        // Create user
        User user = new User(1, "Karthik");

        // Create show
        Show show = new Show(1, movie, screen, new Date());

        // Select seats
        List<Seat> selectedSeats = Arrays.asList(seats.get(0), seats.get(1));

        // Booking service
        BookingService bookingService = new BookingService();

        Booking booking = bookingService.createBooking(user, show, selectedSeats);

        if (booking != null) {
            System.out.println("Booking Successful!");
        } else {
            System.out.println("Booking Failed!");
        }
    }
}