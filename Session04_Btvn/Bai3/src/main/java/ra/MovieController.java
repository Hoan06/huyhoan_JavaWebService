package ra;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/movies")
public class MovieController {

    private static final List<Movie> mockMovieDatabase = new ArrayList<>();

    static {
        mockMovieDatabase.add(new Movie("M001", "Inception", "Sci-Fi", 8.8));
        mockMovieDatabase.add(new Movie("M002", "Parasite", "Drama", 8.6));
        mockMovieDatabase.add(new Movie("M003", "Interstellar", "Sci-Fi", 8.7));
        mockMovieDatabase.add(new Movie("M004", "The Godfather", "Crime", 9.2));
    }

    @GetMapping
    public ResponseEntity<List<Movie>> getMoviesByGenre(
            @RequestParam(value = "genre", required = false) String genre) {

        if (genre == null || genre.isEmpty()) {
            return new ResponseEntity<>(mockMovieDatabase, HttpStatus.OK);
        }

        List<Movie> filteredMovies = mockMovieDatabase.stream()
                .filter(movie -> movie.getGenre().equalsIgnoreCase(genre))
                .collect(Collectors.toList());

        return new ResponseEntity<>(filteredMovies, HttpStatus.OK);
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<?> getMovieById(@PathVariable("movieId") String movieId) {

        for (Movie movie : mockMovieDatabase) {
            if (movie.getMovieId().equalsIgnoreCase(movieId)) {
                return new ResponseEntity<>(movie, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>("Movie not found with ID: " + movieId, HttpStatus.NOT_FOUND);
    }
}