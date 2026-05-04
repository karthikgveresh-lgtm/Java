import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Low-Level Design for a Movie Booking System (e.g., BookMyShow)
 * 
 * Key Design Principles:
 * 1. SOLID Principles: Each class has a single responsibility.
 * 2. Thread Safety: Using synchronized blocks and ConcurrentHashMap for seat booking.
 * 3. Separation of Concerns: Controllers handle business logic, Entities hold data.
 */

// --- Enums ---

enum City {
    DELHI, MUMBAI, BANGALORE, HYDERABAD
}

enum SeatCategory {
    SILVER, GOLD, PLATINUM
}

enum BookingStatus {
    PENDING, CONFIRMED, CANCELLED
}

// --- Entities ---

class Movie {
    int id;
    String title;
    int durationInMins;

    public Movie(int id, String title, int duration) {
        this.id = id;
        this.title = title;
        this.durationInMins = duration;
    }

    @Override
    public String toString() { return title; }
}

class Seat {
    int seatId;
    int row;
    SeatCategory category;
    double price;

    public Seat(int id, int row, SeatCategory category, double price) {
        this.seatId = id;
        this.row = row;
        this.category = category;
        this.price = price;
    }
}

class Screen {
    int id;
    List<Seat> seats;

    public Screen(int id, List<Seat> seats) {
        this.id = id;
        this.seats = seats;
    }
}

class Show {
    int id;
    Movie movie;
    Screen screen;
    long startTime;
    Map<Integer, Boolean> seatAvailability; // seatId -> isAvailable

    public Show(int id, Movie movie, Screen screen, long startTime) {
        this.id = id;
        this.movie = movie;
        this.screen = screen;
        this.startTime = startTime;
        this.seatAvailability = new ConcurrentHashMap<>();
        // Initialize all seats as available
        for (Seat seat : screen.seats) {
            seatAvailability.put(seat.seatId, true);
        }
    }

    // Thread-safe booking
    public synchronized boolean bookSeats(List<Integer> seatIds) {
        // Check if all requested seats are available
        for (int seatId : seatIds) {
            if (!seatAvailability.getOrDefault(seatId, false)) {
                return false;
            }
        }
        // Mark as booked
        for (int seatId : seatIds) {
            seatAvailability.put(seatId, false);
        }
        return true;
    }
}

class Theatre {
    int id;
    String name;
    City city;
    List<Screen> screens;
    List<Show> shows;

    public Theatre(int id, String name, City city) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.screens = new ArrayList<>();
        this.shows = new ArrayList<>();
    }
}

class User {
    int id;
    String name;
    String email;

    public User(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}

class Booking {
    String id;
    User user;
    Show show;
    List<Seat> bookedSeats;
    double totalAmount;
    BookingStatus status;

    public Booking(User user, Show show, List<Seat> seats) {
        this.id = UUID.randomUUID().toString();
        this.user = user;
        this.show = show;
        this.bookedSeats = seats;
        this.totalAmount = seats.stream().mapToDouble(s -> s.price).sum();
        this.status = BookingStatus.CONFIRMED;
    }

    public void display() {
        System.out.println("\n--- Booking Confirmation ---");
        System.out.println("Booking ID: " + id);
        System.out.println("User: " + user.name);
        System.out.println("Movie: " + show.movie.title);
        System.out.println("Seats: " + bookedSeats.size() + " (" + bookedSeats.stream().map(s -> String.valueOf(s.seatId)).reduce((a,b) -> a + "," + b).get() + ")");
        System.out.println("Total Paid: ₹" + totalAmount);
        System.out.println("Status: " + status);
    }
}

// --- Controllers ---

class MovieController {
    Map<City, List<Movie>> cityMovies;
    List<Movie> allMovies;

    public MovieController() {
        cityMovies = new HashMap<>();
        allMovies = new ArrayList<>();
    }

    public void addMovie(Movie movie, City city) {
        allMovies.add(movie);
        cityMovies.computeIfAbsent(city, k -> new ArrayList<>()).add(movie);
    }

    public List<Movie> getMoviesByCity(City city) {
        return cityMovies.getOrDefault(city, new ArrayList<>());
    }
}

class TheatreController {
    Map<City, List<Theatre>> cityTheatres;

