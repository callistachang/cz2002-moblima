package models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Showtime implements ISerializable {
	public int id;
	public LocalDate date;
	public LocalTime time;
	public Movie movie;
	public ArrayList <Integer> seatsTaken; // HOW should i serialize this.

	//	public Cinema cinema;
	// NOT SURE if we should do the conversion of date string to LocalDate in the constructor OR in the ShowtimeManager
	// Since this is technically like the 'weak entity' between Movie and Cinema, should we include transactional details
	// E.g. Whether the movie is 3D or something, lol
	
	public ArrayList<Object> getSerializableData() {
		ArrayList<Object> data = new ArrayList<Object>();
		data.add(getId());
		data.add(getDateAsString());
		data.add(getDateAsTime());
		data.add(getMovie().getId());
		data.add(getSeatsTaken());
		return null;
	}
	
	public Showtime(int id, LocalDate date, LocalTime time, Movie movie, ArrayList<Integer> seatsTaken) {
		super();
		this.id = id;
		this.date = date;
		this.time = time;
		this.movie = movie;
		this.seatsTaken = seatsTaken;
	}

	public int getId() {
		return id;
	}

	public ArrayList<Integer> getSeatsTaken() {
		return seatsTaken;
	}

	public void setId(int id) {
		this.id = id;
	}


	public void setSeatsTaken(ArrayList<Integer> seatsTaken) {
		this.seatsTaken = seatsTaken;
	}


	public Showtime(Movie movie, Cinema cinema, LocalDate date, LocalTime time) {
		this.movie = movie;
//		this.cinema = cinema;
		this.date = date;
		this.time = time;
	}
	
	public String getDateAsString() {
		return this.date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	}
	
	public String getDateAsTime() {
		return this.time.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}


	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}


	
	public int getNoSeatsTaken() {
		return seatsTaken.size();
	}
	public void bookSeat(int seatNo) {
		seatsTaken.add(seatNo);
	}
}
