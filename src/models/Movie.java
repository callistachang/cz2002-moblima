package models;

import java.util.ArrayList;
import java.util.EnumSet;

import managers.Formatter;
import managers.MovieManager;
import managers.ReviewManager;
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
	
	public ArrayList<Object> getSerializableData() {
		ArrayList<Object> data =  new ArrayList<Object>();
		data.add(Formatter.getStringFromInt(id));
		data.add(title);
		data.add(status.toString());
		data.add(synopsis);
		data.add(director);
		data.add(casts);
		data.add(Formatter.getStringFromInt(duration));
		data.add(getReviewIDs()); // CHECK AGAIN for the null settings.
		data.add(type.toString());
		return data;
	}
	
	public ArrayList<String> getReviewIDs() {
		ArrayList<String> reviewIDs = new ArrayList<String>();
		ArrayList<Review> reviews = getReviews();
		
		if (reviews == null)
			reviewIDs.add("null");
		else if (reviews.get(0) == null) {
			if (reviews.size() == 1)
				reviewIDs.add("null");
			else {
				reviews.remove(0);
				for (Review r: reviews)
					reviewIDs.add(r.toString());
			}
		} else {
			for (Review r: reviews)
				reviewIDs.add(r.toString());
		}
		return reviewIDs;
	}
	
	public void setAttr(int attrNum, String attrVal) {
		switch (attrNum) {
			case 1:
				title = attrVal;
				break;
			case 2:
				status = ShowingStatus.getByValue(attrVal);
				break;
			case 3:
				synopsis = attrVal;
				break;
			case 4:
				director = attrVal;
				break;
			case 5:
				duration = Integer.parseInt(attrVal);
				break;
			case 6:
				type = MovieType.getByValue(attrVal);
				break;
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
//	public void createReview(int rating, Account user, String content) {
//		ReviewManager rm = new ReviewManager();
//		Review review = rm.createReview(rating, user, content);
//		//add review into reviews
//			
//	}
}
