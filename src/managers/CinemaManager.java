package managers;

import java.util.ArrayList;

import handlers.DatabaseHandler;
import models.Account;
import models.Booking;
import models.Cinema;
import models.Movie;
import models.Showtime;
import serializers.AbstractSerializer;
import serializers.CinemaSerializer;

public class CinemaManager {
	private static final String DATABASE_NAME = "cinemadata";
	private static ArrayList<Cinema> records = null;
	
	public Cinema getCinemaByID(int cinemaID) {
		for (Cinema c: records) {
			if (c.getId() == cinemaID) {
				return c;
			}
		}
		return null;
	}
	
	public void listAllByCineplex(int cineplexID) {
		
	}	
	
	
//	public ArrayList<Showtime> getShowtimesInCinema(Cinema cinema) {
//		for (Cinema c: records) {
//			
//		}
//	}
	
	public void listAvailabilitiesForMovie(Cinema cinema, int movieID) {
		ShowtimeManager sm = new ShowtimeManager();
		ArrayList<Showtime> showtimes = cinema.getShowtimes();
		
		for (Showtime showtime: showtimes) {
			Movie movie = showtime.getMovie();
			if (movie.getId() == movieID) {
				System.out.println("CinemaId = " + cinema.getId());
				System.out.println("Class: " + cinema.getCinemaClass());
				System.out.println("ShowtimeId: " + showtime.getId());
				cinema.getTotalNumSeats() - showtime.getTotalNumSeatsTaken();
			}
		}
	}
	
	public void listShowtimesInCinemaForMovie(Cinema cinema, int movieID) {
		ShowtimeManager sm = new ShowtimeManager();
		ArrayList<Showtime> showtimes = cinema.getShowtimes();
		
		for (Showtime showtime: showtimes) {
			Movie movie = showtime.getMovie();
			if (movie.getId() == movieID) {
				System.out.println(cinema);
				System.out.println(showtime);
				}
			}
//			sm.getShowtimeMovie(showtime,movie)
//			
//			sm.getShowtimes(movieID);
	}

	private static void initializeDatabase() {
		ArrayList<String> data = DatabaseHandler.readDatabase(DATABASE_NAME);
		AbstractSerializer serializer = new CinemaSerializer();
		records = serializer.deserialize(data);
	}
	
	private void updateDatabase() {
		AbstractSerializer serializer = new CinemaSerializer();
		ArrayList<String> updatedRecords = serializer.serialize(records);
		DatabaseHandler.writeToDatabase(DATABASE_NAME, updatedRecords);
	}
}
