package cs410.webfilmz;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* Represents a user (subscriber to the service), including
 * their watch/like history and liked directors/genres.
 * Responsible for maintaining watch/like history, liked directors/genres, expressing
 * film preferences, and getting personalized recommendations.
 * Refers to and updates Film.
 * Relies on Catalog to implement recommendations.
 */
public class User implements ILikeFilm {
    /* Sets of films watched and liked, respectively.
     * Sets of liked directors and genres, respectively.
     * Uses Set to avoid recording watch/like multiple times.
     * Also uses Set to avoid looping through liked films for liked directors and genres
     */
    private final Set<Film> watched;
    private final Set<Film> liked;

    // Stores liked directors/genres
    private final Set<String> likedDirectors;
    private final Set<String> likedGenres;

    public User() {
        watched = new HashSet<>();
        liked = new HashSet<>();
        likedDirectors = new HashSet<>();
        likedGenres = new HashSet<>();
    }

    // Record that the user watched/liked the given film,
    // updating film if not already in Set.
    // Record the director and genre if the user liked the film.
    public void addWatched(Film film) {
        if (watched.add(film)) {
            film.incrementWatched(1);
        }

    }

    // Record the liked director and genre if not seen already
    public void addLiked(Film film) {
        if (liked.add(film)) {
            film.incrementLiked(1);
            // Update liked directors and genres
            likedDirectors.add(film.director());
            likedGenres.add(film.genre());
        }
    }

    // Get a map with all recommended film sets.
    // Remove watched films from year, director and genre recommendation sets.
    public Map<String, Set<Film>> getAllRecommendations(Catalog catalog, int initialGenericRecsCount) {
        // Film recommendations by genre
        Set<Film> recommendationsByGenre = getRecommendationsByGenre(catalog);

        // Most watched films
        Set<Film> recommendationsByMostWatched = catalog.getRecommendationsMostWatched(initialGenericRecsCount);

        // Most liked films
        Set<Film> recommendationsByMostLiked = catalog.getRecommendationsMostLiked(initialGenericRecsCount);

        // Initial count of film recommendations by year
        Set<Film> recommendationsByYear = catalog.getRecommendationsByYear(initialGenericRecsCount);

        // Initial count of film recommendations by directors
        Set<Film> recommendationsByDirector = getRecommendationsByDirector(catalog);

        // Remove watched films from year, most liked and most watched
        for (Film film : watched) {
            recommendationsByYear.remove(film);
            recommendationsByMostLiked.remove(film);
            recommendationsByMostWatched.remove(film);
        }

        // Map titles to corresponding set of films
        return Map.of(
                "New Releases",
                recommendationsByYear,
                "Favorite Directors",
                recommendationsByDirector,
                "Favorite Genres",
                recommendationsByGenre,
                "Most Watched",
                recommendationsByMostWatched,
                "Most Liked",
                recommendationsByMostLiked);
    }

    // Get a set of film recommendations by director.
    // Remove films already watched in set.
    public Set<Film> getRecommendationsByDirector(Catalog catalog) {
        // Films by director
        Set<Film> recommendationsByDirector = catalog.getRecommendationsByDirector(this);

        // Remove watched films
        for (Film film : watched) {
            recommendationsByDirector.remove(film);
        }
        return recommendationsByDirector;
    }

    // Get a set of film recommendations by genre.
    // Remove films already watched in set.
    public Set<Film> getRecommendationsByGenre(Catalog catalog) {
        // Films by genre
        Set<Film> recommendationsByGenre = catalog.getRecommendationsByGenre(this);

        // Remove watched films
        for (Film film : watched) {
            recommendationsByGenre.remove(film);
        }
        return recommendationsByGenre;
    }

    // Do any of the films the user liked have the given
    // director/genre?
    // Uses sets storing directors and genres instead of looping through liked films.
    public boolean isLikedDirector(String director) {
        return likedDirectors.contains(director);
    }
    public boolean isLikedGenre(String genre) {
        return likedGenres.contains(genre);
    }
}
