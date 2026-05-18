package ra;

public class Movie {
    private String movieId;
    private String title;
    private String genre;
    private double rating;

    public Movie(String movieId, String title, String genre, double rating) {
        this.movieId = movieId;
        this.title = title;
        this.genre = genre;
        this.rating = rating;
    }

    public String getMovieId() { return movieId; }
    public void setMovieId(String movieId) { this.movieId = movieId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }
}