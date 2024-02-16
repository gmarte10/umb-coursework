package domain;

public class Movie {
	int id;
	String name;
	int price;
	String genre;
	int year;
	String contentRating;
	String director;
	String image;
	String video;
	int rentPrice;
	
	public Movie(int id, String name, int price, String genre, int year, String contentRating, String director,
			String image, String video, int rentPrice) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.genre = genre;
		this.year = year;
		this.contentRating = contentRating;
		this.director = director;
		this.image = image;
		this.video = video;
		this.rentPrice = rentPrice;
	}
	public Movie() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getContentRating() {
		return contentRating;
	}

	public void setContentRating(String contentRating) {
		this.contentRating = contentRating;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}
	
	public int getRentPrice() {
		return rentPrice;
	}

	public void setRentPrice(int rentPrice) {
		this.rentPrice = rentPrice;
	}
}
