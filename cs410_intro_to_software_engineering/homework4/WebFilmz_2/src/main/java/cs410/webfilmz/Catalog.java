package cs410.webfilmz;

import java.util.*;

/* Represents the catalog, the list of all available films.
 * Caches a mapping of director to the films they directed.
 * Responsible for adding new films; generating recommendations, both generic
 * and personal.
 * Invariant: every film shows up both in allFilms set and in the set
 *   for the director and genre of the film in byDirector/byGenre
 * Refers to Film, factory for Films
 * Relies on ILikeFilm for film preferences for generating recommendations.
 */
public class Catalog {
    // All available films
    private final Set<Film> allFilms;

    // Cached mapping from director to the films they directed
    private final Map<String, Set<Film>> byDirector;

    // Cached mapping from genre to the films of that genre
    private final Map<String, Set<Film>> byGenre;

    public Catalog() {
        allFilms = new HashSet<>();
        byDirector = new HashMap<>();
        byGenre = new HashMap<>();
    }

    // Factory for films, ensures that new films are recorded in the catalog.
    public Film add(String title, String director, String genre,
                    int releaseYear, Rating rating) {
        Film newFilm = new Film(title, director, genre, releaseYear, rating);
        allFilms.add(newFilm);
        Set<Film> otherFilms = byDirector.get(director);
        if (otherFilms == null) {
            otherFilms = new HashSet<>();
            byDirector.put(director, otherFilms);
        }
        otherFilms.add(newFilm);

        // Update byGenre
        Set<Film> otherGenreFilms = byGenre.get(genre);
        if (otherGenreFilms == null) {
            otherGenreFilms = new HashSet<>();
            byGenre.put(genre, otherGenreFilms);
        }
        otherGenreFilms.add(newFilm);

        return newFilm;
    }

    // Returns the film with the given title, or throws a
    // RuntimeException if no such film is in the catalog.
    public Film findByTitle(String title) {
        for (Film film : allFilms) {
            if (film.title().equals(title)) {
                return film;
            }
        }
        throw new RuntimeException("film not found");
    }

    // Get up to count recommendations, the most recent/watched/liked films in the catalog.
    public Set<Film> getRecommendationsByYear(int count) {
        Comparator<Film> comparator = Comparator.comparingInt(Film::releaseYear).reversed();
        return getRecommendationBySorting(count, comparator);
    }
    public Set<Film> getRecommendationsMostWatched(int count) {
        Comparator<Film> comparator = Comparator.comparingInt(Film::totalWatched).reversed();
        return getRecommendationBySorting(count, comparator);
    }
    public Set<Film> getRecommendationsMostLiked(int count) {
        Comparator<Film> comparator = Comparator.comparingInt(Film::totalLiked).reversed();
        return getRecommendationBySorting(count, comparator);
    }

    // Generalization of non-personalized recommendations by Film attributes.
    // The comparator should put best recommendations at the *start* of the list.
    private Set<Film> getRecommendationBySorting(int count, Comparator<Film> comparator) {
        List<Film> films = new ArrayList<>(allFilms);
        films.sort(comparator);
        count = Integer.min(count, films.size());
        films = films.subList(0, count);
        return new HashSet<>(films);
    }

    // Get all films by liked director/genre
    public Set<Film> getRecommendationsByDirector(ILikeFilm user) {
        Set<Film> recommendations = new HashSet<>();
        /*
        for (String director : byDirector.keySet()) {
            if (user.isLikedDirector(director)) {
                recommendations.addAll(byDirector.get(director));
            }
        }
        */
        for (Map.Entry<String, Set<Film>> entry : byDirector.entrySet()) {
            if (user.isLikedDirector(entry.getKey())) {
                recommendations.addAll(entry.getValue());
            }
        }
        return recommendations;
    }

    // Get all films by liked genre
    public Set<Film> getRecommendationsByGenre(ILikeFilm user) {
        // Recommended films set
        Set<Film> recommendations = new HashSet<>();

        // Add films to set if the genre from byGenre is liked by the user
        for (Map.Entry<String, Set<Film>> entry : byGenre.entrySet()) {
            if (user.isLikedGenre(entry.getKey())) {
                recommendations.addAll(entry.getValue());
            }
        }
        return recommendations;
    }
}
