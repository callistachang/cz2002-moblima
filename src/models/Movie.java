package models;

import java.util.ArrayList;
import java.util.EnumSet;

import managers.MovieManager;
import models.Movie.MovieType;
import models.Movie.ShowingStatus;

public class Movie implements ISerializable {
	private int id;
	private String title;
	private ShowingStatus status;
	private String synopsis;
	private String director;
	private ArrayList<String> casts;
	private int duration;
	private ArrayList<Review> reviews;
	private MovieType type;
	
	public Movie(int id, String title, ShowingStatus status, String synopsis, String director, ArrayList<String> casts, 
			int duration, ArrayList<Review> reviews, MovieType type) {
		this.id = id;
		this.title = title;
		this.status = status;
		this.synopsis = synopsis;
		this.director = director;
		this.casts = casts;
		this.duration = duration;
		this.reviews = reviews;
		this.type = type;
	}
	
	public static void printAttributes() {
		System.out.println("(1) Title");
		System.out.println("(2) Showing Status");
		System.out.println("(3) Synopsis");
		System.out.println("(4) Director");
		System.out.println("(5) Duration");
		System.out.println("(6) Type");
	}
	
	public void printInfo() {
		System.out.println("ID: " + id);
		System.out.println("Title: " + title);
		System.out.println("Showing Status: " + status.toString());
		System.out.println("Synopsis: " + synopsis);
		System.out.println("Duration: " + duration + " minutes");
		System.out.println("Movie Type: " + type.toString());
		System.out.println("Director: " + director);
		System.out.print("Cast Members: ");
		
		int i;
		for (i = 0; i < casts.size()-1; i++) {
			System.out.printf(casts.get(i) + ", ");
		}
		System.out.println(casts.get(i));
	}
	
	public void printReviews() {
		int i;
		for (i = 0; i < reviews.size(); i++) {
			Review review = reviews.get(i);
			System.out.printf("(%d)\n", i+1);
			System.out.println("User: " + review.getUser());
			System.out.println("Rating: " + review.getRating());
			if (review.getContent() != null) {
				System.out.println("Review: " + review.getContent());
			}
		}
	}
	
	public ArrayList<Object> getSerializableData() {
		ArrayList<Object> data =  new ArrayList<Object>();
		data.add(getId());
		data.add(getTitle());
		data.add(getStatus());
		data.add(getSynopsis());
		data.add(getDirector());
		data.add(getCasts());
		data.add(getDuration());
		data.add(getReviews());
		data.add(getType());
		return data;
	}
	
	public void setAttr(int attrNum, String attrVal) {
		switch (attrNum) {
			case 1:
				title = attrVal;
			case 2:
				status = ShowingStatus.getByValue(attrVal);
			case 3:
				synopsis = attrVal;
			case 4:
				director = attrVal;
			case 5:
				duration = Integer.parseInt(attrVal);
			case 6:
				type = MovieType.getByValue(attrVal);
		}
	}
	
	public int getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ShowingStatus getStatus() {
		return status;
	}

	public void setStatus(ShowingStatus status) {
		this.status = status;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public ArrayList<String> getCasts() {
		return casts;
	}

	public void setCasts(ArrayList<String> casts) {
		this.casts = casts;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public ArrayList<Review> getReviews() {
		return reviews;
	}

	public void setReviews(ArrayList<Review> reviews) {
		this.reviews = reviews;
	}
	
	public MovieType getType() {
		return type;
	}

	public void setType(MovieType type) {
		this.type = type;
	}
	
	public enum ShowingStatus {
		COMING_SOON("Coming Soon"),
		NOW_SHOWING("Now Showing"),
		NO_LONGER_SHOWING("No Longer Showing");
		
		private final String value;
		
		private ShowingStatus(String value) {
			this.value = value;
		}
		public String toString() {
			return value;
		}
		public static ShowingStatus getByValue(String value) {
			for (ShowingStatus ss: EnumSet.allOf(ShowingStatus.class)) {
				if (ss.toString().equals(value)) {
					return ss;
				}
			}
			return null;
		}
	}
	
	public enum MovieType {
		TWO_DIMENSIONAL("2D"),
		THREE_DIMENSIONAL("3D");
		
		private final String value;
		
		private MovieType(String value) {
			this.value = value;
		}
		public String toString() {
			return value;
		}
		public static MovieType getByValue(String value) {
			for (MovieType mt: EnumSet.allOf(MovieType.class)) {
				if (mt.toString().equals(value)) {
					return mt;
				}
			}
			return null;
		}
	}
}