    public TheatreController() {
        cityTheatres = new HashMap<>();
    }

    public void addTheatre(Theatre theatre, City city) {
        cityTheatres.computeIfAbsent(city, k -> new ArrayList<>()).add(theatre);
    }

    public Map<Theatre, List<Show>> getAllShows(Movie movie, City city) {
        Map<Theatre, List<Show>> theatreShows = new HashMap<>();
        List<Theatre> theatres = cityTheatres.getOrDefault(city, new ArrayList<>());

        for (Theatre theatre : theatres) {
            List<Show> movieShows = new ArrayList<>();
            for (Show show : theatre.shows) {
                if (show.movie.id == movie.id) {
                    movieShows.add(show);
                }
            }
            if (!movieShows.isEmpty()) {
                theatreShows.put(theatre, movieShows);
            }
        }
        return theatreShows;
    }
}

// --- Main System Driver ---

public class MovieBookingSystem {
    MovieController movieController;
    TheatreController theatreController;

    public MovieBookingSystem() {
        movieController = new MovieController();
        theatreController = new TheatreController();
    }

    public static void main(String[] args) {
        MovieBookingSystem system = new MovieBookingSystem();
        system.initialize();

        // --- Simulated User Flow ---
        
        // 1. User selects a city
        City userCity = City.BANGALORE;
        System.out.println("Step 1: Searching movies in " + userCity);
        List<Movie> movies = system.movieController.getMoviesByCity(userCity);
        if (movies.isEmpty()) return;
        Movie selectedMovie = movies.get(0);
        System.out.println("User selected: " + selectedMovie.title);

        // 2. User searches for shows for this movie in their city
        System.out.println("\nStep 2: Finding theatres and shows for " + selectedMovie.title);
        Map<Theatre, List<Show>> showMap = system.theatreController.getAllShows(selectedMovie, userCity);
        
        // Pick first theatre and its first show
        Theatre selectedTheatre = showMap.keySet().iterator().next();
        Show selectedShow = showMap.get(selectedTheatre).get(0);
        System.out.println("Selected Theatre: " + selectedTheatre.name + " | Show Time: " + selectedShow.startTime);

        // 3. User selects seats
        System.out.println("\nStep 3: Selecting seats...");
        List<Integer> seatIdsToBook = Arrays.asList(1, 2); // Seat IDs
        
        // 4. Booking Logic
        User user = new User(1, "Karthik", "karthik@example.com");
        
        if (selectedShow.bookSeats(seatIdsToBook)) {
            List<Seat> bookedSeats = new ArrayList<>();
            for(int id : seatIdsToBook) {
                bookedSeats.add(selectedShow.screen.seats.stream().filter(s -> s.seatId == id).findFirst().get());
            }
            Booking booking = new Booking(user, selectedShow, bookedSeats);
            booking.display();
        } else {
            System.out.println("Booking Failed! Seats already taken.");
        }
    }

    private void initialize() {
        // Create Movies
        Movie movie1 = new Movie(1, "Interstellar", 169);
        Movie movie2 = new Movie(2, "Inception", 148);

        movieController.addMovie(movie1, City.BANGALORE);
        movieController.addMovie(movie2, City.BANGALORE);
        movieController.addMovie(movie1, City.DELHI);

        // Create Seats
        List<Seat> seats = new ArrayList<>();
        for (int i = 1; i <= 50; i++) {
            SeatCategory cat = (i <= 10) ? SeatCategory.PLATINUM : (i <= 30) ? SeatCategory.GOLD : SeatCategory.SILVER;
            double price = (cat == SeatCategory.PLATINUM) ? 500 : (cat == SeatCategory.GOLD) ? 300 : 200;
            seats.add(new Seat(i, i/10, cat, price));
        }

        // Create Screens
        Screen screen1 = new Screen(1, seats);

        // Create Theatre
        Theatre theatre1 = new Theatre(1, "PVR Cinemas", City.BANGALORE);
        theatre1.screens.add(screen1);

        // Create Shows
        Show morningShow = new Show(1, movie1, screen1, 1000L);
        Show eveningShow = new Show(2, movie1, screen1, 1800L);
        theatre1.shows.add(morningShow);
        theatre1.shows.add(eveningShow);

        theatreController.addTheatre(theatre1, City.BANGALORE);
    }
}
