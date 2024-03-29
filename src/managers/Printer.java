package managers;

import java.util.ArrayList;

import models.Booking;
import models.Cinema;
import models.Movie;
import models.Pricing;
import models.Review;
import models.Showtime;

/**
 * Controller to print all necessary outputs for all managers. 
 * @author balad
 * @version 1.0
 * @since 2019-11-17
 */
public class Printer {
	
	public static void printPricingInfo(Pricing pricing) {
		System.out.println("(1) Base Price: $" + pricing.getBasePrice());
		System.out.println("(2) Weekend Premium: +$" + pricing.getWeekendPremium());
		System.out.println("(3) 3D Premium: +$" + pricing.getMovieTypePremium());
		System.out.println("(4) Platinum Cinema Premium: +$" + pricing.getPlatinumCinemaPremium());
		System.out.println("(5) Gold Cinema Premium: +$" + pricing.getGoldCinemaPremium());
		System.out.println("(6) Student Discount: -$" + pricing.getStudentDiscount());
		System.out.println("(7) Senior Citizen Discount: -$" + pricing.getSeniorCitizenDiscount());
	}
	
	/**
	 * Prints attributes of a movie that can be modified.
	 */
	public static void printMovieAttributes() {
		System.out.println("(1) Title");
		System.out.println("(2) Showing Status");
		System.out.println("(3) Synopsis");
		System.out.println("(4) Director");
		System.out.println("(5) Duration");
		System.out.println("(6) Type");
	}
	
	/**
	 * Prints the details of the movie booking
	 * @param b Movie booking to have its details printed
	 */
	public static void printBookingInfo(Booking b) {
		MovieManager mm = new MovieManager();
		Movie m = mm.getMovieByID(b.getShowtime().getMovieID());
		
		System.out.println("==========================");
		System.out.println("+ TID: " + b.getTID());
		System.out.println("+ Movie: " + m.getTitle() + " (" + m.getType().toString() + ")");
		System.out.println("+ Showtime Date: " + Formatter.getStringFromLocalDate(b.getShowtime().getDate()));
		System.out.println("+ Showtime Time: " + Formatter.getStringFromLocalTime(b.getShowtime().getTime()));
		System.out.println("+ Showtime ID: " + b.getShowtime().getId());
		System.out.println("+ Cinema: " + b.getCinema().getId());
		System.out.println("==========================");

	}
	
	/**
	 * Prints details of the specific movie.
	 * @param m The movie object whose details need to be printed.
	 */
	public static void printMovieInfo(Movie m) {
		System.out.println("===");
		System.out.println("ID: " + m.getId());
		System.out.println("Title: " + m.getTitle());
		System.out.println("Showing Status: " + m.getStatus().toString());
		System.out.println("Synopsis: " + m.getSynopsis());
		System.out.println("Duration: " + m.getDuration() + " minutes");
		System.out.println("Movie Type: " + m.getType().toString());
		System.out.println("Director: " + m.getDirector());
		System.out.print("Cast Members: ");
		
		int i;
		ArrayList<String> casts = m.getCasts();
		for (i = 0; i < casts.size()-1; i++) {
			System.out.printf(casts.get(i) + ", ");
		}
		System.out.println(casts.get(i));
		System.out.println("===");
	}
	/**
	 * Prints reviews of the specific movie.
	 * @param m The movie object whose reviews need to be printed.
	 */
	public static void printMovieReviews(Movie m) {
		int i;
		double totalRating = 0;
		ArrayList<Review> reviews = m.getReviews();
		for (i = 0; i < reviews.size(); i++) {
			Review review = reviews.get(i);
			System.out.printf("(%d)\n", i+1);
			System.out.println("User: " + review.getUser());
			System.out.println("Rating: " + review.getRating());
			totalRating += review.getRating();
			if (!review.getContent().equals("null")) {
				System.out.println("Review: " + review.getContent());
			}
		}
		if(reviews.size()>1) {
			System.out.println("\nAverage Rating: " + totalRating/reviews.size());
		} else {
		System.out.println("\nAverage Rating: NA");
		}
	}
	
	/**
	 * Prints the ID, title and movie type of the specific movie from records.
	 * @param m The movie object whose details need to be printed.
	 */
	public static void printMovieListing(Movie m) {
		System.out.printf("(ID: %d) %s (%s)\n", m.getId(), m.getTitle(), m.getType());
	}
	
	/**
	 * Prints the details of a particular cinema.
	 * @param cinema The cinema object whose details need to be printed.
	 */
	public static void printCinemaInfo(Cinema cinema) {
		System.out.println("ID: " + cinema.getId());
		System.out.println("Cinema Class: " + cinema.getCinemaClass());
	}
	
	/**
	 * Prints the attributes of showtime object.
	 */
	public static void printShowtimeAttributes() {
		System.out.println("(1) Movie");
		System.out.println("(2) Date");
		System.out.println("(3) Time");
	}
	/**
	 * Prints the details of a particular showtime.
	 * @param showtime The showtime object whose details need to be printed.
	 */
	public static void printShowtimeDetails(Showtime showtime) {
		System.out.println("ShowtimeID: (" + showtime.getId() + ")");
		MovieManager mm = new MovieManager();
		int movieID = showtime.getMovieID();
		Movie movie = mm.getMovieByID(movieID);
		System.out.println("Movie: " + movie.getTitle());
		System.out.println("Date: " + showtime.getDate());
		System.out.println("Time: " + showtime.getTime());
	}
	
	/**
	 * Prints the details of showtimes in a particular cinema.
	 * @param cinema The cinema object whose showtime details need to be printed. 
	 */
	public static void printShowtimeDetails(Cinema cinema) {
		System.out.println("Cinema ID: " + cinema.getId());
		for(Showtime showtime : cinema.getShowtimes()) {
			printShowtimeDetails(showtime);
		}
	}
	/**
	 * Prints the details of a review
	 * @param review Review to be printed
	 */
	
	public static void printReviewInfo(Review review) {
		System.out.println("Review info:");
		System.out.println("User: " + review.getUser());
		System.out.println("Rating: " + review.getRating());
		if (!review.getContent().equals("null")) {
			System.out.println("Review: " + review.getContent());
		}
	}
}
