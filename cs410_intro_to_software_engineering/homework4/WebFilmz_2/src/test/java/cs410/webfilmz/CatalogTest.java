package cs410.webfilmz;

/*
 *
 * ADD YOUR Catalog TESTS TO THIS FILE
 *
 */

import org.junit.jupiter.api.Test;

import java.util.Set;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class CatalogTest {

    // Only use this catalog if the test does not modify it or the films it contains
    final Catalog catalog = getCatalog();

    // Makes a known small catalog for testing. Add example movies and their info to catalog.
    static Catalog getCatalog() {
        Catalog catalog = new Catalog();
        catalog.add("The Terminator", "James Cameron", "SciFi", 1984, Rating.R);
        catalog.add("The Princess Bride", "Rob Reiner", "Romance", 1987, Rating.PG);
        catalog.add("The City of Lost Children", "Jean-Pierre Jeunet", "SciFi", 1995, Rating.R);
        catalog.add("Toy Story", "John Lasseter", "Comedy", 1995, Rating.G);
        catalog.add("Titanic", "James Cameron", "Romance", 1997, Rating.PG13);
        catalog.add("Memento", "Christopher Nolan", "Thriller", 2000, Rating.R);
        catalog.add("Amelie", "Jean-Pierre Jeunet", "Romance", 2001, Rating.R);
        catalog.add("Inception", "Christopher Nolan", "SciFi", 2010, Rating.PG13);
        catalog.add("The Martian", "Ridley Scott", "SciFi", 2015, Rating.PG13);
        catalog.add("Oppenheimer", "Christopher Nolan", "Bio" ,2023, Rating.R);
        return catalog;
    }

    // Represents preference for a single genre
    private class JustLikesOneGenre implements ILikeFilm {
        private String likedGenre;
        JustLikesOneGenre(String likedGenre) {
            this.likedGenre = likedGenre;
        }

        // Do any of the films the user liked have the given director/genre?
        @Override
        public boolean isLikedDirector(String director) {
            return false;
        }

        public boolean isLikedGenre(String genre) {
            return this.likedGenre.equals(genre);
        }
    }

    // Test the recommendations by genre
    @Test
    void getPseudoRecommendationsByGenre() {
        // Are all romance films returned?
        String likedGenre = "Romance";
        assertEquals(
                Set.of(catalog.findByTitle("Amelie"), catalog.findByTitle("Titanic"),
                        catalog.findByTitle("The Princess Bride")),
                catalog.getRecommendationsByGenre(new JustLikesOneGenre(likedGenre)));

        likedGenre = "Thriller";
        // Are all thriller films returned?
        assertEquals(
                Set.of(catalog.findByTitle("Memento")),
                catalog.getRecommendationsByGenre(new JustLikesOneGenre(likedGenre)));


    }
}
