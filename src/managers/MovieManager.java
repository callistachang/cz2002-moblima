package managers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

import handlers.DatabaseHandler;
import models.Account;
import models.Movie;
import models.Movie.MovieType;
import models.Movie.ShowingStatus;
import models.Review;
import serializers.MovieSerializer;
import serializers.AbstractSerializer;
/**
 * Controller for the entity labelled Movie.
 * Contains the logic to link showtime to other classes.
 * @author penel
 * @version 1.0
 * @since 2019-11-17
 */
public class MovieManager {
	/**
	 * The name of the csv file used.
	 */
	private final static String DATABASE_NAME = "moviedata";
	/**
	 * Initialises empty array of Movie objects.
	 */
	private static ArrayList<Movie> records = null;
	/**
	 * Checks if array list of movie objects is null.
	 * If null, data from the csv file is written to the list.
	 */
	public MovieManager() {
		if (records == null) {
			initializeDatabase();
		}
	}
	/**
	 * Iterates through records to compare given ID against list of IDs for a movie.
	 * @param movieID Unique identification number given to a movie.
	 * @return Movie that is present in records.
	 */
	public Movie getMovieByID(int movieID) {
		for (Movie m: records) {
			if (m.getId() == movieID) {
				return m;
			}
		}
		return null;
	}
	
	public boolean isEmpty() {
		if(records == null) {
			return true;
		}
		else
			return false;
	}
	/**
	 * Creates a single movie given the movie information by the staff.
	 * @param title The title of the movie.
	 * @param status The status of the movie.
	 * @param director The director of the movie.
	 * @param synopsis The synopsis of the movie.
	 * @param casts The casts of the movie.
	 * @param duration The duration of the movie.
	 * @param type The type of the movie.
	 */
	public void create(String title, String status, String director, String synopsis,
			ArrayList<String> casts, int duration, String type) {
		Movie movie = new Movie(records.size()+1, title, ShowingStatus.getByValue(status), director,
				synopsis, casts, duration, null, MovieType.getByValue(type));
		records.add(movie); 
		System.out.println("===Movie Created!===");
		Printer.printMovieInfo(movie);
		System.out.println("====================\n");
		updateDatabase();
	}
	/**
	 * Updates the title of a movie.
	 * @param movieID ID of movie to be updated.
	 * @param title New title of the movie.
	 */
	public void updateTitle(int movieID, String title) {
		Movie movie = getMovieByID(movieID);
		movie.setTitle(title);
		System.out.println("======");
		updateDatabase();
	}
	/**
	 * Updates the showing status of the movie.
	 * @param movieID Movie ID of movie to be updated.
	 * @param showingStatus New showing status of the movie.
	 */
	
	public void updateShowingStatus(int movieID, ShowingStatus showingStatus) {
		Movie movie = getMovieByID(movieID);
		movie.setStatus(showingStatus);
		System.out.println("======");
		updateDatabase();
	}
	/**
	 * Updates the synopsis of the movie.
	 * @param movieID Movie ID of movie to be updated.
	 * @param synopsis New synopsis of the movie.
	 */

	public void updateSynopsis(int movieID, String synopsis) {
		Movie movie = getMovieByID(movieID);
		movie.setSynopsis(synopsis);
		System.out.println("======");
		updateDatabase();
	}
	
	/**
	 * Updates the director name of the movie.
	 * @param movieID Movie ID of movie to be updated.
	 * @param director New director name of the director of the movie.
	 */
	public void updateDirector(int movieID, String director) {
		Movie movie = getMovieByID(movieID);
		movie.setDirector(director);
		System.out.println("======");
		updateDatabase();
	}
	/**
	 * Updates the duration of the movie.
	 * @param movieID Movie ID of movie to be updated.
	 * @param duration The new duration of the movie.
	 */
	
	public void updateDuration(int movieID, int duration) {
		Movie movie = getMovieByID(movieID);
		movie.setDuration(duration);
		System.out.println("======");
		updateDatabase();
	}
	
