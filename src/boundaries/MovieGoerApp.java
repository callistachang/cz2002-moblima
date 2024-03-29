package boundaries;
import java.util.Scanner;
import models.Account;
import models.Booking;
import models.Cinema;
import models.Cineplex;
import models.Movie;
import models.Review;
import models.Showtime;

import java.util.ArrayList;

import managers.*;

/**
 * Subclass of UserApp. Allows access to function for customers with an account.
 * Accessed via user-specific username and password.
 * @author balad
 * @version 1.0
 * @since 2019-11-17
 */
public class MovieGoerApp extends UserApp {
	/**
	 * Account of the user who accesses the menu.
	 */
	private Account account;
	/**
	 * An array of strings with the options for menu choices.
	 */
	private final String menuOptions[] = {
		"Book and purchase a movie ticket",
		"Leave a rating and review on a movie",
		"View booking history"
	};

	/**
	 * Creates a new account object.
	 * @param account This user's account.
	 */
	public MovieGoerApp(Account account) {
		this.account = account;
	}

	/**
	 * Runs the main logic of this app.
	 * Displays menu options for the users.
	 * (1) Book movie tickets.
	 * (2) Give rating for a movie.
	 * (3) View past bookings.
	 * (4)-(7) Extended from UserApp.
	 * (8) Exits from user menu and MovieGoerApp.
	 */
	public void run() {
		int choice = -1;
		Scanner sc = new Scanner(System.in);

		System.out.println("\nWelcome to MOBLIMA, " + account.getUsername() + "!");

		do {
			System.out.println("=================== Movie-Goer Menu ===================");
			int i;
			for (i = 1; i <= menuOptions.length; i++) {
				System.out.printf("(%d) %s \n", i, menuOptions[i-1]);
			}
			System.out.println("======================================================");
			String[] guestOptions = super.getMenuOptions();
			for (int j = 1; j <= guestOptions.length; i++, j++) {
				System.out.printf("(%d) %s \n", i, guestOptions[j-1]);
			}
			System.out.printf("(%d) %s \n", i, "Exit");
			System.out.println("======================================================");
			choice = InputBoundary.getIntInput("Choose an option");
			switch (choice) {
				case 1:
					bookTicket();
					break;
				case 2:
					rateMovie();
					break;
				case 3:
					viewBookingHistory();
					break;
				case 4:
					listMovies();
					break;
				case 5:
					viewMovieDetails();
					break;
				case 6:
					checkSeatAvailabilities();
					break;
				case 7:
					listTop5Movies();
					break;
				case 8:
					System.out.println("Exited from the movie-goer menu.");
					break;
				default:
					System.out.println("Please input a valid option.");
					break;
				}
		} while (choice != 8);
	}
	/**
	 * Main function to book movie tickets.
	 * (1) Lists all movies recorded in the database.
	 * (2) Takes in user choice of movie, cineplex and showtime.
	 * (3) Takes in the number of tickets to be purchased and user's seat selection.
	 * (4) Calculates the total price for a single booking transaction.
	 */

		private void bookTicket() {
		Scanner sc= new Scanner(System.in);

		MovieManager mm = new MovieManager();
		CineplexManager cxm = new CineplexManager();
		ShowtimeManager sm = new ShowtimeManager();
		CinemaManager cm = new CinemaManager();
		BookingManager bm  = new BookingManager();
		PricingCalculator pm = new PricingCalculator();

		System.out.println("Which movie would you like to watch?");
		mm.listAll();
		int movieID = InputBoundary.getIntInput("Choose an option");

		System.out.println("Which cineplex would you like to watch the movie from?");
		boolean cineplexExists = cxm.listCineplexByMovie(movieID);
		if (!cineplexExists) {
			System.out.println("No cineplex is currently playing the movie.");
			bookTicket();
		}
		else {
		String cineplexID = InputBoundary.getCineplexIDInput("Choose a cineplex:");
		Movie movie = mm.getMovieByID(movieID);
		Cineplex cineplex = cxm.getCineplexByID(cineplexID);

		System.out.println("Which showtime would you like to watch?");
		cxm.listAllSeatAvailabilitiesInCineplexByMovie(cineplex, movie);
		int showtimeID = InputBoundary.getIntInput("Choose an option");
		Cinema cinema = cm.getCinemaByShowtimeID(showtimeID);
		int noOfTickets;
		Showtime showtime = sm.getShowtimeByID(showtimeID);
		Cinema c = cm.getCinemaByShowtimeID(showtimeID);
		do {
		noOfTickets= InputBoundary.getIntInput("How many tickets would you like to purchase?");
		} while (noOfTickets > c.getNoSeatsAvailable(showtime));

		c.printSeatingPlan(showtime);
		double price = 0;
		for(int i=0;i<noOfTickets;i++)
		{
			int seatChosen = InputBoundary.getSeatNumberInput(cinema, showtime, "Please select your seat number");
			sm.bookSeat(showtime,seatChosen);
			System.out.println("Any discounts applicable?");
			System.out.println("(0) None");
			System.out.println("(1) Student");
			System.out.println("(2) Senior Citizen");
			int discountType = InputBoundary.getIntInput("Choose an option: ");
			price+=pm.calculatePrice(discountType, c.getId(), showtimeID);
		}


		System.out.println("The total price is: " + price);
		
		
		Booking booking = bm.addReceipt(c.getId(),showtimeID, price);
		AccountManager am = new AccountManager();
		am.addBooking(account, booking);
		}
	}
		/**
		 * Allows users to view their past bookings stored in the database.
		 */
		private void viewBookingHistory() {
		ArrayList<Booking> bookings = account.getBookings();
		System.out.println("Your booking history is as follows:");
		for (Booking b: bookings) {
			Printer.printBookingInfo(b);
		}
		
	}

	/**
	 * Allows users to leave a review for a movie they have watched.
	 * (1) Select the movie and give a rating between 1-5.
	 * (2) Give a review for the same movie (optional).
	 * (3) Review added to the movie object through movie manager.
	 */
		private void rateMovie() {
		Scanner sc= new Scanner(System.in);

		MovieManager mm = new MovieManager();

		System.out.println("Which movie would you like to rate/review?");
		mm.listAll();
		int movieID = InputBoundary.getIntInput("Choose an option");

		int rating;
		do {
			rating = InputBoundary.getIntInput("Please give your rating (between 1-5)");
		} while (rating < 0 || rating > 5);

		System.out.println("Would you like to give a review?(Y/N)");
		String reviewOption = sc.nextLine();
		String reviewInput = null;

		if (reviewOption.compareToIgnoreCase("y") == 0) {
			System.out.println("Please enter your review:");
			reviewInput = sc.nextLine();
		}
		Review r = mm.addReviewToMovie(movieID, rating, account, reviewInput);
		System.out.println("Review Added.");
		Printer.printReviewInfo(r);
	}
}
