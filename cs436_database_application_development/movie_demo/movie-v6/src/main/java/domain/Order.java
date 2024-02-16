package domain;

public class Order {
	int id;
	String email;
	int movieId;
	String date;
	public Order(int id, String email, int movieId, String date) {
		super();
		this.id = id;
		this.email = email;
		this.movieId = movieId;
		this.date = date;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getMovieId() {
		return movieId;
	}
	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	
	

}