	/**
	 * Updates the type of the movie.
	 * @param movieID Movie ID of movie to be updated.
	 * @param movieType The new type of the movie.
	 */
	public void updateType(int movieID, MovieType movieType) {
		Movie movie = getMovieByID(movieID);
		movie.setType(movieType);
		System.out.println("======");
		updateDatabase();
	}
	
	/**
	 * Removes a single movie from the database given the ID of the movie.
	 * @param movieID Unique identification number of the movie to be removed. 
	 */
	public void remove(int movieID) {
		Movie movie = getMovieByID(movieID);
		records.remove(movie);
		updateDatabase();
	}
	/**
	 * Prints list of all the movies in the database.
	 * Prints the ID, Title and Type of movies.
	 */
	public void listAll() {
		System.out.println("The list of all movies is as follows: ");
		for (Movie m: records) {
			Printer.printMovieListing(m);
		}
	}

	/**
	 * Prints the list of all movies that are current showing from the database.
	 */
	public void listAllShowing() {
		System.out.println("The list of all movies is as follows: ");
		for (Movie m: records) {
			if (m.getStatus().toString().compareTo("Now Showing") == 0) {
				Printer.printMovieListing(m);
			}

		}
	}

	/**
	 * Prints the details of a specific movie.
	 * @param movieID Unique identification number of the movie selected.
	 */
	public void printMovieInfo(int movieID) {
		Movie movie = getMovieByID(movieID);
		Printer.printMovieInfo(movie);
	}
	/**
	 * Prints the reviews stored in the database for the chosen movie.
	 * @param movieID Unique identification number of the movie selected. 
	 */
	public void printMovieReviews(int movieID) {
		Movie movie = getMovieByID(movieID);
		Printer.printMovieReviews(movie);
	}

	/**
	 * Retrieve information from external csv file and converts it into an array of Movie objects.
	 */
	private static void initializeDatabase() {
		ArrayList<String> data = DatabaseHandler.readDatabase(DATABASE_NAME);
		AbstractSerializer serializer = new MovieSerializer();
		records = serializer.deserialize(data);
	}
	/**
	 * Converts current array of Movie objects into string to be stored in external csv file.
	 */
	private void updateDatabase() {
		AbstractSerializer serializer = new MovieSerializer();
		ArrayList<String> updatedRecords = serializer.serialize(records);
		DatabaseHandler.writeToDatabase(DATABASE_NAME, updatedRecords);
	}

	/**
	 * Prints the list of top 5 movies, using ratings as the criterion.
	 */
	@SuppressWarnings("unchecked")
	public void listTop5ByRatings() {

		HashMap<String, Double> map = new HashMap<String, Double>();
		for (Movie m: records) {
			double movieRating = 0;
			ArrayList<Review> reviews = m.getReviews();
			if (reviews.get(0) != null || reviews == null ) {
				for (Review r: reviews) {
					movieRating += r.getRating();
				}
			map.put(m.getTitle(), movieRating/reviews.size());
			}
		}

		Object[] a = map.entrySet().toArray();
		Arrays.sort(a, new Comparator<Object>() {
			public int compare(Object o1, Object o2) {
		        return ((Map.Entry<String, Double>) o2).getValue().compareTo(
		               ((Map.Entry<String, Double>) o1).getValue());
		    }
		});
		for (int i = 0; i < a.length; i++) {
		    System.out.println((i+1) + ". " + ((Map.Entry<String, Integer>) a[i]).getKey() + ": "
                    + ((Map.Entry<String, Integer>) a[i]).getValue() + "/5");
		    if (i == 4) break;
		}
	}
	/**
	 * Adds a user review to a specific movie via the movie's ID.
	 * Contains rating and content of the review.
	 * @param movieId The movie that is being reviewed by the user.
	 * @param rating The rating left by the user.
	 * @param account The account of the user that is reviewing.
	 * @param content The content of the review left by the user.
	 * @return The new review object that is created.
	 */
	public Review addReviewToMovie(int movieId, int rating, Account account, String content) {
		Movie movie = getMovieByID(movieId);
		ReviewManager rm = new ReviewManager();
		Review newReview = rm.create(rating, account, content);

		ArrayList<Review> reviews = movie.getReviews();
		reviews.add(newReview);
		updateDatabase();
		return newReview;
	}
}
